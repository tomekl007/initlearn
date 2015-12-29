var Config = (function () {

    var appUrl = 'https://initlearn.herokuapp.com';
    var userGroupPath = '/group/users/';
    var allTeachersUrl = appUrl + userGroupPath + 'teachers';
    var registerAccountUrl = appUrl + '/registerAccount';
    var isUserLoggedInUrl = appUrl + '/isLoggedIn';
    var loggedUserUrl = appUrl + '/me';
    var myProfileHash = '#me';
    var logoutUserUrl = appUrl + '/logout';
    var userUrl = appUrl + '/users';
    var usersHash = '#users/';
    var updateUserDataUrl = function(email) {return userUrl + '/' + email + '/data'};

    var authorizationPrefix = 'Bearer ';

    return {
        appUrl: appUrl,
        userGroupPath: userGroupPath,
        allTeachersUrl: allTeachersUrl,
        registerAccountUrl: registerAccountUrl,
        isUserLoggedInUrl: isUserLoggedInUrl,
        loggedUserUrl: loggedUserUrl,
        myProfileHash: myProfileHash,
        logoutUserUrl: logoutUserUrl,
        userUrl: userUrl,
        usersHash: usersHash,
        authorizationPrefix: authorizationPrefix,
        updateUserDataUrl: updateUserDataUrl
    };
})();

module.exports = Config;
