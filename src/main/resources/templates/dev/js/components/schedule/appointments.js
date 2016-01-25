import React from 'react';
import tapOrClick from 'react-tap-or-click';

import DateComponent from './date';
import ModalComponent from '../modal';
import ModalReservation from '../modalReservation';

var Appointments = React.createClass({

    getInitialState() {
        return {
            modalOpen: false,
            appointment: {}
        };
    },
    remove(event) {
        var $target = event.currentTarget;
        var appointment = {
            email:  $target.getAttribute('data-email'),
            date: {fromHour: parseFloat($target.getAttribute('data-date'))}
        };
        this.setState({modalOpen: true, appointment: appointment});
    },
    render() {
        var $modalComponent;
        var $thisComponent = this;

        if (this.state.modalOpen) {
            $modalComponent = <ModalComponent
                parent={this}
                content={<ModalReservation
                    parent={this}
                    schedule={this.props.parent}
                    option={'appointment'}
                    appointment={this.state.appointment}

                />}/>
        }
        //{...tapOrClick($thisComponent.remove)}
        return (
            <div>
                {this.props.parent.state.appointments.map(function (appointment, key) {
                    return <div className='main-schedule-item' key={key} >
                        <DateComponent date={appointment.date} />
                        <div className='main-schedule-item-content'>
                            {appointment.data.teacher}
                        </div>
                        <div className='main-schedule-item-cancel' data-email={appointment.data.teacher}
                            data-date={appointment.data.from_hour} {...tapOrClick($thisComponent.remove)}>
                            <i className='fa fa-times'></i>
                        </div>
                    </div>;
                })}

                {$modalComponent}
            </div>
        );
    }
});

module.exports = Appointments;