import React from 'react';

var Date = React.createClass({

        render() {
            return (
                <div className='main-schedule-appointment-date'>
                    <div className='main-schedule-appointment-date-day'>
                        {this.props.date.day}
                    </div>
                    <div className='main-schedule-appointment-date-month'>
                        {this.props.date.monthString}
                    </div>
                    <div className='main-schedule-appointment-date-year'>
                        {this.props.date.year}
                    </div>
                    <div className='main-schedule-appointment-content-date-hour'>
                        <i className='fa fa-clock-o'></i>
                        {this.props.date.time.hour}:{this.props.date.time.minutes}
                    </div>
                </div>
            );
        }
    });

module.exports = Date;
