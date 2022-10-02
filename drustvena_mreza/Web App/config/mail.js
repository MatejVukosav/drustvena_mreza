
var mailer = require('nodemailer');

var transporter = mailer.createTransport({
    //moj email bubbbles.mislav@gmail.com
    service: 'Gmail',
    auth: {
        user: 'bubbles.notification', //dodati svoj mail
        pass: 'Dr(!*Ezk@R48'   //dodati svoju sifru
    }
});

module.exports = {

    sendVerificationEmail: function(email,link) {

        var mailOptions = {
            from: 'Bubbles Notification', // sender address
            to: email, // list of receivers
            subject: 'Just one more step to get started on Bubbles', // Subject line
            html: '<h3>To complete your Bubbles registration, please confirm your account by following the link</h3>'+
            '<a href=' + link + '>' + link + '<//a>'// html body
        };

        // send mail with defined transport object
        transporter.sendMail(mailOptions, function(error, info){
            if(error){
                return console.log(error);
            }
            console.log('Message sent: ' + info.response);

        });
    }
};
