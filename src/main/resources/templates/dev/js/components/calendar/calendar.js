import React from 'react';
import Router from 'react-router';

import CalendarYearComponent from './year';
import CalendarMonthComponent from './month';
import CalendarDayComponent from './day';

var dateComponents = [<CalendarYearComponent year={2016}/>, <CalendarMonthComponent year={2016} days={31}/>, <CalendarDayComponent/>];

var Calendar = React.createClass({

    getInitialState() {
        return null;
    },
    getComponentDateFromPath() {
        var dateFormat = Router.HashLocation.getCurrentPath().replace('/calendar/', '').split('/').slice(-3).length - 1;
        return dateComponents[dateFormat];
    },
    render() {
        return (
            <div>
            {this.getComponentDateFromPath()}
            </div>
        );
    }
});

module.exports = Calendar;