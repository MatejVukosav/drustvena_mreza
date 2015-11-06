var Bookshelf = require('bookshelf');

var config = {
   host: 'localhost',  // your host
   user: 'root', // your database user
   password: 'root', // your database password
   database: 'drustvena_mreza',
   charset: 'utf8',
	port: '3306'
};

var DB = Bookshelf.initialize({
   client: 'mysql', 
   connection: config
});

module.exports.DB = DB;
