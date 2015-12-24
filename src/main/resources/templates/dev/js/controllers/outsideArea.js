/*TODO delete*/
import $ from '../lib/jquery';
import Modal from './modal';

    var OutsideArea = (function () {

        var $outsideArea;

        var init = function () {

            $outsideArea = $('.main-modal-window-close');

            attachEvents();
        };

        var attachEvents = function () {
            $outsideArea.on('click', close);
            $outsideArea.on('click', Modal.close);
        };

        var close = function () {

            var $modal = $('.main-modal-window');

            $modal.removeClass('is-active');
        };

        return {
            init: init,
            close: close
        };
    })();

module.exports = OutsideArea;