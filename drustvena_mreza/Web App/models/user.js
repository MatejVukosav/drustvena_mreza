
var orm = require('../config/orm');

var CheckIt = require('checkit');
var Promise = require('bluebird');
var Mail = require('../config/mail');
var bCrypt = Promise.promisifyAll(require('bcrypt-nodejs'));
var fs = Promise.promisifyAll(require('fs'));
var path = require('path');

var ValidationError = require('./errors/validationError');

var Country = require('./country');
require('./relationship_status');
var Bubble = require('./bubble');
var Comment = require('./comment');
require('./gender');

var User = orm.Model.extend({

    tableName: 'user',
    hidden: ['password_hash'],
    hasTimestamps : true,

    country : function () { return this.belongsTo('Country'); },
    relationshipStatus : function() { return this.belongsTo('RelationshipStatus'); },
    bubbles : function() { return this.hasMany('Bubble'); },
    comments: function() { return this.hasMany('Comment'); },
    gender: function() { return this.belongsTo('Gender'); },
    privilege: function() { return this.belongsToMany('User', 'privilege', 'permitter_id', 'permittee_id') },
    likes: function() { return this.belongsToMany('Like', 'like', 'user_id', 'content_id') },
    dislikes: function() { return this.belongsToMany('Dislike', 'dislike', 'user_id', 'content_id') },

    initialize : function() {
        this.on('created', this.onCreated, this);
        this.on('saving', this.onSaving, this)
    },

    onSaving: function () {
        return this.getUserCheckIt().run(this.attributes)
            .then(this.resolveCountry.bind(this))
            .then(this.hash.bind(this))
            .catch(CheckIt.Error, Promise.method(function(checkItError) {
                throw new ValidationError(checkItError);
            }));
    },

    onCreated: function() {
        var user = this;
        return Promise.all([
            Bubble.forge({user_id: user.get('id'), bubble_type_id: 1, title: 'Timeline'}).save(),
            Bubble.forge({user_id: user.get('id'), bubble_type_id: 2, title: 'Gallery'}).save(),
            this.sendConfirmationMail()
        ]);
    },

    resolveCountry :  function() {
        var user = this;
        return Country.where({name: this.get('country_name')}).fetch().then(function (country) {
            user.set('country_id', (country ? country.id : null));
        })
    },

    hash : Promise.method(function() {
        var user = this;
        if(user.hasChanged('password_hash')) {
            return bCrypt.genSaltAsync(10).then(function (salt) {
                return bCrypt.hashAsync(user.get('password_hash'), salt, null);
            }).then(function (hash) {
                user.set('password_hash', hash);
            });
        }
    }),

    sendConfirmationMail: Promise.method(function() {
        Mail.sendVerificationEmail(this.get('email'), "localhost:8080/emailverification?id=" + this.get('id') + "&hash=" + this.get('password_hash'));
    }),

    format: function(attributes) {
        attributes.first_name = attributes.first_name || null;
        attributes.last_name = attributes.last_name || null;
        attributes.middle_name = attributes.middle_name || null;
        attributes.city = attributes.city || null;
        attributes.address = attributes.address || null;
        attributes.gender_id = attributes.gender_id || null;
        attributes.relationship_status_id = attributes.relationship_status_id || null;

        delete attributes.country_name;
        return attributes;
    },

    getUserCheckIt : function () {
        var user = this;

        var checkIt = new CheckIt(require('./validation/user')(user));

        var passwordCheck = require('./validation/password');

        return checkIt.maybe(passwordCheck, function(password_hash) {
            return user.hasChanged('password_hash');
        });
    },

    createBubble : function(attributes) {
        attributes['bubble_type_id'] = 3;
        attributes['user_id'] = this.get('id');
        return Bubble.forge(attributes).save();
    },

    validPassword: function(password) {
        return bCrypt.compareSync(password, this.get('password_hash'));
    }

});

module.exports=orm.model('User', User);