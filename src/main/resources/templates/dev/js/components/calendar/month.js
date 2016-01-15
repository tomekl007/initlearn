import React from 'react';

class Month extends React.Component {
    render() {
        console.log('month');
        var $days = [];
        for (var i = 0; i < this.props.days; i++) {
            $days.push(<div className='main-calendar-days' key={i} data-day-nr={i}>
                <a href={'#calendar/' + this.props.year + '/04/' + i} >{i}</a>
            </div>);
        }

        return (
            <div className='main-calendar-month'>
                {$days}
            </div>
        );
    }
}

module.exports = Month;