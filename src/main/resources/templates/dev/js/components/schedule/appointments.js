import React from 'react';
import tapOrClick from 'react-tap-or-click';

import config from '../../ajax/config';

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
            email: $target.getAttribute('data-email'),
            date: {fromHour: parseFloat($target.getAttribute('data-date'))}
        };
        this.setState({modalOpen: true, appointment: appointment});
    },
    render() {
        var $modalComponent;
        var $paynowBtn;
        var $cancelBtn;
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

        return (
            <div>
                {this.props.parent.state.appointmentsWithPayments.map(function (appointment, key) {
                    var reservation = appointment.data.reservation;
                    var payment = appointment.data.payment;
                    $paynowBtn = [];
                    $cancelBtn = [];

                    if (payment.payment_status !== 'COMPLETED') {
                        $paynowBtn = <a href={config.paymentPath(reservation.teacher, reservation.from_hour)}
                            className='main-payment-btn float-right' data-paypal-button='true'>
                            <img src='//www.paypalobjects.com/en_US/i/btn/btn_paynow_LG.gif' alt='Pay Now' />
                        </a>;

                        $cancelBtn = <div className='main-schedule-item-cancel' data-email={reservation.teacher}
                            data-date={reservation.from_hour} {...tapOrClick($thisComponent.remove)}>
                            <i className='fa fa-times'></i>
                        </div>;
                    }

                    return <div className='main-schedule-item row ' key={key} >
                        <DateComponent date={appointment.date} />
                        <div className='main-schedule-item-content'>
                            <div className='main-schedule-item-content-items row'>
                                <div className='main-schedule-item-content-item col s12 m6'>
                                    <i className='fa fa-users'></i>
                                    Teacher:
                                </div>
                                <div className='main-schedule-item-content-item col s12 m6'>
                                    {reservation.teacher}
                                </div>
                            </div>
                            <div className='main-schedule-item-content-items row'>
                                <div className='main-schedule-item-content-item col s12 m6'>
                                    <i className='fa fa-tag'></i>
                                    Subject:
                                </div>
                                <div className='main-schedule-item-content-item col s12 m6'>
                                    {reservation.subject}
                                </div>
                            </div>
                            <div className='main-schedule-item-content-items row'>
                                <div className='main-schedule-item-content-item col s12 m6'>
                                    <i className='fa fa-hourglass-half'></i>
                                    Payment status:
                                </div>
                                <div className={'main-schedule-item-content-item col s12 m6 fw-700 ' + payment.payment_status}>
                                    {payment.payment_status}
                                </div>
                            </div>
                            <div className='main-schedule-item-content-items row'>
                                <div className='main-schedule-item-content-item col s12 m6'>
                                    <i className='fa fa-money'></i>
                                    Payment amount:
                                </div>
                                <div className='main-schedule-item-content-item col s12 m6 fw-700'>
                                    {payment.amount}
                                </div>
                            </div>
                            {$paynowBtn}
                        </div>
                        {$cancelBtn}
                    </div>;
                })}

                {$modalComponent}
            </div>
        );
    }
});

module.exports = Appointments;