/**
 * Created by Gordan on 12.12.2015..
 */

var orm = require('../config/orm');

require('./user');

var Gender = orm.Model.extend({

    tableName : 'gender',

    users : function() { return this.hasMany('User'); }

});

module.exports = orm.model('Gender', Gender);
