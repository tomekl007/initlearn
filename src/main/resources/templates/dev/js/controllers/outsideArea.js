import $ from '../lib/jquery';
import Modal from './modal';

    var OutsideArea = (function () {

        var $outsideArea;

        var init = function () {

            $outsideArea = $('.main-outside-area');

            attachEvents();
        };

        var attachEvents = function () {
            $outsideArea.on('click', close);
            $outsideArea.on('click', Modal.close);
        };

        var close = function () {

            $outsideArea = $('.main-outside-area');

            $outsideArea.removeClass('is-active');
        };

        return {
            init: init,
            close: close
        };
    })();

module.exports = OutsideArea;