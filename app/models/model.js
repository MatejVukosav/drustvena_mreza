var DB = require('../../config/db').DB;
var bcrypt = require('bcrypt-nodejs');
var localUser={
	 tableName: 'tblUsers',
   	 idAttribute: 'userId',
}

var LocalUser = DB.Model.extend(localUser);
LocalUser.validPassword = function(pass1,pass2) {
    return bcrypt.compareSync(pass1,pass2); 
};
LocalUser.generateHash = function(password) {
    return bcrypt.hashSync(password);
};

module.exports=LocalUser;