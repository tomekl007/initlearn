import $ from './lib/jquery';
import Form from './controllers/form';
import Menu from './controllers/menu';
import OutsideArea from './controllers/outsideArea';

$(function () {
    Form.init();
    Menu.init();
    OutsideArea.init();
});