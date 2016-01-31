import React from 'react';
import Router from 'react-router';

import userData from '../ajax/userData';
import config from '../ajax/config';
import api from '../ajax/api';

var Schedule = React.createClass({

    getInitialState() {

        return {
            email: this.getUserEmail() || userData.get().email,
            reservations: [],
            reservationsWithPayments: [],
            appointments: [],
            appointmentsWithPayments: []
        };
    },
    getUserEmail() {
        var path = Router.HashLocation.getCurrentPath();

        return path.replace(config.usersPath + '/', '').split('/')[0];
    },
    componentDidMount() {
        this.getData(true);
    },
    componentWillReceiveProps(state) {
        if (this.props.type === 'schedule') {
            this.getData(state.fromDate);
        }
    },
    getData(fromCurrentDate) {
        var fromDate = typeof fromCurrentDate !== 'undefined' ?
            fromCurrentDate : this.props.fromDate;

        if (this.props.type === 'schedule') {
            api.getReservationsWithPayments(fromDate)
                .then(this.add('reservationsWithPayments').toStore);

            api.getAppointmentsWithPayments(fromDate)
                .then(this.add('appointmentsWithPayments').toStore);

        } else if (this.props.type === 'calendar') {
            api.getReservations(this.state.email, false)
                .then(this.add('reservations').toStore);
        }
    },
    add(option) {
        var $thisComponent = this;
        var dataStore = [];

        var toStore = function(data) {
            data.forEach(function(item) {
                var timestamp = typeof item.reservation !== 'undefined' ?
                    item.reservation.from_hour : item.from_hour;
                dataStore.push({
                    data: item,
                    date: $thisComponent.getFullDate(timestamp)
                });
            });

            $thisComponent.setState({[option]: dataStore});
        };

        return {
            toStore: toStore
        }
    },
    getFullDate(timestamp) {
        var date = new Date(timestamp);

        return {
            year: date.getFullYear(),
            month: date.getMonth() + 1,
            monthString: date.toString().split(' ')[1],
            day: date.getDate(),
            dayString: date.toString().split(' ')[0],
            time: {
                hour: date.getHours(),
                minutes: date.getMinutes() < 30 ? '00' : 30
            }
        };
    },
    render() {
        return (
            <div>
                <this.props.content parent={this} />
            </div>
        );
    }
});

module.exports = Schedule;