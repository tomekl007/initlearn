import React from 'react';
import Router from 'react-router';

var Month = React.createClass({

    getInitialState() {
        var path = Router.HashLocation.getCurrentPath();
        var date = path.split('/').slice(-2);

        return {
            path: '#' + path,
            year: parseInt(date[0]),
            month: parseInt(date[1])
        };
    },
    getDay(timestamp) {
        return new Date(timestamp).getDate();
    },
    daysInMonth(month, year) {
        return new Date(year || this.state.year, this.state.month, 0).getDate();
    },
    render() {
        console.log('month');
        console.log(this);
        var $thisComponent = this;
        var $days = [];
        var $reservations = [];

        this.props.parent.state.reservations.forEach(function (reservation, key) {
            var year = reservation.date.year;
            var month = reservation.date.month;
            if ($thisComponent.state.month === month && $thisComponent.state.year === year) {
                var day = reservation.date.day;
                $reservations[day] = $reservations[day] || [];
                $reservations[day].push(<span className='main-calendar-day-reservation' key={key}></span>);
            }
        });

        for (var i = 1; i < this.daysInMonth() + 1; i++) {
            $days.push(<div className='main-calendar-days' key={i} data-day-nr={i}>
                <a href={this.state.path + '/' + i} >
                {i}
                {$reservations[i]}
                </a>
            </div>);
        }

        return (
            <div className='main-calendar-month'>
                {$days}
            </div>
        );
    }
})

module.exports = Month;