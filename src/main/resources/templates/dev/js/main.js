import $ from './lib/jquery';
import React from 'react';
import ReactDOM from 'react-dom';
import Router from 'react-router';

import routes from './Routes/routes';

import LayoutView from './views/layout';
import api from './ajax/api';

ReactDOM.render(<LayoutView />, document.getElementById('main-app'));
Router.run(routes, (Handler) => ReactDOM.render(<Handler />, document.getElementById('main-container')));