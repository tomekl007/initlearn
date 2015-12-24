/*TODO delete*/
import $ from '../lib/jquery';

    var Menu = (function () {

        var $wrapper, $mobileOpenBtn;

        var init = function () {

            $wrapper = $('.main-nav');
            $mobileOpenBtn = $('.js-main-nav-menu-open');

            attachEvents();
        };

        var attachEvents = function () {

            $mobileOpenBtn.on('click', toggleMobile);
        };

        var toggleMobile = function () {
            $wrapper.toggleClass('is-open');
        };

        return {
            init: init
        };
    })();

module.exports = Menu;
