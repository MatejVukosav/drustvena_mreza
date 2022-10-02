/**
 * Created by Gordan on 19.12.2015..
 */

module.exports = {
    title: [
        {
            rule: 'required',
            message: 'A bubble must have a title.'
        },
        {
            rule: 'maxLength:64',
            message: 'A bubble title is limited to 64 characters.'
        }
    ],
    description: [
        {
            rule: 'maxLength:255',
            message: 'A bubble description is limited to 255 characters.'
        }
    ]
};