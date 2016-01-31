var SystemDetection = (function () {

    var detectMobileSystem = function () {

        var agent = navigator.userAgent;
        var iOSVer = parseFloat(
                ('' + (/CPU.*OS ([0-9_]{1,5})|(CPU like).*AppleWebKit.*Mobile/i.exec(navigator.userAgent) || [0, ''])[1])
                    .replace('undefined', '3_2').replace('_', '.').replace('_', '')) || false;

        return {
            iOS: /(iPad|iPhone|iPod)/i.test(agent),
            iOSVer: iOSVer,
            android: /Android/i.test(agent)
        };
    };

    var isTouchEnabled = ('ontouchstart' in window) || window.DocumentTouch && document instanceof DocumentTouch ? true : false;
    var mobileSystem = detectMobileSystem();
    var isMobileOrTabletDevice = mobileSystem.iOS || mobileSystem.android;

    return {
        isTouchEnabled: isTouchEnabled,
        mobileSystem: mobileSystem,
        isMobileOrTabletDevice: isMobileOrTabletDevice
    };
})();

module.exports = SystemDetection;