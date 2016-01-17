import React from 'react';

var Day = React.createClass({

    getInitialState() {
        var path = Router.HashLocation.getCurrentPath();
        var date = path.split('/').slice(-3);

        return {
            year: date[0],
            month: date[1],
            day: date[2]
        };
    },
    getHour(timestamp) {
        return new Date(timestamp).getHours();
    },
    getMinutes(timestamp) {
        return new Date(timestamp).getMinutes() < 30 ? '' : 'half-hour';
    },
    render() {
        console.log(this.props.parent.state.teacherEmail);
        console.log('day');
        var $thisComponent = this;
        var hours = 24;
        var hour = 12;
        var nextHour = 1;
        var timeOfDay = 'am';
        var $hours = [];
        var $hourSlots = [];
        var $reservations = [];

        this.props.parent.state.reservations.forEach(function (data, key) {
            $reservations[$thisComponent.getHour(data.from_hour)] =
                <div className={'main-calendar-day-slot-hour-reservation ' + $thisComponent.getMinutes(data.from_hour)} key={key}>
                    <span className='fw-700'>Reserved by: {data.reserved_by}</span>
                </div>;
        });
        for (var i = 0; i < hours; i++) {

            if (i === 12) {
                nextHour = 1;
                timeOfDay = 'pm';
            }

            $hours.push(<div className='main-calendar-day-hour' key={i}>{hour + timeOfDay}</div>);
            $hourSlots.push(<div className='main-calendar-day-slot' key={i + 1}>
                <div className='main-calendar-day-slot-hour'>
                    <span className='main-calendar-day-slot-hour-booking-class'>
                        <span className='from-hour'>
                            {hour} -
                        </span>
                        <span className='to-hour'>
                            {nextHour}
                        </span>
                    </span>
                    <i className='fa fa-calendar-plus-o'></i>
                </div>
                <div className='main-calendar-day-slot-hour'>
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
        return (
            <div className='main-calendar-day'>
                <div className='main-calendar-day-hours'>
                    {$hours}
                </div>
                <div className='main-calendar-day-slots'>
                    {$hourSlots}
                </div>
            </div>
        );
    }
});

module.exports = Day;