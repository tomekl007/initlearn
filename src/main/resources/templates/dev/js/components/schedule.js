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
            appointments: []
        };
    },
    getUserEmail() {
        var path = Router.HashLocation.getCurrentPath();

        return path.replace(config.usersPath + '/', '').split('/')[0];
    },
    componentDidMount() {
        api.getReservation(this.state.email)
            .then(this.add('reservations').toStore);

        api.getAppointments()
            .then(this.add('appointments').toStore);

    },
    add(option) {
        var $thisComponent = this;
        var dataStore = [];
        console.log(option);

        var toStore = function(data) {
            data.forEach(function(item) {
                dataStore.push({
                    data: item,
                    date: $thisComponent.getFullDate(item.from_hour)
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
            day: date.getDate(),
            hour: date.getHours()
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