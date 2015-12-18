import $ from '../lib/jquery';
import Modal from './modal';
import Form from './form';

    var Menu = (function () {

        var $wrapper = $('.main-nav');
        var $mobileOpenBtn = $('.js-main-nav-menu-open');
        var $createAccountBtn = $('.main-create-account');
        var $signInBtn = $('.main-sign-in');

        var init = function () {
            attachEvents();
        };

        var attachEvents = function () {

            $mobileOpenBtn.on('click', toggleMobile);
            $signInBtn.on('click', Modal.open);
            $signInBtn.on('click', Form.show);
            $createAccountBtn.on('click', Modal.open);
            $createAccountBtn.on('click', Form.show);
        };

        var toggleMobile = function () {
            $wrapper.toggleClass('is-open');
        };

        return {
            init: init
        };
    })();

module.exports = Menu;
