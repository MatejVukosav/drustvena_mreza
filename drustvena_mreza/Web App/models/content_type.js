/**
 * Created by Gordan on 4.12.2015..
 */

var orm = require('../config/orm');

require('./content');

var ContentType = orm.Model.extend({

    tableName : 'content_type',

    contents : function() { return this.hasMany('Content'); }

});

module.exports = orm.model('ContentType', ContentType);
