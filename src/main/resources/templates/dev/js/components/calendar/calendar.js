import React from 'react';
import MonthComponent from './month';

class Calendar extends React.Component {
    render() {
        return (
            <div className='main-calendar'>
                <MonthComponent month={0} days={31} />
            </div>
        );
    }
}

module.exports = Calendar;