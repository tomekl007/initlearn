define('outsideArea', ['jquery', 'modal'], function ($, modal) {

    var OutsideArea = (function () {
        var $outsideArea = $('.main-outside-area');

        var init = function () {
            attachEvents();
        };

        var attachEvents = function () {
            $outsideArea.on('click', close);
            $outsideArea.on('click', modal.close);
        };

        var close = function () {
            $outsideArea.removeClass('is-active');
        };

        return {
            init: init
        };
    })();

    return OutsideArea;
});