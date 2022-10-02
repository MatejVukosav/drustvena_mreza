/**
 * Created by Gordan on 4.12.2015..
 */

var orm = require('../config/orm');

require('./user');

var Country = orm.Model.extend({

    tableName : 'country',

    users : function() { return this.hasMany('User'); }

});

module.exports = orm.model('Country', Country);