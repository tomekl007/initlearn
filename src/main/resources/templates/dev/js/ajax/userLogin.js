import $ from '../lib/jquery';
import config from './config';
import localStorage from '../common/localStorage';
import userData from './userData';

var UserLogin = (function () {

    var headers = {};

    if (localStorage.isAvailable()) {

        if (window.localStorage.getItem('user-token') !== null) {
            headers = {
                'Authorization' : localStorage.isAvailable() ? config.authorizationPrefix + window.localStorage.getItem('user-token') || '' : ''
            }
        }
    }

    var get = function() {
        $.ajax({
            url : config.isUserLoggedInUrl,
            headers: headers,
            success: function(data){
                console.log(data);
                if (data === true) {
                    userData.get();
                }
            },
            error: function(jqXHR, statusString, err) {
                console.log(jqXHR);
                console.log(statusString);
                console.log(err);
                console.log('login attempt failed.  Please try again.');
            }
        });
    };

    return {
        get: get
    }

})();

module.exports = UserLogin;