import React from 'react';
import Router from 'react-router';

var {DefaultRoute, Route} = Router;

import TeachersView from '../views/teachers';
import HomeView from '../views/home';
import userProfileView from '../views/userProfile';
import myProfileView from '../views/me';

var routes = (
    <Route name="app" path="/">
        <Route name="teachers" handler={TeachersView}/>
        <Route name="users/*" handler={userProfileView} />
        <Route name="me" handler={myProfileView} />
        <Route name="*" path='*' handler={HomeView}/>
        <DefaultRoute handler={HomeView}/>
    </Route>
);

module.exports = routes;