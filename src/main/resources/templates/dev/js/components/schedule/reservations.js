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
                {this.props.parent.state.reservations.map(function (reservation, key) {
                    return <div className='main-schedule-item row' key={key} >
                        <DateComponent date={reservation.date} />
                        <div className='main-schedule-item-content'>
                            <div>Teacher: {reservation.data.reserved_by}</div>
                            <div>Subject: {reservation.data.subject}</div>
                        </div>
                        <div className='main-schedule-item-cancel' data-email={reservation.data.reserved_by}
                            data-date={reservation.data.from_hour} {...tapOrClick($thisComponent.remove)}>
                            <i className='fa fa-times'></i>
                        </div>
                    </div>;
                })}

                {$modalComponent}
            </div>
        );
    }
});

module.exports = Reservations;