import React from 'react';
import ReactDOM from 'react-dom';
import tapOrClick from 'react-tap-or-click';

import ScheduleComponent from '../components/schedule';
import ReservationsComponent from '../components/schedule/reservations';
import AppointmentsComponent from '../components/schedule/appointments';

var Schedule = React.createClass({

    getInitialState() {
        return {
            appointmentsOpen: true,
            reservationsOpen: false
        };
    },
    toggleItem(event) {
        var $item = event.target;
        var $schedule = ReactDOM.findDOMNode(this);

        if ($item.getAttribute('data-item') === 'appointments') {
            this.setState({appointmentsOpen: true, reservationsOpen: false});
        } else {
            this.setState({appointmentsOpen: false, reservationsOpen: true});
        }

        $schedule.querySelector('[data-item=appointments]').classList.remove('is-active');
        $schedule.querySelector('[data-item=reservations]').classList.remove('is-active');
        $item.classList.add('is-active');
    },
    render() {
        var $component = AppointmentsComponent;

        if (this.state.reservationsOpen) {
            $component = ReservationsComponent;
        }

        return (
            <div className='main-section-schedule'>
                <div className='row'>
                    <div className='main-schedule-tabs'>
                        <div data-item='appointments' className='main-schedule-tabs-item is-active'
                        {...tapOrClick(this.toggleItem)}>
                            My Appointments
                        </div>
                        <div data-item='reservations' className='main-schedule-tabs-item'
                        {...tapOrClick(this.toggleItem)}>
                            My Reservations
                        </div>
                    </div>
                </div>
                <ScheduleComponent content={$component}/>
            </div>
        );
    }
});

module.exports = Schedule;