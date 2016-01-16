import React from 'react';

class Day extends React.Component {
    render() {
        console.log('day');

        var hours = 24;
        var hour = 12;
        var timeOfDay = 'am';
        var $hours = [];
        var $appointmentSlots = [];

        for (var i = 0; i < hours; i++) {

            if (i === 1 || i === 13) {
                hour = 1;
                timeOfDay = 'pm';
            }

            $hours.push(<div className='main-calendar-day-hour' key={i}>{hour + timeOfDay}</div>);
            $appointmentSlots.push(<div className='main-calendar-day-appointment-slot' key={i + 1}>
                <div className='main-calendar-day-appointment-slot-hour'></div>
                <div className='main-calendar-day-appointment-slot-hour'></div>
            </div>);

            hour++;
        }
        return (
            <div className='main-calendar-day'>
                <div className='main-calendar-day-hours'>
                    {$hours}
                </div>
                <div className='main-calendar-day-appointment-slots'>
                    {$appointmentSlots}
                </div>
            </div>
        );
    }
}

module.exports = Day;