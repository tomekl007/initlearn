import React from 'react';

class Day extends React.Component {
    render() {
        console.log('day');
        var $hours = [];
        for (var i = 0; i < 24; i++) {
            $hours.push(<div className='main-calendar-day-hour' key={i}>{i}</div>);
        }
        return (
            <div className='main-calendar-day'>
                {$hours}
            </div>
        );
    }
}

module.exports = Day;