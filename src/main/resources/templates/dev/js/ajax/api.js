import $http from './http';
import config from './config';
import localStorage from '../common/localStorage';


var Api = (function () {

    var call = {
        payload: function (data) {
            var options = {
                /*always call header*/
                headers: (function () {

                    var authorizationPrefix = 'Bearer ';
                    var headerOptions = {
                        'Content-Type': 'application/json'
                    };

                    if (localStorage.isAvailable()) {
                        var userToken = window.localStorage.getItem('user-token');

                        if (typeof userToken === 'string') {
                            headerOptions.Authorization = authorizationPrefix + userToken;
                        }
                    }

                    return headerOptions;
                })()
            };

            for (var key in data) {
                options[key] = data[key];
            }
            return options;
        },
        getTeachers: function () {
            return $http(config.getTeachersUrl)
                .get(this.payload());
        },
        registerAccount: function (data) {
            return $http(config.registerAccountUrl)
                .post(this.payload({json: data}));
        },
        addUserToTeachGroup: function (data) {
            return $http(config.addUserToTeacherGroupUrl)
                .post(this.payload({json: data}));
        },
        addUserRating: function (emailPath, data) {
            return $http(config.addUserRatingUrl(emailPath))
                .post(this.payload({json: data}));
        },
        updateUserData: function (data) {
            return $http(config.updateUserDataUrl)
                .post(this.payload({json: data}));
        },
        updateUserDataField: function (fieldPath, data) {
            return $http(config.updateUserDataFieldUrl(fieldPath))
                .post(this.payload({json: data}));
        },
        isUserLoggedIn: function () {
            return $http(config.isUserLoggedInUrl)
                .get(this.payload());
        },
        getAuthToken: function (data) {
            return $http(config.getAuthTokenUrl)
                .post(this.payload({formData: data}));
        },
        getUserData: function () {
            return $http(config.getUserDataUrl)
                .get(this.payload());
        },
        logoutUser: function() {
            return $http(config.logoutUserUrl)
                .post(this.payload());
        },
        getMessagesOverview: function () {
            return $http(config.getMessagesOverviewUrl)
                .get(this.payload());
        },
        getMessages: function (emailPath) {
            return $http(config.getMessagesUrl(emailPath))
                .get(this.payload());
        },
        sendMessage: function(data) {
            return $http(config.messagesUrl)
                .post(this.payload({json: data}));
        },
        getSearchAutocompleteOptions: function () {
            return $http(config.getSearchAutocompleteOptionsUrl)
                .get(this.payload());
        },
        addReservation: function(data) {
            return $http(config.addReservationUrl)
                .post(this.payload({json: data}));
        },
        getReservation: function(emailPath) {
            return $http(config.getReservationsUrl(emailPath))
                .get(this.payload());
        },
        deleteReservation: function(emailPath) {
            return $http(config.deleteReservationUrl(emailPath))
                .delete(this.payload());
        },
        getAppointments: function() {
            return $http(config.getAppointmentsUrl)
                .get(this.payload());
        }
    };

    return call;
})();

module.exports = Api;