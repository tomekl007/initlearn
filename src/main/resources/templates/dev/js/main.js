import $ from './lib/jquery';
import Form from './controllers/form';
import Menu from './controllers/menu';
import OutsideArea from './controllers/outsideArea';
import React from 'react';
import Router from 'react-router';
import routes from './Routes/routes';
import userLogin from './ajax/userLogin';

Router.run(routes, (Handler) => React.render(<Handler /> , document.getElementById('main-container')));

$(function () {
    Form.init();
    Menu.init();
    OutsideArea.init();
    userLogin.get();
});