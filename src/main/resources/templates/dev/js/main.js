$(window).load(function () {

    Modal.init();
    Form.init();
    Menu.init(Modal, Form);
    OutsideArea.init(Modal);
});