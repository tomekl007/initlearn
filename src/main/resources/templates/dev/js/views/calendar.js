import React from 'react';

import CalendarComponent from '../components/calendar/calendar';

import config from '../ajax/config';

class Calendar extends React.Component {
    render() {
        return (
            <div className='main-section-calendar'>
                <CalendarComponent />
            </div>
        );
    }
}

module.exports = Calendar;