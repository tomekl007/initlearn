import localStorage from '../common/localStorage';

var Config = (function () {

    var appUrl = 'https://initlearn.herokuapp.com';
    var myProfileHash = '#me';
    var usersPath = '/users';
    var usersUrl = appUrl + usersPath;
    var usersHash = '#users/';
    var userGroupPath = '/group' + usersPath + '/';
    var messagesUrl = appUrl + '/msg';
    var messagesHash = '#msg/';
    var calendarPath = '/calendar/';

    var registerAccountUrl = appUrl + '/registerAccount';
    var addUserToTeacherGroupUrl = usersUrl + '/teachers';
    var updateUserDataUrl = usersUrl + '/data';
    var isUserLoggedInUrl = appUrl + '/isLoggedIn';
    var getAuthTokenUrl = appUrl + '/oauth/token';
    var getUserDataUrl = appUrl + '/me';
    var getMessagesOverviewUrl = messagesUrl + '/overview';
    var getSearchAutocompleteOptionsUrl = appUrl + '/skills';
    var addReservationUrl = appUrl + '/reservations';
    var getReservationsWithPaymentsUrl = appUrl + '/reservations/payments';
    var getAppointmentsUrl = appUrl + '/appointments';
    var getAppointmentsWithPaymentsUrl = appUrl + '/appointments/payments';
    var getTeachersUrl = appUrl + userGroupPath + 'teachers';
    var logoutUserUrl = appUrl + '/logout';

    var searchUrl = 'search?';
    var searchTeachersBySkillPath = usersHash + searchUrl + 'skill=';

    var getMessagesUrl = function (email) {
        return messagesUrl + '/' + email;
    };

    var addUserRatingUrl = function (email) {
        return usersUrl + '/' + email + '/rate';
    };

    var updateUserDataFieldUrl = function (fieldPath) {
        return usersUrl + '/' + fieldPath;
    };

    var getReservationsUrl = function (email) {
        return appUrl + '/reservations/' + email;
    };

    var deleteReservationUrl = function (email) {
        return appUrl + '/reservations/delete/' + email;
    };

    var deleteAppointmentUrl = function (email) {
        return appUrl + '/appointments/delete/' + email;
    };

    var paymentPath = function (email, timestamp) {
        return '/adaptivePayment?toEmail=' + email + '&fromHour=' + timestamp;
    };

    return {
        appUrl: appUrl,
        myProfileHash: myProfileHash,
        usersPath: usersPath,
        usersUrl: usersUrl,
        usersHash: usersHash,
        userGroupPath: userGroupPath,
        messagesUrl: messagesUrl,
        messagesHash: messagesHash,
        calendarPath: calendarPath,
        registerAccountUrl: registerAccountUrl,
        addUserToTeacherGroupUrl: addUserToTeacherGroupUrl,
        addUserRatingUrl: addUserRatingUrl,
        updateUserDataUrl: updateUserDataUrl,
        updateUserDataFieldUrl: updateUserDataFieldUrl,
        isUserLoggedInUrl: isUserLoggedInUrl,
        getAuthTokenUrl: getAuthTokenUrl,
        getUserDataUrl: getUserDataUrl,
        getMessagesOverviewUrl: getMessagesOverviewUrl,
        getMessagesUrl: getMessagesUrl,
        getSearchAutocompleteOptionsUrl: getSearchAutocompleteOptionsUrl,
        addReservationUrl: addReservationUrl,
        getReservationsUrl: getReservationsUrl,
        deleteReservationUrl: deleteReservationUrl,
        getReservationsWithPaymentsUrl: getReservationsWithPaymentsUrl,
        getAppointmentsUrl: getAppointmentsUrl,
        deleteAppointmentUrl: deleteAppointmentUrl,
        getAppointmentsWithPaymentsUrl: getAppointmentsWithPaymentsUrl,
        getTeachersUrl: getTeachersUrl,
        logoutUserUrl: logoutUserUrl,
        searchTeachersBySkillPath: searchTeachersBySkillPath,
        paymentPath: paymentPath
    };
})();

module.exports = Config;
