import React from 'react';
import tapOrClick from 'react-tap-or-click';

import DateComponent from './date';
import ModalComponent from '../modal';
import ModalReservation from '../modalReservation';

var Reservations = React.createClass({

    getInitialState() {
        return {
            modalOpen: false,
            reservation: {}
        };
    },
    remove(event) {
        var $target = event.currentTarget;
        var reservation = {
            email:  $target.getAttribute('data-email'),
            date: {fromHour: parseFloat($target.getAttribute('data-date'))}
        };
        this.setState({modalOpen: true, reservation: reservation});
    },
    render() {
        var $modalComponent;
        var $cancelBtn;
        var $thisComponent = this;

        if (this.state.modalOpen) {
            $modalComponent = <ModalComponent
                parent={this}
                content={<ModalReservation
                    parent={this}
                    schedule={this.props.parent}
                    option={'reservation'}
                    reservation={this.state.reservation}

                />}/>
        }

        return (
            <div>
                {this.props.parent.state.reservationsWithPayments.map(function (reservationItem, key) {
                    var reservation = reservationItem.data.reservation;
                    var payment = reservationItem.data.payment;
                    $cancelBtn = [];

                    if (payment.payment_status !== 'COMPLETED') {
                        $cancelBtn = <div className='main-schedule-item-cancel' data-email={reservation.reserved_by}
                            data-date={reservation.from_hour} {...tapOrClick($thisComponent.remove)}>
                            <i className='fa fa-times'></i>
                        </div>;
                    }

                    return <div className={'main-schedule-item row ' + payment.payment_status || ''} key={key} >
                        <DateComponent date={reservationItem.date} />
                        <div className='main-schedule-item-content'>
                            <div className='main-schedule-item-content-items row'>
                                <div className='main-schedule-item-content-item col s12 m6'>
                                    <i className='fa fa-users'></i>
                                    Reserved by:
                                </div>
                                <div className='main-schedule-item-content-item col s12 m6'>
                                    {reservation.reserved_by}
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
                        </div>
                        {$cancelBtn}
                    </div>;
                })}

                {$modalComponent}
            </div>
        );
    }
});

module.exports = Reservations;