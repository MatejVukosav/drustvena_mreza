/**
 * Created by Domagoj on 5.12.2015..
 */
// Checks and initializes 'size' and 'from' parameters passed through context.
// Sends en empty JSON with 404 status code to response ('res') given in context if parameters are invalid.
// Returns true if parameters are valid, false otherwise.
var general = require('../utils/general');
function checkInitSizeAndFrom(context, defaultSize, defaultFrom){
    // initialize default values
    if(!context.size) {
        context.size = defaultSize;
    }

    if(!context.from){
        context.from = defaultFrom;
    }

    // check if values given aren't numbers
    if (isNaN(context.from) || isNaN(context.size)){
        general.sendMessage(context.res, 'Invalid parameters', 404);
        return false;
    }

    return true;
}

module.exports = {
    checkInitSizeAndFrom: checkInitSizeAndFrom
}