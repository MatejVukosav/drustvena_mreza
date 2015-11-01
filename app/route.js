// vendor library
var passport = require('passport');
var bcrypt = require('bcrypt-nodejs');
// custom library
// model
var LocalUser = require('./models/model');

module.exports=function(app,passport){
   // GET
   app.get('/', function(req, res, next) {
      if(!req.isAuthenticated()) {
         res.redirect('/signin');
      } else {
         var user = req.user;
         if(user !== undefined) {
            user = user.toJSON();
         }
         res.render('index', {title: 'Home', user: user});
      }
   });

// signin
// GET
   app.get('/signin', function(req, res, next) {
      if(req.isAuthenticated()) res.redirect('/');
      res.render('signin', {title: 'Sign In'});
   });
// POST  
   app.post('/signin', function(req, res, next) {
      passport.authenticate('local', { successRedirect: '/',
                          failureRedirect: '/signin'}, function(err, user, info) {
         if(err) {
            return res.render('signin', {title: 'Sign In', errorMessage: err.message});
         } 

         if(!user) {
            return res.render('signin', {title: 'Sign In', errorMessage: info.message});
         }
         return req.logIn(user, function(err) {
            if(err) {
               return res.render('signin', {title: 'Sign In', errorMessage: err.message});
            } else {
               return res.redirect('/');
            }
         });
      })(req, res, next);
   });
// signup
// GET
   app.get('/signup', function(req, res, next) {
      if(req.isAuthenticated()) {
         res.redirect('/');
      } else {
         res.render('signup', {title: 'Sign Up'});
      }
   });
// POST
   app.post('/signup', function(req, res, next) {
      var user = req.body;
      var usernamePromise = null;
      usernamePromise = new LocalUser({username: user.username}).fetch();
      return usernamePromise.then(function(model) {
         if(model) {
            res.render('signup', {title: 'signup', errorMessage: 'username already exists'});
         } else {
            //****************************************************//
          // MORE VALIDATION GOES HERE(E.G. PASSWORD VALIDATION)
            //****************************************************//
            var password = user.password;
            var hash = LocalUser.generateHash(password);
            var signUpUser = new LocalUser({username: user.username, password: hash});
            signUpUser.save().then(function(model) {
               // sign in the newly registered user
               res.redirect(307,'/signin');
            });   
         }
      });
   });

// logout
// GET
   app.get('/signout', function(req, res, next) {
      if(!req.isAuthenticated()) {
         notFound404(req, res, next);
      } else {
         req.logout();
         res.redirect('/signin');
      }
   });

/********************************/

/********************************/
// 404 not found
   app.use(function(req, res, next) {
   res.status(404);
   res.render('404', {title: '404 Not Found'});
   });
};