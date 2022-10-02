/**
 * Created by Gordan on 4.12.2015..
 */

var orm = require('../config/orm');

require('./bubble');
require('./content_type');

var Content = orm.Model.extend({

    tableName : 'content',
    hasTimestamps : true,

    bubble : function() { return this.belongsTo('Bubble'); },
    contentType : function() { return this.belongsTo('ContentType'); },
    comments : function() { return this.hasMany('Comment'); },
    likes: function() { return this.belongsToMany('Like', 'like', 'content_id', 'user_id') },
    dislikes: function() { return this.belongsToMany('Dislike', 'dislike', 'content_id', 'user_id') }

});

module.exports = orm.model('Content', Content);