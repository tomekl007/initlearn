import React from 'react';

import ScheduleComponent from '../components/schedule';
import ReservationComponent from '../components/schedule/reservation';
import AppointmentComponent from '../components/schedule/appointment';

class Schedule extends React.Component {
    render() {
        return (
            <div className='main-section-schedule'>
                <div className='row'>
                    <div className='main-schedule-tabs'>
                        <div className='main-schedule-tabs-item is-active'>My Appointments</div>
                        <div className='main-schedule-tabs-item'>My Reservations</div>
                    </div>
                </div>
                <ScheduleComponent content={AppointmentComponent}/>
            </div>
        );
    }
}

module.exports = Schedule;