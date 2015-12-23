import $ from '../lib/jquery';
import config from './config';
import localStorage from '../common/localStorage';

var UserLogout = (function () {

    var get = function() {
        $.ajax({
            url : config.logoutUserUrl,
            headers: {
                'Authorization' : localStorage.isAvailable() ? config.authorizationPrefix + window.localStorage.getItem('user-token') || '' : ''
            },
            success: function(){

                if (localStorage.isAvailable()) {
                    //FB.logout(function(response) {});
                    window.localStorage.clear();
                }

                $('.main-user-name').fadeOut(0);
                $('.main-user-logout').fadeOut(0);
                $('.main-create-account').fadeIn(0);
                $('.main-sign-in').fadeIn(0);
            },
            error: function(jqXHR, statusString, err) {
                console.log(jqXHR);
                console.log(statusString);
                console.log(err);
                console.log('logout failed');
            }
        });
    };

    return {
        get: get
    }

})();

module.exports = UserLogout;