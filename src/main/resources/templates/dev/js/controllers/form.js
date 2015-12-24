/*TODO delete*/
import $ from '../lib/jquery';
import localStorage from '../common/localStorage';
import config from '../ajax/config';
import modal from './modal';
import outsideArea from './outsideArea';

var Form = (function () {

    var $wrapper;;

    var show = function (event) {
        $wrapper = $('.main-form-wrapper');

        $wrapper.find('.main-form').removeClass('show');

        if (typeof event !== 'undefined') {
            $wrapper.find(event.target.hash).addClass('show');
        }
    };

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
        init: init,
        show: show
    };
})();

module.exports = Form;