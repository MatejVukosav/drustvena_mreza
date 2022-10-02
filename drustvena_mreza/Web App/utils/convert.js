/**
 * Created by Domagoj on 5.12.2015..
 */

function dateToSqlFormat(date){
    return date.getUTCFullYear() + '-'
        + toTwoDigits(1 + date.getUTCMonth()) + '-'
        + toTwoDigits(date.getUTCDate()) + ' '
        + toTwoDigits(date.getUTCHours()) + ':'
        + toTwoDigits(date.getUTCMinutes()) + ':'
        + toTwoDigits(date.getUTCSeconds());
}

function toTwoDigits(value){
    if(0 <= value && value < 10){
        return '0' + value.toString();
    }

    if(-10 < value && value < 0){
        return '-0' + (-1 * value).toString();
    }

    return value.toString();
}

module.exports = {
    dateToSqlFormat: dateToSqlFormat,
    toTwoDigits: toTwoDigits
}

