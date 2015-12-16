var Modal = (function () {

    var $wrapper = $('.main-modal-window');
    var $outsideArea = $('.main-outside-area');

    var init = function () {
        attachEvents();
    };

    var attachEvents = function() {

    };

    var open = function () {
        $outsideArea.addClass('is-active');
        $wrapper.addClass('is-active');
    };

    var close = function () {
        $wrapper.removeClass('is-active');
    };

    return {
        open: open,
        close: close,
        init: init
    }
})();