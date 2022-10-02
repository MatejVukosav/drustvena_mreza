/**
 * Created by Gordan on 14.12.2015..
 */

var ValidationError = require('../models/errors/validationError');

var express = require('express');
var router = express.Router();
var general = require('../utils/general');

var Bubble = require('../models/bubble');
var Content = require('../models/content');

router.all("*", require('../utils/authentication'));

router.post('/create', function(req, res) {
    var form = req.body;
    var user = req.user;

    Bubble.forge({
        user_id: user.get('id'),
        bubble_type_id: 3,
        title: form.title,
        description: form.description
    }).save().then(function (bubble) {
        res.json(bubble);
    }).catch(ValidationError, function (error) {
        res.status(400).json({errors: error.messages});
    })
});

router.post('/edit/:id', function(req, res) {
    var user = req.user;
    var form = req.body;

    Bubble.where({id: req.params.id}).fetch().then(function (bubble) {

        if(!bubble) { res.status(404).json({error: 'Requested bubble does not exist.'}) }
        else if(!bubble.ownedBy(user)) { res.status(403).json({error: 'You don\'t have permission to edit this bubble.'}) }
        else {
            bubble.set('title', form.title);
            bubble.set('description', form.description);
            return bubble.save();
        }

    }).then(function (bubble) {
        res.json(bubble);
    }).catch(ValidationError, function (error) {
        res.status(400).json({errors: error.messages});
    });
});

router.get('/:id/content', function (req, res) {
    Bubble.where({id: req.params.id}).fetch().then(function(bubble, err){
        if (!bubble || err){
            console.log(err);
            return general.sendMessage(res, "This bubble doesn't exist or it was deleted", 404);
        }
        Content.where({bubble_id: req.params.id}).fetchAll().then(function (contents) {
            res.json({attributes: bubble.attributes, contents: contents});
        })

    });

});

router.get('/:id', function (req, res) {
    Bubble.where({id: req.params.id}).fetch().then(function(bubble, err){
        if (!bubble || err){
            console.log(err);
            return general.sendMessage(res, "This bubble doesn't exist or it was deleted", 404);
        }

        res.json(bubble);
    })
});


module.exports = router;