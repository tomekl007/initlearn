import React from 'react';

var Date = React.createClass({

        render() {
            return (
                <div className='main-schedule-appointment-date'>
                    <div className='main-schedule-appointment-date-day'>
                    {this.props.date.day}
                    </div>
                    <div className='main-schedule-appointment-date-month'>
                    {this.props.date.month}
                    </div>
                    <div className='main-schedule-appointment-date-year'>
                    {this.props.date.year}
                    </div>
                </div>
            );
        }
    });

module.exports = Date;
