import React from 'react';

import ScheduleComponent from '../components/schedule';
import ReservationComponent from '../components/schedule/reservation';
import AppointmentComponent from '../components/schedule/appointment';

class Schedule extends React.Component {
    render() {
        return (
            <div className='main-section-schedule'>
                <ScheduleComponent content={AppointmentComponent}/>
            </div>
        );
    }
}

module.exports = Schedule;