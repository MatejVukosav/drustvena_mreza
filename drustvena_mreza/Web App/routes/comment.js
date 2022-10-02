/**
 * Created by Gordan on 19.12.2015..
 */

var express = require('express');
var router = express.Router();

var Comment = require('../models/comment');

var requireAuthentication = require('../utils/authentication');

var ValidationError = require('../models/errors/validationError');

router.all('*', requireAuthentication);

router.post('/edit/:id', function(req, res) {
    var form = req.body;
    var user = req.user;

    Comment.where({
        id: req.params.id
    }).fetch().then(function (comment) {
        if(!comment) { res.status(404).json({error: 'Requested comment does not exist.'}) }
        else if(!comment.ownedBy(user)) { res.status(403).json({error: 'You don\'t have permission to edit this comment.'}) }
        else {
            comment.set('comment', form.comment);
            return comment.save();
        }
    }).then(function (comment) {
        res.json(comment.appendAuthor(user));
    }).catch(ValidationError, function(error) {
        res.status(400).json({errors: error.messages});
    })
});

module.exports = router;