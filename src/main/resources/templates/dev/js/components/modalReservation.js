import React from 'react';
import ReactDOM from 'react-dom';
import tapOrClick from 'react-tap-or-click';

import config from '../ajax/config';
import api from '../ajax/api';

import ModalMessageNotificationComponent from './modalMessageNotification';

/*TODO improve Teachers class to ES6*/
var ModalReservation = React.createClass({

    getInitialState() {
        return {
            modalPaymentOpen: false,
            errorMessage: false,
            errorMessageText: ''
        }
    },
    addReservation() {
        var $thisComponent = this;
        var $input = ReactDOM.findDOMNode(this);
        var reservation = {
            fromHour: this.props.reservationDate.timestamp,
            teacher: this.props.teacher,
            subject: $input.querySelector('.main-modal-reservation-subject').value
        };

        api.addReservation(reservation)
            .then(function (data) {
                $thisComponent.props.calendar.add('reservations').toStore(data);
                $thisComponent.setState({modalPaymentOpen: true});
            })
            ['catch'](function (jqXHR) {

            if (jqXHR.status === 409) {
                var errorMessageText = 'There appears to be a problem with dates, please choose other date.';
                $thisComponent.setState({errorMessage: true, errorMessageText: errorMessageText});
            }
        });
    },
    removeReservation() {
        var $thisComponent = this;

        if (this.props.option === 'appointment') {
            var appointment = this.props.appointment;
            api.deleteAppointment(appointment.email, appointment.date)
                .then(function (appointments) {
                    $thisComponent.props.schedule.add('appointments').toStore(appointments);
                    $thisComponent.props.parent.setState({modalOpen: false});
                });
        } else {
            var reservation = this.props.reservation;
            api.deleteReservation(reservation.email, reservation.date)
                .then(function (reservations) {
                    $thisComponent.props.schedule.add('reservations').toStore(reservations);
                    $thisComponent.props.parent.setState({modalOpen: false});
                });
        }
    },
    closeModal() {
        this.props.parent.setState({modalOpen: false});
    },
    render() {

        var $template;

        /*TODO code refactoring*/

        if (this.state.errorMessage) {
            $template = <ModalMessageNotificationComponent
                type={'cancel'}
                message={this.state.errorMessageText}
            />
        } else if (this.state.modalPaymentOpen) {
            $template = <div>
                <div className='main-modal-header row txt-center'>
                    <h2>Payment</h2>
                </div>
                <div className='main-modal-payment-items row txt-center'>
                    <div className='col s12 m6'>
                        <div className='main-modal-payment-item'>
                            <a href={config.paymentPath(this.props.teacher, this.props.reservationDate.timestamp)}
                                className='main-payment-btn' data-paypal-button='true'>
                                <img src='//www.paypalobjects.com/en_US/i/btn/btn_paynow_LG.gif' alt='Pay Now' />
                            </a>
                        </div>
                    </div>
                    <div className='col s12 m6'>
                        <div className='main-modal-payment-item'>
                            <button className='main-btn btn-gold btn-radius' {...tapOrClick(this.closeModal)}>Pay Later</button>
                        </div>
                    </div>
                </div>
            </div>;
        } else {
            if (this.props.option === 'add') {
                $template = <div>
                    <div className='main-modal-header row txt-center'>
                        <h2>Reservation</h2>
                    </div>
                    <div className='main-modal-reservation-items row'>
                        <div className='col s12 m6'>
                            <div className='main-modal-reservation-item'>
                                <i className='fa fa-calendar'></i>
                                Date
                            </div>
                        </div>
                        <div className='col s12 m6'>
                            <div className='main-modal-reservation-item'>
                            {this.props.reservationDate.date},
                            {this.props.reservationDate.hour}
                            </div>
                        </div>
                    </div>
                    <div className='main-modal-reservation-items row'>
                        <div className='col s12 m6'>
                            <div className='main-modal-reservation-item'>
                                <i className='fa fa-tag'></i>
                                Subject
                            </div>
                        </div>
                        <div className='col s12 m6'>
                            <div className='main-modal-reservation-item'>
                                <input className='main-modal-reservation-subject' placeholder='eg. Java inheritance'/>
                            </ div>
                        </div>
                    </div>
                    <div className='main-modal-reservation-items row txt-center'>
                        <div className='col s12 m12'>
                            <div className='main-modal-reservation-item'>
                                <button className='main-btn btn-primary' {...tapOrClick(this.addReservation)}>add reservation</button>
                            </div>
                        </div>
                    </div>
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
        }

        return (
            <div className='main-modal-reservation'>
                {$template}
            </div>
        );
    }
});

module.exports = ModalReservation;