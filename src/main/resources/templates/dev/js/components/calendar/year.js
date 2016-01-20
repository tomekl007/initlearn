import React from 'react';
import Router from 'react-router';

var Year = React.createClass({

    getInitialState() {
        var path = Router.HashLocation.getCurrentPath();
        var year = parseInt(path.split('/').slice(-1));

        return {
            path: '#' + path,
            year: year,
            months: 12
        };
    },
    getMonth(timestamp) {
        return new Date(timestamp).getMonth();
    },
    render() {
        console.log('year');
        var $thisComponent = this;
        var $months = [];
        var $reservations = [];

        /*TODO needs to be refactored*/
        this.props.parent.state.reservations.forEach(function (reservation, key) {
            var year = reservation.date.year;

            if ($thisComponent.state.year === year) {
                var month = reservation.date.month;
                $reservations[month] = $reservations[month] || [];
                $reservations[month].push(<span className='main-calendar-month-reservation' key={key}></span>);
            }
        });

        console.log($reservations);

        for (var i = 1; i < this.state.months + 1; i++) {
            $months.push(<div className='main-calendar-months' key={i}>
                <a href={this.state.path + '/' + i} >
                {i}
                {$reservations[i]}
                </a>
            </div>);
        }

        return (
            <div className='main-calendar-year'>
                {$months}
            </div>
        );
    }
});

module.exports = Year;