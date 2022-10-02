/**
 * Created by Gordan on 15.12.2015..
 */

var orm = require('../config/orm');

require('./user');

var Privilege = orm.Model.extend({

    tableName : 'privilege',

    permitter: function() { return this.belongsTo('User', 'permitter_id') },
    permittee: function() { return this.belongsTo('User', 'permittee_id') }
});

module.exports = orm.model('Privilege', Privilege);