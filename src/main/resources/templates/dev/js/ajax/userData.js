import localStorage from '../common/localStorage';

var UserData = (function () {
    var data = localStorage.isAvailable ? JSON.parse(window.localStorage.getItem('user-data')) : {};


    var set = function(newdata) {
        localStorage.isAvailable ? window.localStorage.setItem('user-data', JSON.stringify(newdata)) : false;
        data = newdata;
    };

    var get = function() {
        return data;
    };

    return {
        set: set,
        get: get
    };
})();

module.exports = UserData;