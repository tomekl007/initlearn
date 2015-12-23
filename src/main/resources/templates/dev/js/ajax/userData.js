import $ from '../lib/jquery';
import config from './config';
import localStorage from '../common/localStorage';

var userData = (function () {

    var get = function() {
        $.ajax({
            url : config.loggedUserUrl,
            headers: {
                'Authorization' : localStorage.isAvailable() ? config.authorizationPrefix + window.localStorage.getItem('user-token') || '' : ''
            },
            success: function(data){
                console.log(data);
                $('.main-create-account').fadeOut(0);
                $('.main-sign-in').fadeOut(0);
                $('.main-user-name').fadeIn(0).find('a').attr('href', config.usersHash + data.email).html(data.fullName);
                $('.main-user-logout').fadeIn(0);

            },
            error: function(jqXHR, statusString, err) {
                console.log(jqXHR);
                console.log(statusString);
                console.log(err);
                console.log('cannot retrieve user data');
            }
        });
    };

    return {
        get: get
    }

})();

module.exports = userData;