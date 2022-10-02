/**
 * Created by Domagoj on 3.12.2015..
 */
var express = require('express');
var router = express.Router();

var User = require('../models/user');

var requireAuthentication = require('../utils/authentication');

router.all('*', requireAuthentication);

router.get('/', function(req, res, next) {
    var query = '%' + req.query.query + '%';
    var size = req.query.size;
    var from = req.query.from;

    // log query
    console.log('Search with query: ' + query + ' size: ' +  size + ' from: ' + from);

    // initialize default values
    if(!size) {
        size = 10;
    }

    if(!from){
        from = 0;
    }

    // check if values given aren't numbers
    if (isNaN(from) || isNaN(size)){
        return sendErr(res);
    }

    var results = {
        users: []
    };

    // find users that match the query
    var userPromise = new User().query(function (qb) {
            qb
                .where('username', 'LIKE', query)
                .orWhere('first_name', 'LIKE', query)
                .orWhere('last_name', 'LIKE', query)
            ;
        })
        .fetchAll().then(function (collection) {
            var count = collection.models.length;

            if (count < from + size) {
                size = count;
            }

            if (from > size) {
                from = size;
            }

            for (var i = from; i < size; i++) {
                var item = collection.at(i);
                console.log(item.attributes);
                results.users.push({
                    username: item.attributes.username,
                    first_name: item.attributes.first_name,
                    last_name: item.attributes.last_name
                });
            }
            
            if(req.query.html == 1) {
                res.render('search-results.partial.ejs', {results: JSON.stringify(results)});
            } else {
                res.json(results);
            }
        });

});

function sendErr(res){
    res.status(404);
    return res.json({});
}

module.exports = router;