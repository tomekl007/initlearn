import React from 'react';
import Router from 'react-router';

import config from '../../ajax/config';
import api from '../../ajax/api';

import CalendarYearComponent from './year';
import CalendarMonthComponent from './month';
import CalendarDayComponent from './day';

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
        var $thisComponent = this;
        var reservationsStore = [];

        reservations.forEach(function(reservation) {
            reservationsStore.push({
                data: reservation,
                date: $thisComponent.getFullDate(reservation.from_hour)
            });
        });

        this.setState({reservations: reservationsStore});
    },
    addAppointments(appointments) {

        this.setState({appointments: appointments});
    },
    getFullDate(timestamp) {
        var date = new Date(timestamp);

        return {
            year: date.getFullYear(),
            month: date.getMonth(),
            day: date.getDate(),
            hour: date.getHours()
        };
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
        return (
            <div>
                {this.getDateComponentFromPath()}
            </div>
        );
    }
});

module.exports = Calendar;