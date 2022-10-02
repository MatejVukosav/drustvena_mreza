/**
 * Created by Domagoj on 23.12.2015..
 */
var express = require('express');
var router = express.Router();
var ValidationError = require('../models/errors/validationError');
var Content = require('../models/content');
var Bubble = require('../models/bubble');
var User = require('../models/user');
var general = require('../utils/general');
var requireAuthentication = require('../utils/authentication');
router.all("*", requireAuthentication);

router.get("/edit/:id",function(req,res,next){

    Content.where({id: req.params.id, content_type_id: 2}).fetch().then(function(image, err){
        if (err){
            console.log(err);
        }

        if (!image){
            return general.sendMessage(res, "This image doesn't exist", 404);
        }

        // ako treba za debug console.log(image);

        Bubble.where({id: image.attributes.bubble_id, user_id: req.user.id}).fetch().then(function(bubble, err){
            if (err){
                console.log(err);
            }

            if (!bubble){
                return general.sendMessage(res, "You don't have permission to edit this image", 403);
            }

            // ako treba za debug console.log(bubble);

            return res.render('edit-image.ejs', {user: req.user.toJSON()});
        });
    })
});

router.get("/edit/",function(req,res,next){

    return res.render('edit-image.ejs', {user: req.user.toJSON()});

});

module.exports=router;