/**
 * Created by Domagoj on 21.12.2015..
 */
var orm = require('../config/orm');

require('./user');
require('./content');

var Dislike = orm.Model.extend({

    tableName : 'dislike',
    hasTimestamps: true,

    user : function() { return this.belongsTo('User'); },
    content: function() { return this.belongsTo('Content') }

});

module.exports = orm.model('Dislike', Dislike);
