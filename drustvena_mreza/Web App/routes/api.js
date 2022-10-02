/**
 * Created by Mislav on 11/27/2015.
 */
var express = require('express');
var router = express.Router();

var User = require('../models/user');

module.exports=function (passport) {

    router.use('/content', require('./content'));
    router.use('/home', require('./home'));
    router.use('/search', require('./search'));
    router.use('/bubble', require('./bubble'));
    router.use('/user', require('./user'));
    router.use('/comment', require('./comment'));
    router.use('/account', require('./account'));
    router.use('/util', require('./util'));

    router.post('/sign-up', function(req, res, next) {
        var form = req.body;
        User.forge({
            username: form.username,
            email: form.email,
            password_hash: form.password,
            first_name: form.firstName,
            last_name: form.lastName,
            middle_name: form.middleName,
            address: form.address,
            city: form.city,
            country_name: form.country
        }).save().then(function (user) {
            res.json({response: user});
        }).catch(function(error) {
            res.json({errors: error.messages});
        });
    });

    router.post('/sign-in', function(req, res, next) {
        passport.authenticate('local', function(err, user, info) {
            if(err) {
                return res.json({error:'An error occurred.'});
            }

            if(!user) {
                return res.json({error:'Invalid login attempt.'});
            }

            req.logIn(user, function(err) {
                if(err) {
                    return res.json({error:'An error occurred.'});
                } else {
                    return res.json({response: user});
                }
            });
        })(req, res, next);
    });

    router.post('/sign-out', function(req, res, next) {
        if(!req.isAuthenticated()) {
            notFound404(req, res, next);
        } else {
            req.logout();
            res.end(200);
        }
    });

    router.get("/isUsernameAvailable", function(req, res, next) {
        User.where({username: req.query.username}).fetch().then(function(model) {
            res.json({available:(model ? false : true)});
        });
    });

    router.get("/isEmailAvailable", function(req, res, next) {
        User.where({email: req.query.email}).fetch().then(function(model) {
            res.json({available:(model ? false : true)});
        });
    });

    return router;
};