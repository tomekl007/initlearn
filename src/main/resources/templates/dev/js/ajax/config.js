import localStorage from '../common/localStorage';

var Config = (function () {

    /*urls, paths, hashes*/
    var appUrl = 'https://initlearn.herokuapp.com';
    var authTokenUrl = appUrl + '/oauth/token';
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
    var addUserToTeacherGroupUrl = userUrl + '/teachers';
    var updateScreenheroUrl = userUrl + '/screenhero';
    var updateUserDataUrl = userUrl + '/data';
    var searchUrl = 'search?';
    var searchTeachersBySkillPath = usersHash + searchUrl + 'skill=';
    var paymentPath = '/adaptivePayment?toEmail=';
    var searchAutocompleteOptionsUrl = appUrl + '/skills';

    var getMessagesUrl = function (email) {
        return messagesUrl + '/' + email;
    };

    var userRatingUrl = function(email) {
      return userUrl + '/' + email + '/rate';
    };

    var updateUserDataField = function(fieldPath) {
        return userUrl + '/' + fieldPath;
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
                header.Authorization = authorizationPrefix + userToken;
            }
        }

        return header;
    };

    return {
        appUrl: appUrl,
        authTokenUrl: authTokenUrl,
        userGroupPath: userGroupPath,
        allTeachersUrl: allTeachersUrl,
        registerAccountUrl: registerAccountUrl,
        isUserLoggedInUrl: isUserLoggedInUrl,
        loggedUserUrl: loggedUserUrl,
        myProfileHash: myProfileHash,
        logoutUserUrl: logoutUserUrl,
        userUrl: userUrl,
        usersHash: usersHash,
        userRatingUrl: userRatingUrl,
        authorizationPrefix: authorizationPrefix,
        messagesHash: messagesHash,
        messagesUrl: messagesUrl,
        messagesOverviewUrl: messagesOverviewUrl,
        updateUserDataUrl: updateUserDataUrl,
        addUserToTeacherGroupUrl: addUserToTeacherGroupUrl,
        updateScreenheroUrl: updateScreenheroUrl,
        getMessagesUrl: getMessagesUrl,
        apiCallHeader: apiCallHeader,
        searchTeachersBySkillPath: searchTeachersBySkillPath,
        paymentPath: paymentPath,
        searchAutocompleteOptionsUrl: searchAutocompleteOptionsUrl
    };
})();

module.exports = Config;
