/**
 * Created by Gordan on 9.12.2015..
 */

var orm = require('../config/orm');
var Promise = require('bluebird');
var CheckIt = require('checkit');

var ValidationError = require('./errors/validationError');

require('./user');
require('./content');

var Comment = orm.Model.extend({

    tableName : 'comment',
    hasTimestamps : true,

    user : function() { return this.belongsTo('User'); },
    contents : function() { return this.belongsTo('Content'); },

    initialize: function() {
        this.on('saving', this.onSaving, this)
    },

    onSaving: function() {
        return this.getCheckIt().run(this.attributes).catch(CheckIt.Error, Promise.method(function (error) {
            throw new ValidationError(error);
        }))
    },

    getCheckIt: function() {
        return new CheckIt(require('./validation/comment'));
    },

    ownedBy: function (user) {
        return this.get('user_id') === user.get('id');
    },

    appendAuthor: function(user) {
        var shallowCopy = this.toJSON();
        shallowCopy.first_name = user.get('first_name');
        shallowCopy.last_name = user.get('last_name');
        shallowCopy.middle_name = user.get('middle_name');
        shallowCopy.username = user.get('username');
        return shallowCopy;
    }
});

module.exports = orm.model('Comment', Comment);
