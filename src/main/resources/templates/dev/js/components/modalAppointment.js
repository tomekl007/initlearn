import React from 'react';
import tapOrClick from 'react-tap-or-click';

import api from '../ajax/api';

/*TODO improve Teachers class to ES6*/
var ModalAppointment = React.createClass({

    addReservation() {
        var reservation = {
            fromHour: new Date(this.props.appointmentDate.date + ',' + this.props.appointmentDate.hourFrom).getTime(),
            teacher: this.props.teacher
        };

        api.addReservation(reservation).then(function (data) {
            console.log(data);
        });
    },
    render() {
        console.log(new Date(this.props.appointmentDate.date + ',' + this.props.appointmentDate.hourFrom));
        console.log(new Date(this.props.appointmentDate.date + ',' + this.props.appointmentDate.hourTo));
        return (
            <div className='main-modal-appointment'>
                <h2 className='main-modal-appointment-header'>Appoinment</h2>
                <div>
                    {this.props.appointmentDate.date},
                    {this.props.appointmentDate.hour}
                </div>
                <span>Subject: </span>
                <input className='main-modal-appointment-subject'/>

                <button className='main-btn btn-primary fw-700' {...tapOrClick(this.addReservation)}>send</button>
            </div>
        );
    }
});

module.exports = ModalAppointment;