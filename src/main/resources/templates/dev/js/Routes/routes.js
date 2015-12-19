import React from 'react';
import Router from 'react-router';

var {DefaultRoute, Route} = Router;

import TeachersView from '../views/teachers';
import HomeView from '../views/home';

var routes = (
    <Route name="app" path="/">
        <Route name="teachers" handler={TeachersView}/>
        <Route name="*" path='*' handler={HomeView}/>
        <DefaultRoute handler={HomeView}/>
    </Route>
);

module.exports = routes;