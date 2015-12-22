var Config = (function () {

    var appUrl = 'https://initlearn.herokuapp.com';
    var userGroupPath = '/group/users/';
    var allTeachersUrl = appUrl + userGroupPath + 'teachers';
    var userUrl = appUrl + '/users';
    var usersHash = '#users/';

    return {
        appUrl: appUrl,
        userGroupPath: userGroupPath,
        allTeachersUrl: allTeachersUrl,
        userUrl: userUrl,
        usersHash: usersHash
    };
})();

module.exports = Config;
