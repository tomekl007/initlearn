import React from 'react';
import Router from 'react-router';

var Month = React.createClass ({

    getInitialState() {
        var date = Router.HashLocation.getCurrentPath().split('/').slice(-2);

        return {
            year: date[0],
            month: date[1]
        };
    },
    render() {
        console.log('month');
        var $days = [];
        for (var i = 0; i < 31; i++) {
            $days.push(<div className='main-calendar-days' key={i} data-day-nr={i}>
                <a href={'#calendar/' + this.state.year + '/'+ this.state.month +'/' + i} >{i}</a>
            </div>);
        }

        return (
            <div className='main-calendar-month'>
                {$days}
            </div>
        );
    }
})

module.exports = Month;