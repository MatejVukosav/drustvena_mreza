/**
 * Created by Gordan on 20.12.2015..
 */

var orm = require('../../config/orm');

var User = orm.model('User');
var Country = orm.model('Country');

module.exports = function(user) {
    return {
        username: [
            {
                rule: 'required',
                message: 'Username is required.'
            },
            {
                rule: 'minLength:3',
                message: 'Username must be at least 3 characters in length.'
            },
            {
                rule: 'maxLength:30',
                message: 'Username must be at most 30 characters in length'
            },
            {
                rule: 'alphaDash',
                message: 'Username must consist of alphanumerics, dashes and underscores.'
            },
            function (username) {
                return User.where({username: username}).fetch().then(function (fetchedUser) {
                    if (fetchedUser && fetchedUser.id !== user.id && fetchedUser.username === user.username){
                        throw new Error('Username already exists.');
                    }
                })
            }
        ],
        email: [
            {
                rule: 'required',
                message: 'Email is required.'
            },
            {
                rule: 'email',
                message: 'Invalid email format.'
            },
            function (email) {
                return User.where({email: email}).fetch().then(function (fetchedUser) {
                    if (fetchedUser && fetchedUser.id !== user.id && fetchedUser.email === user.email) {
                        throw new Error('Email already exists.');
                    }
                })
            }
        ],
        first_name: [
            {
                rule: 'maxLength:35',
                message: 'First name must be at most 35 characters in length.'
            }
        ],
        last_name: [
            {
                rule: 'maxLength:35',
                message: 'Last name must be at most 35 characters in length.'
            }
        ],
        middle_name: [
            {
                rule: 'maxLength:35',
                message: 'Middle name must be at most 35 characters in length.'
            }
        ],
        country_name: [
            function(country) {
                return Country.where({name: country}).fetch().then(function (fetchedCountry) {
                    if(!fetchedCountry) {
                        throw new Error('Country does not exist.');
                    }
                })
            }
        ]
    };
}
