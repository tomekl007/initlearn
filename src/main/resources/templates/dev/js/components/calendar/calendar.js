import React from 'react';
import Router from 'react-router';

import config from '../../ajax/config';
import api from '../../ajax/api';

import CalendarYearComponent from './year';
import CalendarMonthComponent from './month';
import CalendarDayComponent from './day';

window.Router = Router;

var Calendar = React.createClass({

    getInitialState() {
        var teacherEmail = this.getUserEmail();

        return {
            teacherEmail: teacherEmail,
            teacherCalendar: teacherEmail ? true : false,
            reservations: [],
            appointments: []
        };
    },
    getUserEmail() {
        var path = Router.HashLocation.getCurrentPath();

        return path.replace(config.usersPath + '/', '').split('/')[0];
    },
    componentDidMount() {

        if (this.state.teacherCalendar) {
            api.getReservation(this.state.teacherEmail)
                .then(this.addReservations);
        } else {
            api.getAppointments()
                .then(this.addAppointments);
        }
    },
    addReservations(reservations) {
        this.setState({reservations: reservations});
    },
    addAppointments(appointments) {
        this.setState({appointments: appointments});
    },
    getDateComponentFromPath() {
        var dateComponents = [
            <CalendarYearComponent parent={this}/>,
            <CalendarMonthComponent parent={this}/>,
            <CalendarDayComponent parent={this}/>];
        var path = Router.HashLocation.getCurrentPath();
        var dateFormat = path.substring(path.indexOf(config.calendarPath))
                .replace(config.calendarPath, '')
                .split('/').slice(-3)
                .length - 1;

        return dateComponents[dateFormat];
    },
    render() {
        console.log(this);
        return (
            <div>
                {this.getDateComponentFromPath()}
            </div>
        );
    }
});

module.exports = Calendar;