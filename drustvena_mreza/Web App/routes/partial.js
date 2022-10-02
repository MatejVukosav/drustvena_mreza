var express = require('express');
var router = express.Router();

var User = require('../models/user');
var Bubble = require('../models/bubble');

var requireAuthentication = require('../utils/authentication');
router.all('*' , requireAuthentication);

// GET
router.get('/feed', function(req, res, next) {
    res.render('feed.partial.ejs');
});
router.get('/new-bubble', function(req, res, next) {
    res.render('bubble-editor.partial.ejs');
});
router.get('/edit-bubble/:id', function(req, res, next) {
    Bubble.where({id: req.params.id}).fetch().then(function(bubble){
      res.render('bubble-editor.partial.ejs', bubble);
    });
});
router.get('/new-content', function(req, res, next) {
    res.render('new-content.partial.ejs');
});
router.get('/view-profile', function(req, res, next) {
    User.where({id: req.user.id}).fetch().then(function (user) {
        res.render('view-profile.partial.ejs', {user: user.toJSON()});
    })
});
router.get('/edit-profile', function(req, res, next) {
    res.render('edit-profile.partial.ejs', {user: req.user.toJSON()});
});
// nova slika
router.get('/edit-image/', function(req, res, next) {
    res.render('edit-image.partial.ejs', {user: req.user.toJSON()});
});
// uređivanje postojeće
router.get('/edit-image/:id', function(req, res, next) {
    res.render('edit-image.partial.ejs', {user: req.user.toJSON()});
});
router.get('/manage-account', function(req, res, next) {
    res.render('manage-account.partial.ejs');
});

// 404
router.get('/404', function(req, res, next) {
    res.render('404.partial.ejs');
});

module.exports=router;