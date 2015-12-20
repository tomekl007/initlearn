var Config = (function () {

    var appUrl = 'https://initlearn.herokuapp.com/';
    var userGroupPath = 'group/users/';
    var allTeachersUrl = appUrl + userGroupPath + 'teachers';
    var usersHash = '#users/';

    return {
        appUrl: appUrl,
        userGroupPath: userGroupPath,
        allTeachersUrl: allTeachersUrl,
        usersHash: usersHash
    };
})();

module.exports = Config;
