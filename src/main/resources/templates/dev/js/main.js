import $ from './lib/jquery';
import React from 'react';
import Router from 'react-router';

import Form from './controllers/form';
import Menu from './controllers/menu';

import routes from './Routes/routes';

import LayoutView from './views/layout';

React.render(<LayoutView />, document.getElementById('main-app'));
Router.run(routes, (Handler) => React.render(<Handler /> , document.getElementById('main-container')));

$(function () {

    Form.init();
    Menu.init();
});