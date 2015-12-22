import $ from '../lib/jquery';
import config from './config';
import localStorage from '../common/localStorage';

var userData = (function () {

    var get = function() {
        $.ajax({
            url : config.loggedUserUrl,
            headers: {
                'Authorization' : localStorage.isAvailable() ? window.localStorage.getItem('user-token') || '' : ''
            },
            success: function(data){
                console.log(data);
            },
            error: function(jqXHR, statusString, err) {
                console.log(jqXHR);
                console.log(statusString);
                console.log(err);
                alert('cannot retrieve user data');
            }
        });
    };

    return {
        get: get
    }

})();

module.exports = userData;