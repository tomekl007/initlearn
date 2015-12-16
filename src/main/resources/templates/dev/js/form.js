var Form = (function () {

    var $wrapper = $('.main-form-wrapper');

    var show = function (event) {

        $wrapper.find('.main-form').removeClass('show');

        if (typeof event !== 'undefined') {
            $wrapper.find(event.target.hash).addClass('show');
        }
    };

    var init = function () {
        attachEvents();
    };

    var attachEvents = function () {
        $wrapper.find('.main-input').on('blur', isFilled);

    };

    var isFilled = function () {
        var $currentInput = $(this);
        var textLength = $currentInput.val().trim().length;

        if (textLength > 0) {
            $currentInput.addClass('is-filled');
            return true;
        }

        $currentInput.removeClass('is-filled');
        return false;
    };

    return {
        show: show,
        init: init
    };
})();