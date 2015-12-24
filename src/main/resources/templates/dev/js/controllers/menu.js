import $ from '../lib/jquery';
import Modal from './modal';
import Form from './form';

    var Menu = (function () {

        var $wrapper, $mobileOpenBtn, $createAccountBtn, $signInBtn;

        var init = function () {

            $wrapper = $('.main-nav');
            $mobileOpenBtn = $('.js-main-nav-menu-open');
            $createAccountBtn = $('.main-create-account');
            $signInBtn = $('.main-sign-in');

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
