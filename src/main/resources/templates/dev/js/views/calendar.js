import React from 'react';
import Router from 'react-router';

import config from '../ajax/config';

import ScheduleComponent from '../components/schedule';
import CalendarYearComponent from '../components/calendar/year';
import CalendarMonthComponent from '../components/calendar/month';
import CalendarDayComponent from '../components/calendar/day';

class Calendar extends React.Component {

    getDateComponentFromPath() {
        var dateComponents = [
            CalendarYearComponent,
            CalendarMonthComponent,
            CalendarDayComponent];
        var path = Router.HashLocation.getCurrentPath();
        var dateFormat = path.substring(path.indexOf(config.calendarPath))
                .replace(config.calendarPath, '')
                .split('/').slice(-3)
                .length - 1;

        return dateComponents[dateFormat];
    }

    render() {
        return (
            <section className='main-section-calendar'>
                <ScheduleComponent content={this.getDateComponentFromPath()}/>
            </section>
        );
    }
}

module.exports = Calendar;