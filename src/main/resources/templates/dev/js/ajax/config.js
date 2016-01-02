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
    var messagesHash = '#msg/';
    var messagesUrl = appUrl + '/msg';
    var updateUserDataUrl = function (email) {
        return userUrl + '/' + email + '/data';
    };
    var addUserToTeacherGroupUrl = function (email) {
        return userUrl + '/' + email + '/teachers';
    };
    var updateScreenheroUrl = function (email) {
        return userUrl + '/' + email + '/screenhero';
    };
    var getMessagesUrl = function (email) {
        return messagesUrl + '/' + email;
    };

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
        messagesHash: messagesHash,
        messagesUrl: messagesUrl,
        updateUserDataUrl: updateUserDataUrl,
        addUserToTeacherGroupUrl: addUserToTeacherGroupUrl,
        updateScreenheroUrl: updateScreenheroUrl,
        getMessagesUrl: getMessagesUrl
    };
})();

module.exports = Config;
