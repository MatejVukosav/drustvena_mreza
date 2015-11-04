var DB = require('../../config/db').DB;
var bcrypt = require('bcrypt-nodejs');
var User = DB.Model.extend({
	 tableName: 'user',
   	 idAttribute: 'id'
});
User.validPassword = function(pass1,pass2) {
    return bcrypt.compareSync(pass1,pass2); 
};
User.generateHash = function(password) {
    return bcrypt.hashSync(password);
};

module.exports=User;