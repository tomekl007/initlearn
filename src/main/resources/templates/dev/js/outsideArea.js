var OutsideArea = (function () {

    var $outsideArea = $('.main-outside-area');

    var init = function(Modal) {
        attachEvents(Modal);
    };

    var attachEvents = function(Modal) {
        $outsideArea.on('click', close);
        $outsideArea.on('click', Modal.close);
    };

    var close = function() {
        $outsideArea.removeClass('is-active');
    };

    return {
        init: init
    };
})();