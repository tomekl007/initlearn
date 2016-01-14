import React from 'react';

import Day from './day';

class Month extends React.Component {
    render() {
        var $days = [];
        for (var i = 0; i < this.props.days; i++) {
            $days.push(<Day day={i} key={i} />);
        }

        return (
            <div className='main-calendar-month'>
                {$days}
            </div>
        );
    }
}

module.exports = Month;