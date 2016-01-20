import React from 'react';
import Router from 'react-router';

var Year = React.createClass ({

    getInitialState() {
        var path = Router.HashLocation.getCurrentPath();
        var year = path.split('/').slice(-1);

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

        this.props.parent.state.reservations.forEach(function (data, key) {
            $reservations[$thisComponent.getMonth(data.from_hour)] = <span className='main-calendar-month-reservation' key={key}></span>;
        });

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