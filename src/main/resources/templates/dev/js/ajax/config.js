import localStorage from '../common/localStorage';

var Config = (function () {

    var appUrl = 'https://initlearn.herokuapp.com';
    var myProfileHash = '#me';
    var usersUrl = appUrl + '/users';
    var usersHash = '#users/';
    var userGroupPath = '/group/users/';
    var messagesUrl = appUrl + '/msg';
    var messagesHash = '#msg/';

    var registerAccountUrl = appUrl + '/registerAccount';
    var addUserToTeacherGroupUrl = usersUrl + '/teachers';
    var updateScreenheroUrl = usersUrl + '/screenhero';
    var updateUserDataUrl = usersUrl + '/data';
    var isUserLoggedInUrl = appUrl + '/isLoggedIn';
    var getAuthTokenUrl = appUrl + '/oauth/token';
    var getUserDataUrl = appUrl + '/me';
    var getMessagesOverviewUrl = messagesUrl + '/overview';
    var getSearchAutocompleteOptionsUrl = appUrl + '/skills';
    var getTeachersUrl = appUrl + userGroupPath + 'teachers';
    var logoutUserUrl = appUrl + '/logout';

    var searchUrl = 'search?';
    var searchTeachersBySkillPath = usersHash + searchUrl + 'skill=';
    var paymentPath = '/adaptivePayment?toEmail=';

    var getMessagesUrl = function (email) {
        return messagesUrl + '/' + email;
    };

    var addUserRatingUrl = function(email) {
      return usersUrl + '/' + email + '/rate';
    };

    var updateUserDataFieldUrl = function(fieldPath) {
        return usersUrl + '/' + fieldPath;
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
        myProfileHash: myProfileHash,
        usersUrl: usersUrl,
        usersHash: usersHash,
        userGroupPath: userGroupPath,
        messagesUrl: messagesUrl,
        messagesHash: messagesHash,
        registerAccountUrl: registerAccountUrl,
        addUserToTeacherGroupUrl: addUserToTeacherGroupUrl,
        addUserRatingUrl: addUserRatingUrl,
        updateUserDataUrl: updateUserDataUrl,
        updateScreenheroUrl: updateScreenheroUrl,
        updateUserDataFieldUrl: updateUserDataFieldUrl,
        isUserLoggedInUrl: isUserLoggedInUrl,
        getAuthTokenUrl: getAuthTokenUrl,
        getUserDataUrl: getUserDataUrl,
        getMessagesOverviewUrl: getMessagesOverviewUrl,
        getMessagesUrl: getMessagesUrl,
        getSearchAutocompleteOptionsUrl: getSearchAutocompleteOptionsUrl,
        getTeachersUrl: getTeachersUrl,
        logoutUserUrl: logoutUserUrl,
        searchTeachersBySkillPath: searchTeachersBySkillPath,
        paymentPath: paymentPath,
        authorizationPrefix: authorizationPrefix,
        apiCallHeader: apiCallHeader
    };
})();

module.exports = Config;
