/**
 * Created by Domagoj on 5.12.2015..
 */
/**
 * Created by Domagoj on 3.12.2015..
 */
var express = require('express');
var router = express.Router();
var gm = require('gm');
var fs = require('fs');
var md5 = require('md5');

var ValidationError = require('../models/errors/validationError');

var Content = require('../models/content');
var Bubble = require('../models/bubble');
var User = require('../models/user');
var Comment = require('../models/comment');
var Like = require('../models/like');
var Dislike = require('../models/dislike');

var convert = require('../utils/convert');
var arrays = require('../utils/arrays');
var params = require('../utils/params');
var general = require('../utils/general');

var multer  = require('multer');
var storage = multer.memoryStorage();
var upload = multer({ storage: storage });

var requireAuhentication = require('../utils/authentication');

router.all("*", requireAuhentication);

// post post with post :P
// POST
router.post('/post/:bubble_id', function(req, res, next) {

    var context = {
        bubbleId: req.params.bubble_id,
        typeId: 1,
        title: req.body.title,
        description: req.body.description,
        content: req.body.content,
        res: res,
        req: req
    }

    return parseContent(context);
});

// POST api/content/image/:bubble_id
// ubacuje sliku u dani bubble
// očekuje sljedeće form parametre:
// - title - naslov slike
// - description - opis slike
// - content - binarni sadržaj slike
// slati kao multipart/form-data, a ne obični form!
router.post('/image/:bubble_id', upload.single('content'), function(req, res, next) {

    var context = {
        bubbleId: req.params.bubble_id,
        typeId: 2,
        title: req.body.title,
        description: req.body.description,
        content: req.file,
        imgPath: './res/img/',
        filename:  md5(Date.now().toString() + req.file.originalname) + '_'
        + req.file.originalname,
        res: res,
        req: req
    }

    console.log('Received file: ');
    console.log(req.file);
    console.log(' from: ' +  req.user.id);
    return parseContent(context);

});

// Gets latest posts in the specified bubble
// parameters:  post_id - id of the post to get

// GET api/post/:post_id
router.get('/post/:post_id', function(req, res, next) {

    Content.where({id: req.params.post_id, content_type_id: 1}).fetch().then(function (post){
        if (!post){
            return general.sendMessage(res, "This post doesn't exist or it was deleted!", 404);
        }

        return res.status(200).json(post);
    })
});

// GET api/image/:image_id
// vraća OPISNIK slike s danim id-em
// opisnik slike je json koji u potpunosti odgovara opisu slike u bazi.
router.get('/image/:image_id', function(req, res, next) {

    Content.where({id: req.params.image_id, content_type_id: 2}).fetch().then(function (image){
        if (!image){
            return general.sendMessage(res, "This image doesn't exist or it was deleted!", 404);
        }

        return res.status(200).json(image);
    })
});

router.post('/edit/post/:id', function(req, res, next){
    return editContent(req, res, 1);
})

router.post('/edit/image/:id', upload.single('content'), function(req, res, next){
    return editContent(req, res, 2);
})

router.post('/delete/:id', function(req, res, next){
    var contentId = req.params.id;

    Content.where({id: contentId}).fetch().then(function(content){
        if(!content){
            general.sendMessage(res, "This post doesn't exist or it was deleted!", 404);
        } else {
            Bubble.where({id: content.attributes.bubble_id}).fetch().then(function(bubble){
                if (!req.isAuthenticated() || bubble.attributes.user_id != req.user.id){
                    return general.sendMessage(res, "You don't have permission to delete this content!", 403)
                } else {
                    Content.where({id: contentId}).destroy().then(function(destroyed){
                        res.status(200);
                        return res.json(destroyed);
                    })
                }
            })
        }
    })

})

router.get('/timeline', function(req, res) {
    var user = req.user;
    Content.query(function (qb) {
        qb.join('bubble', 'content.bubble_id', 'bubble.id')
            .leftJoin('like', 'like.content_id', 'content.id')
            .where('bubble.user_id', user.id)
            .andWhere(function () {
                this.where('bubble_type_id', 1).orWhere('bubble_type_id', 3);
            }).groupBy('content.id', 'content.created_at', 'content.updated_at', 'content.title', 'content.content', 'content.description')
            .columns('content.id', 'content.created_at', 'content.updated_at', 'content.title', 'content.content', 'content.description')
            .count('like.content_id as likes')
            .orderBy('created_at', 'DESC');
    }).fetchAll().then(function (posts) {
        res.json( {posts: posts} );
    });
});

router.get('/gallery', function(req, res) {
    var user = req.user;
    Content.query(function (qb) {
        qb.join('bubble', 'content.bubble_id', 'bubble.id')
            .leftJoin('like', 'like.content_id', 'content.id')
            .where('bubble.user_id', user.id)
            .andWhere(function () {
                this.where('bubble_type_id', 2);
            }).groupBy('content.id', 'content.created_at', 'content.updated_at', 'content.title', 'content.content', 'content.description')
            .columns('content.id', 'content.created_at', 'content.updated_at', 'content.title', 'content.content', 'content.description')
            .count('like.content_id as likes')
            .orderBy('created_at', 'DESC');
    }).fetchAll().then(function (posts) {
        res.json( {posts: posts} );
    });
});

router.get('/myBubbles', function (req, res) {
    Bubble.query(function(qb) {
        qb.where({user_id: req.user.id}).andWhere(function () {
            this.where('bubble_type_id', 1).orWhere('bubble_type_id', 3);
        });
    }).fetchAll().then(function (bubbles) {
        res.json({bubbles: bubbles});
    });
});

router.get('/comments/:content_id', function (req, res) {
    Comment.query(function (qb) {
        qb.join('user', 'comment.user_id', 'user.id')
            .where('content_id', req.params.content_id)
            .orderBy('comment.created_at', 'desc');
    }).fetchAll({columns: ['comment.*', 'first_name', 'last_name', 'middle_name', 'username']}).then(function(comments) {
        res.json({comments: comments});
    })
});

router.post('/comment/:content_id', function(req, res, next) {
    Comment.forge({
        user_id: req.user.id,
        content_id: req.params.content_id,
        comment: req.body.comment
    }).save().then(function () {
        res.end();
    });
});

router.post('/like/:id', function (req, res) {
    Like.where({
        user_id: req.user.id,
        content_id: req.params.id
    }).fetch().then(function (like) {
        if(like) {
            return Like.where({
                user_id: req.user.id,
                content_id: req.params.id
            }).destroy();
        } else {
            return Like.forge({
                user_id: req.user.id,
                content_id: req.params.id
            }).save()
        }
    }).then(function () {
        res.end();
    });
});

router.post('/dislike/:id', function (req, res) {
    Dislike.where({
        user_id: req.user.id,
        content_id: req.params.id
    }).fetch().then(function (dislike) {
        if(dislike) {
            return Dislike.where({
                user_id: req.user.id,
                content_id: req.params.id
            }).destroy();
        } else {
            return Dislike.forge({
                user_id: req.user.id,
                content_id: req.params.id
            }).save()
        }
    }).then(function () {
        res.end();
    });
});

router.get('/likes/:id', function (req, res) {
    Like.query(function(qb) {
        qb.join('user', 'user.id', 'like.user_id')
            .where('content_id', req.params.id)
            .columns(['first_name', 'last_name', 'middle_name', 'username']);
    }).fetchAll().then(function (users) {
        res.json({users: users});
    })
});

router.get('/dislikes/:id', function (req, res) {
    Dislike.query(function(qb) {
        qb.join('user', 'user.id', 'dislike.user_id')
            .where('content_id', req.params.id)
            .columns(['first_name', 'last_name', 'middle_name', 'username']);
    }).fetchAll().then(function (users) {
        res.json({users: users});
    })
});

function parseContent(context){

    Bubble.where({id: context.bubbleId}).fetch().then(function (bubble){
        if (!bubble){
            return general.sendMessage(context.res, "This bubble doesn't exist or it was deleted!", 404);
        }

        if (bubble.attributes.bubble_type_id == 1 && context.typeId != 1){
            return general.sendMessage(context.res, "Only posts can be posted in timeline bubbles!", 403);
        }

        if (bubble.attributes.bubble_type_id == 2 && context.typeId != 2){
            return general.sendMessage(context.res, "Only images can be posted in gallery bubbles!", 403);
        }

        if (!context.req.isAuthenticated() || bubble.attributes.user_id != context.req.user.id){
            return general.sendMessage(context.res, "You don't have permission to post in this bubble!", 403);
        }

        if (context.typeId == 1){
            return handlePost(context);
        }

        if (context.typeId == 2){
            return handleImg(context);
        }
    })

}

// not yet tested
function handleImg(context){
    var loc = context.imgPath + context.filename;
    fs.writeFile(loc, context.content.buffer, { encoding: 'ascii', mode: 0666, flag: 'w+'}, function(err){
        if (err){
            console.log(err);
            return general.sendMessage(context.res, "Failed to write the image.", 500);
        }

        gm(loc)
            .resize(125, 125)
            .autoOrient()
            .write(context.imgPath + 'small' + context.filename, function (err) {
                if (err) {
                    console.log(err);
                }
            });
        gm(loc)
            .resize(500)
            .autoOrient()
            .write(context.imgPath + 'medium' + context.filename, function(err) {
                if (err) {
                    console.log(err);
                }
            });
        gm(loc)
            .resize(1280)
            .autoOrient()
            .write(context.imgPath + 'large' + context.filename, function(err) {
                if (err) {
                    console.log(err);
                }
            });

        var image = {
            content: loc,
            bubble_id: context.bubbleId,
            title: context.title,
            description: context.description,
            content_type_id: context.typeId
        }

        if (context.content_id){
            image.id = context.content_id;
        }

        Content.forge(image).save().then(function(finished, err){
            if (err){
                return general.sendMessage(context.res, "Failed to save the image.", 500);
            }
            context.res.status(200);
            return context.res.json(finished);
        })
    });

}

function handlePost(context){

    console.log(context.content);

    var post = {
        content: context.content,
        bubble_id: context.bubbleId,
        title: context.title,
        description: context.description,
        content_type_id: context.typeId
    }

    if (context.content_id){
        post.id = context.content_id;
    }

    Content.forge(post).save().then(function(finished, err){
        if (err){
            return general.sendMessage(context.res, "Failed to save the post.", 500);
        }
        context.res.status(200);
        return context.res.json(finished);
    })
}

function editContent(req, res, type){
    Content.where({id: req.params.id, content_type_id: type}).fetch().then(function(content){
        if(!content) {
            return general.sendMessage(res, "This content doesn't exist or it was deleted!", 404);
        }

        console.log(content);
        Bubble.where({id: content.attributes.bubble_id}).fetch().then(function(bubble){
            if (!req.isAuthenticated() || bubble.attributes.user_id != req.user.id){
                    return general.sendMessage(res, "You don't have permission to edit this content!", 403)
            }

            var context = {
                content_id: content.id,
                bubbleId: bubble.id,
                typeId: type,
                title: req.body.title,
                description: req.body.description,
                res: res,
                req: req
            }

            if (type == 1){
                context.content = req.body.content;
            }


            if (type == 2){
                context.content = req.file;
                context.imgPath = './res/img/';
                context.filename = md5(Date.now().toString() + req.file.originalname) + '_'
                    + req.file.originalname
            }

            return parseContent(context);
            })
    })
}

function replaceAll(string, toReplace, replacement) {
    return string.split(toReplace).join(replacement);
}

module.exports = router;