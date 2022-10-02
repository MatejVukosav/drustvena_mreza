/**
 * Created by Domagoj on 5.12.2015..
 */
function sendMessage(res, message, code){
    res.status(code);
    res.json({
        message: message
    })
}

module.exports = {
    sendMessage: sendMessage
}