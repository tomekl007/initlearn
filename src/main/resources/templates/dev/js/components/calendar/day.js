import React from 'react';
import Router from 'react-router';
import tapOrClick from 'react-tap-or-click';

import ModalComponent from '../../components/modal';
import ModalReservation from '../../components/modalReservation';

var Day = React.createClass({

    getInitialState() {
        var path = Router.HashLocation.getCurrentPath();
        var date = path.split('/').slice(-3);

        return {
            modalOpen: false,
            reservationDate: {},
            year: parseInt(date[0]),
            month: parseInt(date[1]),
            day: parseInt(date[2])
        };
    },
    getHour(timestamp) {
        return new Date(timestamp).getHours();
    },
    getMinutes(timestamp) {
        return new Date(timestamp).getMinutes() < 30 ? '' : 'half-hour';
    },
    selectDate(event) {
        var $target = event.currentTarget;
        var hour = $target.getAttribute('data-hour');
        var hourFrom = $target.getAttribute('data-hour-from');
        var hourTo = $target.getAttribute('data-hour-to');
        var date = this.state.year + '/' + this.state.month + '/' +
            this.state.day;

        this.setState({
            modalOpen: true, reservationDate: {
                date: date, hour: hour, hourFrom: hourFrom, hourTo: hourTo
            }
        });
    },
    render() {
        var $thisComponent = this;
        var hours = 24;
        var hour = 12;
        var nextHour = 1;
        var timeOfDay = 'am';
        var $hours = [];
        var $hourSlots = [];
        var $reservations = [];
        var $modalComponent;

        this.props.parent.state.reservations.forEach(function (reservation, key) {
            var year = reservation.date.year;
            var month = reservation.date.month;
            var day = reservation.date.day;

            if ($thisComponent.state.month === month &&
                $thisComponent.state.year === year &&
                $thisComponent.state.day === day) {

                var hour = reservation.date.hour;
                $reservations[hour] = <div className={'main-calendar-day-slot-hour-reservation ' + $thisComponent.getMinutes(reservation.data.from_hour)} key={key}>
                    <span className='fw-700'>Reserved by: {reservation.data.reserved_by}</span>
                </div>;
            }
        });

        for (var i = 0; i < hours; i++) {

            if (i === 12) {
                nextHour = 1;
                timeOfDay = 'pm';
            }

            $hours.push(<div className='main-calendar-day-hour' key={i}>{hour + timeOfDay}</div>);
            $hourSlots.push(<div className='main-calendar-day-slot' key={i + 1}>
                <div className='main-calendar-day-slot-hour' data-hour={hour + ':00 - ' + nextHour + ':00'}
                    data-hour-from={i + ':00'} data-hour-to={(i + 1) + ':00'}  {...tapOrClick(this.selectDate)}>
                    <span className='main-calendar-day-slot-hour-booking-class'>
                        <span className='from-hour'>
                            {hour + ':00'} -
                        </span>
                        <span className='to-hour'>
                            {nextHour + ':00'}
                        </span>
                    </span>
                    <i className='fa fa-calendar-plus-o'></i>
                </div>
                <div className='main-calendar-day-slot-hour' data-hour={hour + ':30 - ' + nextHour + ':30'}
                    data-hour-from={i + ':30'} data-hour-to={(i + 1) + ':30'} {...tapOrClick(this.selectDate)}>
                    <span className='main-calendar-day-slot-hour-booking-class'>
                        <span className='from-hour'>
                            {hour + ':30'} -
                        </span>
                        <span className='to-hour'>
                            {nextHour + ':30'}
                        </span>
                    </span>
                    <i className='fa fa-calendar-plus-o'></i>
                </div>
                {$reservations[i]}
            </div>);

            hour = nextHour;
            nextHour++;
        }

        if (this.state.modalOpen) {
            $modalComponent = <ModalComponent
                parent={this}
                content={<ModalReservation
                    parent={this}
                    reservationDate={this.state.reservationDate}
                    teacher={this.props.parent.state.email}
                    calendar={this.props.parent}/>}/>
        }

        return (
            <div className='main-calendar-day'>
                <div className='main-calendar-day-hours'>
                    {$hours}
                </div>
                <div className='main-calendar-day-slots'>
                    {$hourSlots}
                </div>
                {$modalComponent}
            </div>
        );
    }
});

module.exports = Day;