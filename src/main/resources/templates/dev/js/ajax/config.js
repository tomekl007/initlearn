var Config = (function () {

    var appUrl = 'https://initlearn.herokuapp.com';
    var userGroupPath = '/group/users/';
    var allTeachersUrl = appUrl + userGroupPath + 'teachers';
    var isUserLoggedInUrl = appUrl + '/isLoggedIn';
    var loggedUserUrl = appUrl + '/me';
    var logoutUserUrl = appUrl + '/logout';
    var userUrl = appUrl + '/users';
    var usersHash = '#users/';

    return {
        appUrl: appUrl,
        userGroupPath: userGroupPath,
        allTeachersUrl: allTeachersUrl,
        isUserLoggedInUrl: isUserLoggedInUrl,
        loggedUserUrl: loggedUserUrl,
        logoutUserUrl: logoutUserUrl,
        userUrl: userUrl,
        usersHash: usersHash
    };
})();

module.exports = Config;
