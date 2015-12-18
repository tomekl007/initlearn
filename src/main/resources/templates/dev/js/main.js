requirejs(['jquery', 'form', 'menu', 'outsideArea', 'ajax/config'], function ($, form, menu, outsideArea, config) {

    $(function () {
        form.init();
        menu.init();
        outsideArea.init();
    });
});