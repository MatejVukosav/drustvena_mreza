/**
 * Created by Gordan on 9.12.2015..
 */

var User = require('../../models/user');

exports.seed = function(knex, Promise) {
    return Promise.join(

        User.where({username: 'kolinda'}).fetch().then(function (user) {
            return Promise.join(
                user.createBubble({title: 'Kampanja'}),
                user.createBubble({title: 'HDZ'}),
                user.createBubble({title: 'Balon'})
            );
        }),
        User.where({username: 'user'}).fetch().then(function (user) {
            return Promise.join(
                user.createBubble({title: 'Korisnicki'}),
                user.createBubble({title: 'Fali mi maste'}),
                user.createBubble({title: 'Balonchekiiii'}),
                user.createBubble({title: 'Bozic'})
            );
        })

    );
};