/**
 * Created by Gordan on 4.12.2015..
 */

var orm = require('../config/orm');

require('./user');

var RelationshipStatus = orm.Model.extended({

    tableName : 'relationship_status',

    users : function() { return this.hasMany('User'); }

});

module.exports = orm.model('RelationshipStatus', RelationshipStatus);