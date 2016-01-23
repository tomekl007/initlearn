import React from 'react';

var Date = React.createClass({

        render() {
            return (
                <div className='main-schedule-item-date'>
                    <div className='main-schedule-item-date-day'>
                        {this.props.date.day}
                    </div>
                    <div className='main-schedule-item-date-month'>
                        {this.props.date.monthString}
                    </div>
                    <div className='main-schedule-item-date-year'>
                        {this.props.date.year}
                    </div>
                    <div className='main-schedule-item-date-time'>
                        <i className='fa fa-clock-o'></i>
                        {this.props.date.time.hour}:{this.props.date.time.minutes}
                    </div>
                </div>
            );
        }
    });

module.exports = Date;
