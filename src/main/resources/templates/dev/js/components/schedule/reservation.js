import React from 'react';

import DateComponent from './date';

var Reservation = React.createClass({

    render() {
        return (
            <div>
                Reservation
                <DateComponent />
            </div>
        );
    }
});

module.exports = Reservation;
