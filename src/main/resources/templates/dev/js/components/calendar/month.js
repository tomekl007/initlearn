import React from 'react';
import Router from 'react-router';

var Month = React.createClass ({

    getInitialState() {
        var path = Router.HashLocation.getCurrentPath();
        var date = path.split('/').slice(-2);

        return {
            path: '#' + path,
            year: date[0],
            month: date[1]
        };
    },
    getDay(timestamp) {
        return new Date(timestamp).getDay();
    },
    render() {
        console.log('month');
        var $thisComponent = this;
        var $days = [];
        var $reservations = [];

        this.props.parent.state.reservations.forEach(function (data, key) {
            $reservations[$thisComponent.getDay(data.from_hour)] = <span className='main-calendar-day-reservation' key={key}></span>;
        });
        for (var i = 0; i < 31; i++) {
            $days.push(<div className='main-calendar-days' key={i} data-day-nr={i}>
                <a href={this.state.path +'/' + i} >
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