/**
 * Created by Gordan on 15.12.2015..
 */

var express = require('express');
var router = express.Router();

var User = require('../models/user');
var Privilege = require('../models/privilege');

var requireAuthentication = require('../utils/authentication');

router.all('*', requireAuthentication);

router.get('/isContact', function (req, res) {
    Privilege.where({permittee_id: req.user.get('id'), permitter_id: req.query.id}).fetch().then(function (privilege) {
        res.json({isContact: (privilege ? true : false)});
    });
});

router.get('/info', function (req, res) {
    User.where({id: req.query.id}).fetch().then(function (user) {
        res.json(user);
    });
});

router.post('/contactRequest', function (req, res, next) {
    Privilege.forge({permittee_id: req.user.get('id'), permitter_id: req.body.user_id}).save().then(function () {
        res.end();
    })
});

router.get('/contacts', function(req, res, next) {
    Privilege.query(function(qb) {
        qb.join('user', 'privilege.permitter_id', 'user.id')
            .where('privilege.permittee_id', req.query.id || req.user.get('id'))
            .orderBy('username', 'ASC');
    }).fetchAll({columns: ['user.id', 'user.username']}).then(function (contacts) {
        res.json({contacts: contacts});
    }).catch(function(error) {
        console.log(error);
    });
});

module.exports=router;