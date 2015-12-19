var Config = (function () {

    var appUrl = 'https://initlearn.herokuapp.com/';
    var userGroupPath = 'group/users/';
    var allTeachersUrl = appUrl + userGroupPath + 'teachers';

    return {
        appUrl: appUrl,
        userGroupPath: userGroupPath,
        allTeachersUrl: allTeachersUrl
    };
})();

module.exports = Config;
