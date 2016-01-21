import React from 'react';
import Router from 'react-router';

import config from '../../ajax/config';
import api from '../../ajax/api';

import CalendarYearComponent from './year';
import CalendarMonthComponent from './month';
import CalendarDayComponent from './day';

var Calendar = React.createClass({

    getInitialState() {

        return {
            email: this.getUserEmail(),
            reservations: []
        };
    },
    getUserEmail() {
        var path = Router.HashLocation.getCurrentPath();

        return path.replace(config.usersPath + '/', '').split('/')[0];
    },
    componentDidMount() {
        api.getReservation(this.state.email)
            .then(this.addReservations);

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
    getFullDate(timestamp) {
        var date = new Date(timestamp);

        return {
            year: date.getFullYear(),
            month: date.getMonth() + 1,
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