
var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;

var User = require('../models/user');

passport.use(new LocalStrategy({
   usernameField:'username',
   passwordField:'password'
},function(username, password, done) {
   User.where({username: username}).fetch().then(function(user) {
      if(user === null) {
         return done(null, false, {message: 'Invalid username or password'});
      } else {
         if(!user.validPassword(password)) {
            return done(null, false, {message: 'Invalid username or password'});
         } else if(user.get('confirmed') === 1 ){
            return done(null, user.toJSON());
         }
         else{
            return done(null,false,{message:'You should verify your email'});
         }
      }
   });
}));

passport.serializeUser(function(user, done) {
   done(null, user.username);
});

passport.deserializeUser(function(username, done) {
   User.where({username: username}).fetch().then(function(user) {
      done(null, user);
   });
});

module.exports = passport;