import $ from '../lib/jquery';
import config from './config';
import localStorage from '../common/localStorage';
import userData from './userData';

var UserLogin = (function () {

    var get = function() {
        $.ajax({
            url : config.isUserLoggedInUrl,
            headers: {
                'Authorization' : localStorage.isAvailable() ? window.localStorage.getItem('user-token') || '' : ''
            },
            success: function(data){
                console.log(data);
                userData.get();
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