import React from 'react';

import DateComponent from './date';

var Appointment = React.createClass({

    render() {
        return (
            <div>
                Appointment
                <DateComponent />
            </div>
        );
    }
});

module.exports = Appointment;
