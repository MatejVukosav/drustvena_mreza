
var config = require('../knexfile');
var knex = require('knex')(config['development']);

var orm = require('bookshelf')(knex);
orm.plugin('registry');
orm.plugin('visibility');

module.exports = orm;