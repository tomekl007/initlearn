import React from 'react';

import DateComponent from './date';

var Appointment = React.createClass({

    render() {
        console.log(this);
        return (
            <div>
                <h2 className='main-schedule-appointment-header'>Appointments</h2>
                {this.props.parent.state.appointments.map(function (appointment, key) {
                    return <div className='main-schedule-appointment' key={key} >
                        <DateComponent date={appointment.date} />
                        <div className='main-schedule-appointment-content'>
                            {appointment.data.teacher}
                        </div>
                        <div className='main-schedule-appointment-content-cancel'>
                            <i className='fa fa-times'></i>
                        </div>
                    </div>;
                })}
            </div>
        );
    }
});

module.exports = Appointment;
