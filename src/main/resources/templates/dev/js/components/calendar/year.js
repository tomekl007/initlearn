import React from 'react';
import Router from 'react-router';

var Year = React.createClass ({

    getInitialState() {
        return {
            year: Router.HashLocation.getCurrentPath().split('/').slice(-1),
            months: 12
        };
    },
    render() {
        console.log('year');
        var $months = [];
        for (var i = 0; i < this.state.months; i++) {
            $months.push(<div className='main-calendar-months' key={i}>
                <a href={'#calendar/' + this.state.year + '/' + i} >{i}</a>
            </div>);
        }

        return (
            <div className='main-calendar-year'>
                {$months}
            </div>
        );
    }
});

module.exports = Year;