/**
 * Created by Gordan on 20.12.2015..
 */

module.exports = {
    password_hash: [
        {
            rule: 'required',
            message: 'Password is required'
        },
        {
            rule: 'minLength:8',
            message: 'Password must be at least 8 characters in length.'
        },
        {
            rule: 'maxLength:30',
            message: 'Password must be at most 30 characters in length.'
        },
        {
            rule: 'alphaDash',
            message: 'Password must consist of alphanumerics, dashes and underscores.'
        }
    ]
};
