var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var User = require('../app/models/user');
var bcrypt = require('bcrypt-nodejs');
module.exports=function(passport){
   passport.use(new LocalStrategy({
      usernameField:'username',
      passwordField:'password'
   },function(username, password, done) {
      new User({'username':username}).fetch()
      .then(function(data) {
         var user = data;
         if(user === null) {
            return done(null, false, {message: 'Invalid username or password'});
         } else {
            user = data.toJSON();
            if(!User.validPassword(password,user.password_hash)) {
               return done(null, false, {message: 'Invalid username or password'});
            } else {
               return done(null, user);
            }
         }
      });
   }));

   passport.serializeUser(function(user, done) {
    done(null, user.username);
   });

   passport.deserializeUser(function(username, done) {
      new User({username: username}).fetch().then(function(user) {
         done(null, user);
      });
   });
};