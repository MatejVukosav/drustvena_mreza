/**
 * Created by Gordan on 17.12.2015..
 */

var orm = require('../config/orm');

require('./user');
require('./content');

var Like = orm.Model.extend({

    tableName : 'like',
    hasTimestamps: true,

    user : function() { return this.belongsTo('User'); },
    content: function() { return this.belongsTo('Content') }

});

module.exports = orm.model('Like', Like);
