import React from 'react';
import ReactDOM from 'react-dom';
import tapOrClick from 'react-tap-or-click';

import api from '../ajax/api';

/*TODO improve Teachers class to ES6*/
var ModalReservation = React.createClass({

    addReservation() {
        var $thisComponent = this;
        var $input = ReactDOM.findDOMNode(this);
        var reservation = {
            fromHour: new Date(this.props.reservationDate.date + ',' +
            this.props.reservationDate.hourFrom).getTime(),
            teacher: this.props.teacher,
            subject: $input.querySelector('.main-modal-reservation-subject').value
        };

        api.addReservation(reservation)
            .then(function (data) {
                $thisComponent.props.calendar.add('reservations').toStore(data);
                $thisComponent.props.parent.setState({modalOpen: false});
            });
    },
    removeReservation() {
        var $thisComponent = this;
        console.log(this.props.option);

        if (this.props.option === 'appointment') {
            var appointment = this.props.appointment;
            api.deleteAppointment(appointment.email, appointment.date)
                .then(function(/*data*/) {
                    //$thisComponent.props.schedule.add('reservations').toStore(data);
                    $thisComponent.props.parent.setState({modalOpen: false});
                });
        } else {
            var reservation = this.props.reservation;
            api.deleteReservation(reservation.email, reservation.date)
                .then(function(/*data*/) {
                    //$thisComponent.props.schedule.add('reservations').toStore(data);
                    $thisComponent.props.parent.setState({modalOpen: false});
                });
        }
    },
    render() {

        var $template;

        if (this.props.option === 'add') {
            $template = <div>
                <h2 className='main-modal-reservation-header'>Reservation</h2>
                <div>
                    {this.props.reservationDate.date},
                    {this.props.reservationDate.hour}
                </div>
                <span>Subject: </span>
                <input className='main-modal-reservation-subject'/>

                <button className='main-btn btn-primary fw-700' {...tapOrClick(this.addReservation)}>send</button>
            </div>
        } else {
                $template = <div>
                <div className='main-modal-message-notification-icon warning'>
                    <i className='fa fa-exclamation-circle'></i>
                </div>
                <div>Do you really want to cancel that lesson</div>
                <button className='main-btn btn-primary fw-700' {...tapOrClick(this.removeReservation)}>yes</button>
                </div>
        }

        return (
            <div className='main-modal-reservation'>
                {$template}
            </div>
        );
    }
});

module.exports = ModalReservation;