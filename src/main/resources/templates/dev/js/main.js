import React from 'react';
import ReactDOM from 'react-dom';
import Router from 'react-router';
import intro from './common/intro';

import routes from './Routes/routes';

import LayoutView from './views/layout';

window.onload = function () {
    ReactDOM.render(<LayoutView />, document.getElementById('main-app'));
    Router.run(routes, (Handler) => ReactDOM.render(<Handler />, document.getElementById('main-container')));

    /*TODO delete in the future*/
    intro.introJs().addHints();
    window.intro = intro;
};