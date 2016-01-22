import React from 'react';

import DateComponent from './date';

var Appointment = React.createClass({

    render() {
        console.log(this);
        return (
            <div>
                {this.props.parent.state.appointments.map(function (appointment, key) {
                    return <div className='main-schedule-appointment' key={key} >
                        <DateComponent date={appointment.date} />
                        <div className='main-schedule-appointment-content'>
                            {appointment.data.teacher}
                        </div>
                        <div className='main-schedule-appointment-content-date-hour'>
                            <i className='fa fa-clock-o'></i>
                                {appointment.date.hour}
                        </div>
                    </div>;
                })}
            </div>
        );
    }
});

module.exports = Appointment;
