var LocalStorage = (function() {
    var isAvailable = function () {

        try {
            if (!window.localStorage) { return false; }
            else {
                window.localStorage.setItem('test','test');
                if (window.localStorage.getItem('test') !== 'test') {
                    return false;
                } else {
                    window.localStorage.removeItem('test');
                }
            }

            return true;
        } catch (e) {
            console.error('Local storage error. Private mode or not supported.');
            return false;
        }
    };

    return {
        isAvailable: isAvailable
    }
});

module.exports = LocalStorage;