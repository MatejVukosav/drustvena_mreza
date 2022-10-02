/**
 * Created by Gordan on 20.12.2015..
 */

var express = require('express');
var router = express.Router();
var ValidationError = require('../models/errors/validationError');

router.all('*', require('../utils/authentication'));

router.post('/changePassword', function(req, res) {

    var user = req.user;
    var form = req.body;

    if(!user.validPassword(form.oldPassword)){
        return res.status(403).json({error: 'Invalid password.'})
    }

    user.set('password_hash', form.newPassword);
    user.save().then(function (user) {
        res.json(user);
    }).catch(ValidationError, function(error) {
        res.status(400).json({errors: error.messages});
    })
});

module.exports = router;