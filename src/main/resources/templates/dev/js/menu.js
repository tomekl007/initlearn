define('menu', ['jquery', 'modal', 'form'], function ($, modal, form) {

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
            $signInBtn.on('click', modal.open);
            $signInBtn.on('click', form.show);
            $createAccountBtn.on('click', modal.open);
            $createAccountBtn.on('click', form.show);
        };

        var toggleMobile = function () {
            $wrapper.toggleClass('is-open');
        };

        return {
            init: init
        };
    })();

    return Menu;
});