define('ajax/config', function () {
    var config = (function () {

        var appUrl = 'https://initlearn.herokuapp.com/';
        var userGroupPath = 'group/users/';

        return {
            appUrl: appUrl,
            userGroupPath: userGroupPath
        };
    })();

    return config;
});