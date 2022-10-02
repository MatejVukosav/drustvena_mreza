/**
 * Created by Gordan on 4.12.2015..
 */

var orm = require('../config/orm');
var Promise = require('bluebird');
var CheckIt = require('checkit');
var ValidationError = require('./errors/validationError');

require('./user');
require('./bubble_type');
require('./content');

var Bubble = orm.Model.extend({

    tableName : 'bubble',
    hasTimestamps : true,

    user : function() { return this.belongsTo('User'); },
    bubble_type : function() { return this.belongsTo('BubbleType'); },
    contents : function() { return this.hasMany('Content'); },

    initialize: function() {
        this.on('saving', this.onSaving, this)
    },

    onSaving: function() {
        return this.getCheckIt().run(this.attributes).catch(CheckIt.Error, Promise.method(function (checkItError) {
            throw new ValidationError(checkItError);
        }));
    },

    getCheckIt: function () {
        return new CheckIt(require('./validation/bubble'));
    },

    format: function(attributes) {
        attributes.description = attributes.description || null;

        return attributes;
    },

    ownedBy: function(user) {
        return this.get('user_id') === user.get('id');
    }
});

module.exports = orm.model('Bubble', Bubble);
