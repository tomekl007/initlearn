import React from 'react';
import Router from 'react-router';

var {DefaultRoute, Route} = Router;

import TeachersView from '../views/teachers';
import HomeView from '../views/home';
import UserProfileView from '../views/userProfile';
import MyProfileView from '../views/myProfile';
import MessagesView from '../views/messages';
import CancelPaymentView from '../views/cancelPayment';
import SuccessPaymentView from '../views/successPayment';
import CalendarView from '../views/calendar';

var routes = (
    <Route name='app' path='/'>
        <Route name='teachers' handler={TeachersView}/>
        <Route name='users/*' handler={UserProfileView} />
        <Route name='successPayment' handler={SuccessPaymentView} />
        <Route name='cancelPayment' handler={CancelPaymentView} />
        <Route name='calendar/:year' handler={CalendarView} />
        <Route name='calendar/:year/:month' handler={CalendarView} />
        <Route name='calendar/:year/:month/:day' handler={CalendarView} />
        <Route name='msg/*' handler={MessagesView} />
        <Route name='me' handler={MyProfileView} />
        <Route name='*' path='*' handler={HomeView}/>
        <DefaultRoute handler={HomeView}/>
    </Route>
);

module.exports = routes;