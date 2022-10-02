/**
 * Created by Gordan on 19.12.2015..
 */

var express = require('express');
var router = express.Router();

var Country = require('../models/country');
var Gender = require('../models/gender');

router.get('/countries', function (req, res) {
    Country.query(function (qb) {
        qb.orderBy('name');
    }).fetchAll().then(function (countries) {
        res.json({countries: countries});
    })
});

router.get('/genders', function (req, res) {
    Gender.fetchAll().then(function (genders) {
        res.json({genders: genders});
    })
});

module.exports = router;