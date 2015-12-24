import $ from '../lib/jquery';
import localStorage from '../common/localStorage';
import config from '../ajax/config';
import modal from './modal';
import outsideArea from './outsideArea';

var Form = (function () {

    var $wrapper;;

    var show = function (event) {
        $wrapper = $('.main-form-wrapper');

        $wrapper.find('.main-form').removeClass('show');

        if (typeof event !== 'undefined') {
            $wrapper.find(event.target.hash).addClass('show');
        }
    };

    var init = function () {
        $wrapper = $('.main-form-wrapper');

        attachEvents();

        var frm = $('#sign-in-form');

        frm.on('submit', function (ev) {

            $.ajax({
                type: frm.attr('method'),
                url: frm.attr('action'),
                data: frm.serialize(),

                success: function (data) {
                    console.log(data);
                    if(localStorage.isAvailable()) {
                        window.localStorage.setItem('user-token', data.access_token);
                    }

                    window.location.reload();
                },

                error: function(jqXHR, statusString, err) {
                    console.log(jqXHR);
                    console.log(statusString);
                    console.log(err);
                    console.log('login attempt failed.  Please try again.');
                }

            });

            ev.preventDefault();

        });
    };

    var attachEvents = function () {
        $wrapper.find('.main-input').on('blur', isFilled);
    };

    var isFilled = function () {

        var $currentInput = $(this);
        var textLength = $currentInput.val().trim().length;

        if (textLength > 0) {
            $currentInput.addClass('is-filled');
            return true;
        }

        $currentInput.removeClass('is-filled');
        return false;
    };

    return {
        init: init,
        show: show
    };
})();

module.exports = Form;