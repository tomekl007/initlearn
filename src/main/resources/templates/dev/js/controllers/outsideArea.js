import $ from '../lib/jquery';
import Modal from './modal';

    var OutsideArea = (function () {
        var $outsideArea = $('.main-outside-area');

        var init = function () {
            attachEvents();
        };

        var attachEvents = function () {
            $outsideArea.on('click', close);
            $outsideArea.on('click', Modal.close);
        };

        var close = function () {
            $outsideArea.removeClass('is-active');
        };

        return {
            init: init
        };
    })();

module.exports = OutsideArea;