/**
 * Created by Mislav on 11/27/2015.
 */

var Promise = require('bluebird');

var User = require('../models/user');
var Bubble = require('../models/bubble');
var Content = require('../models/content');
var Privilege = require('../models/privilege')

var express = require('express');
var router = express.Router();

var requireAuthentication = require('../utils/authentication');

router.all('*', requireAuthentication);

router.get('/feed', function(req, res) {
    var user = req.user;

    Promise.join(
        getContents(),
        getLikesOrDislikes('like', 'myLike', 'likes', 'iLike'),
        getLikesOrDislikes('dislike', 'myDislike', 'dislikes', 'iDislike'),
        function (_contents, _likes, _dislikes){
            var contents = _contents.toJSON();
            var likes = _likes.toJSON();
            var dislikes = _dislikes.toJSON();
            for(var i = 0; i < _contents.length; i++) {
                contents[i]['likes'] = likes[i].count;
                contents[i]['dislikes'] = dislikes[i].count;
                contents[i]['iLike'] = likes[i].bool;
                contents[i]['iDislike'] = dislikes[i].bool;
            }

            res.json({contents: contents});
    }).catch(function (error) {
        console.log(error.stack);
    });

    function getContents() {
        return Privilege.query(function (qb) {
            qb.join('bubble', 'privilege.permitter_id', 'bubble.user_id')
                .join('content', 'content.bubble_id', 'bubble.id')
                .join('user', 'bubble.user_id', 'user.id')
                .where('privilege.permittee_id', user.get('id'))
                .columns('content.*', 'username').union(function () {
                this.from('bubble')
                    .join('content', 'content.bubble_id', 'bubble.id')
                    .join('user', 'bubble.user_id', 'user.id')
                    .where('user.id', user.get('id'))
                    .columns('content.*', 'username')
                    .orderBy('created_at', 'DESC');
            })
        }).fetchAll();
    }

    function getLikesOrDislikes(tableName) {
        return Privilege.query(function (qb) {
            qb.join('bubble', 'privilege.permitter_id', 'bubble.user_id')
                .join('content', 'content.bubble_id', 'bubble.id')
                .leftJoin(tableName, tableName + '.content_id', 'content.id')
                .leftJoin(tableName + ' as alias', function () {
                    this.on('alias.content_id', tableName + '.content_id').andOn('alias.user_id', tableName + '.user_id').andOn('alias.user_id', user.get('id'))
                })
                .where('privilege.permittee_id', user.get('id'))
                .groupBy('content.bubble_id', 'content.id', 'content.content_type_id', 'content.created_at', 'content.title', 'content.content')
                .columns('content.created_at')
                .count(tableName + '.user_id as count')
                .count('alias.user_id as bool').union(function () {
                this.from('bubble')
                    .join('content', 'content.bubble_id', 'bubble.id')
                    .join('user', 'bubble.user_id', 'user.id')
                    .leftJoin(tableName, tableName + '.content_id', 'content.id')
                    .leftJoin(tableName + ' as alias', function () {
                        this.on('alias.content_id', tableName + '.content_id').andOn('alias.user_id', tableName + '.user_id').andOn('alias.user_id', user.get('id'))
                    })
                    .where('user.id', user.get('id'))
                    .groupBy('content.bubble_id', 'content.id', 'content.content_type_id', 'content.created_at', 'content.title', 'content.content')
                    .columns('content.created_at')
                    .count(tableName + '.user_id as count')
                    .count('alias.user_id as bool')
                    .orderBy('created_at', 'DESC');
            })
        }).fetchAll();
    }
});

module.exports=router;