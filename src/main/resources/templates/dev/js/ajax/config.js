var Config = (function () {

    var appUrl = 'https://initlearn.herokuapp.com';
    var userGroupPath = '/group/users/';
    var allTeachersUrl = appUrl + userGroupPath + 'teachers';
    var isUserLoggedInUrl = appUrl + '/isLoggedIn';
    var loggedUserUrl = appUrl + '/me';
    var userUrl = appUrl + '/users';
    var usersHash = '#users/';

    var authorizationPrefix = 'Bearer ';

    return {
        appUrl: appUrl,
        userGroupPath: userGroupPath,
        allTeachersUrl: allTeachersUrl,
        isUserLoggedInUrl: isUserLoggedInUrl,
        loggedUserUrl: loggedUserUrl,
        userUrl: userUrl,
        usersHash: usersHash,
        authorizationPrefix: authorizationPrefix
    };
})();

module.exports = Config;
