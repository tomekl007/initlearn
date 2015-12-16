var Form = (function () {

    var $wrapper = $('.main-form-wrapper');

    var show = function(event) {

        $wrapper.find('.main-form').removeClass('show');

        if (typeof event !== 'undefined') {
            $wrapper.find(event.target.hash).addClass('show');
        }
    };

    return {
        show: show
    }
})();