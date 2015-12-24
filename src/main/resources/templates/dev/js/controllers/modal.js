/*TODO delete*/
import $ from '../lib/jquery';

var Modal = (function () {

    var $wrapper, $outsideArea;

    var open = function () {

        $wrapper = $('.main-modal-window');
        $outsideArea = $('.main-outside-area');

        $outsideArea.addClass('is-active');
        $wrapper.addClass('is-active');
    };

    var close = function () {

        $wrapper = $('.main-modal-window');

        $wrapper.removeClass('is-active');
    };

    return {
        open: open,
        close: close
    };
})();

module.exports = Modal;
