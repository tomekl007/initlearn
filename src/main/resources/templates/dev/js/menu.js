var Menu = (function () {

    var $wrapper = $('.main-nav');
    var $mobileOpenBtn = $('.js-main-nav-menu-open');

    var toggleMobile = function () {
        $wrapper.toggleClass('is-open');
    };

    var attachEvents = function () {
        $mobileOpenBtn.on('click', toggleMobile);
    };

    var init = function () {
        attachEvents();
    };

    return {
        init: init
    };
})();