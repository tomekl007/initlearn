var Menu = (function () {

    var $wrapper = $('.main-nav');
    var $mobileOpenBtn = $('.js-main-nav-menu-open');
    var $createAccountBtn = $('.main-create-account');
    var $signInBtn = $('.main-sign-in');

    var init = function (Modal, Form) {
        attachEvents(Modal, Form);
    };

    var attachEvents = function (Modal, Form) {
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
    }
})();