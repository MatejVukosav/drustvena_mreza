/**
 * Created by Gordan on 4.12.2015..
 */

var orm = require('../config/orm');

require('./bubble');

var BubbleType = orm.Model.extend({

    tableName : 'bubble_type',

    bubbles : function() { return this.hasMany('Bubble'); }

});

module.exports = orm.model('BubbleType', BubbleType);