
var express = require('express');
var router = express.Router();

var passport = require('../config/passport');

var Promise = require('bluebird');

var ValidationError = require('../models/errors/validationError');

var User = require('../models/user');
var Country = require('../models/country');

router.use('/api', require('./api')(passport));
router.use('/partial', require('./partial'));
router.use('/profile', require('./profile'));
router.use('/image', require('./image'));
router.use('/res', require('./res'));

// index - sign in and sign up
// GET
router.get('/', function(req, res, next) {
    if(req.isAuthenticated()){
        res.redirect('/homepage');
    }
    else {
        res.render('index.ejs');
    }
});
// GET
router.get('/sign-in', function(req, res, next){
    if(req.isAuthenticated()){
        res.redirect('/homepage');
    }
    else {
        res.render('index.ejs', {title: 'Sign in', signIn: true});
    }
});
// POST
router.post('/sign-in', function(req, res, next) {
    passport.authenticate('local', function(err, user, info) {
        var isAndroid = req.is('application/json');
        if(err) {
            if(isAndroid) {
                return res.json({error:'An error occurred.'});
            } else {
                return res.render('index.ejs', {loginError:'An error occoured.'});
            }

        }

        if(!user) {
            if(isAndroid) {
                return res.json({error:'Invalid login attempt.'});
            } else {
                return res.render('index.ejs', {loginError:'Invalid login attempt.'});
            }
        }

        return req.logIn(user, function(err) {
            if(err) {
                if(isAndroid) {
                    return res.json({error:'An error occurred.'});
                } else {
                    return res.render('index', {title: 'Sign in', loginError:'An error occoured.'});
                }
            } else {
                if(isAndroid) {
                    return res.end();
                } else {
                    return res.redirect('/homepage');
                }
            }
        });
    })(req, res, next);
});

// sign up
// GET
router.get('/sign-up', function(req, res, next) {
    if(req.isAuthenticated()){
        res.redirect('/homepage');
    }
    else {
        res.render('index.ejs', {title: 'Sign up', signUp: true});
    }
});

// POST
router.post('/sign-up', function(req, res, next) {
    var isAndroid = req.is('application/json');
    var form = req.body;
    return User.forge({
        username: form.username,
        email: form.email,
        password_hash: form.password,
        first_name: form.firstName,
        last_name: form.lastName,
        middle_name: form.middleName,
        address: form.address,
        city: form.city,
        country_name: form.country,
        gender_id: form.gender_id
    }).save().then(function (user) {
        if(isAndroid) {
            res.json(user);
        } else {
            res.render('sign-up-successful.ejs', {title: 'Confirm account', data: form});
        }
    }, function(error) {
        if(isAndroid) {
            res.json(error.messages);
        } else {
            res.render('index', {title: 'Sign up', signUp: true, registerError: error.messages, registrationInput: form});
        }
    });
});


router.get("/emailverification",function(req,res,next){
    User.where({id: req.query.id}).fetch().then(function(user) {
        if(user.get('password_hash') === req.query.hash){
            user.set({confirmed: true});
            user.save().then(function(user){
                res.render('emailverification.ejs', {username: user.get('username')});
            });
        }
    });
});

// sign out
// POST
router.post('/sign-out', function(req, res, next) {
    if(!req.isAuthenticated()) {
        notFound404(req, res, next);
    } else {
        req.logout();
        res.redirect('/');
    }
});

router.get('/homepage', function(req, res, next) {
    res.render('homepage.ejs', {title: 'Home', user: req.user.toJSON()});
});





/********************************/
// 404 not found
router.use(function(req, res, next) {
    res.render('homepage.ejs', {title: 'Home', user: req.user.toJSON()});
});

module.exports = router;