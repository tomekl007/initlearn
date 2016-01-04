import localStorage from '../common/localStorage';

var Config = (function () {

    /*urls, paths, hashes*/
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
    var messagesOverviewUrl = messagesUrl + '/overview';
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

    /*calls*/
    var authorizationPrefix = 'Bearer ';

    var apiCallHeader = function () {

        var header = {
            'Content-Type': 'application/json'
        };

        if (localStorage.isAvailable()) {
            var userToken = window.localStorage.getItem('user-token');

            if (typeof userToken === 'string') {
                header.Authorization = userToken;
            }
        }

        return header;
    };

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
        messagesOverviewUrl: messagesOverviewUrl,
        updateUserDataUrl: updateUserDataUrl,
        addUserToTeacherGroupUrl: addUserToTeacherGroupUrl,
        updateScreenheroUrl: updateScreenheroUrl,
        getMessagesUrl: getMessagesUrl,
        apiCallHeader: apiCallHeader
    };
})();

module.exports = Config;
