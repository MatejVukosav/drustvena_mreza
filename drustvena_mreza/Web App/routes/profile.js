/**
 * Created by Zeljko on 5/11/2015.
 */

var express = require('express');
var router = express.Router();

var ValidationError = require('../models/errors/validationError.js');

var User = require('../models/user');

var requireAuthentication = require('../utils/authentication');

router.all('*' , requireAuthentication);

router.get('/view', function(req, res, next) {
    User.where({id: req.user.id}).fetch().then(function (user) {
        res.render('view-profile.ejs', {user: user.toJSON()});
    })
});

router.get('/edit', function(req, res, next) {
    User.where({id: req.user.id}).fetch().then(function (user) {
        res.render('edit-profile.ejs', {user: user.toJSON()});
    })
});

router.post('/edit', function(req, res, next) {

    var isAndroid = req.is('application/json');

    var user = req.user;
    var form = req.body;

    user.set('first_name', form.firstName);
    user.set('last_name', form.lastName);
    user.set('middle_name', form.middleName);
    user.set('country_name', form.country);
    user.set('city', form.city);
    user.set('address', form.address);
    user.set('relationship_status_id', form.relationshipStatusId);
    user.set('gender_id', form.gender_id);
    user.save().then(function (user) {
        if(isAndroid) {
            res.json({user: user.toJSON()});
        } else {
            res.render('edit-profile.ejs', {user: user.toJSON()});
        }
    }).catch(ValidationError, function (error) {
        if(isAndroid) {
            res.json({errors: error.messages});
        } else {
            res.render('edit-profile.ejs', {user: user.toJSON(), editProfileError: error.messages});
        }
    })
});

module.exports=router;