/**
 * Created by Gordan on 10.12.2015..
 */

function ValidationError(checkItError) {
    this.messages = [];
    var temp = this.messages;
    checkItError.forEach(function (val, key) {
        val.forEach(function(message) {
            temp.push(message.message);
        })
    });
    this.stack = (new Error()).stack;
}

ValidationError.prototype = Object.create(Error.prototype);
ValidationError.prototype.name = 'ValidationError';

module.exports = ValidationError;