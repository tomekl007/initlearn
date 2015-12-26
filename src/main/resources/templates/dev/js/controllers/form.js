/*TODO delete*/
import $ from '../lib/jquery';
import localStorage from '../common/localStorage';
import config from '../ajax/config';

var Form = (function () {

    var $wrapper;

    var init = function () {
        $wrapper = $('.main-form-wrapper');

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
        init: init
    };
})();

module.exports = Form;