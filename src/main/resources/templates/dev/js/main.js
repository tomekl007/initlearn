import $ from './lib/jquery';
import React from 'react';
import Router from 'react-router';

import routes from './Routes/routes';

import LayoutView from './views/layout';

React.render(<LayoutView />, document.getElementById('main-app'));
Router.run(routes, (Handler) => React.render(<Handler /> , document.getElementById('main-container')));