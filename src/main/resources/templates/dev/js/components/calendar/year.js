import React from 'react';

var Year = React.createClass({

    render() {
        console.log('year');
        var $months = [];
        for (var i = 0; i < 12; i++) {
            $months.push(<div className='main-calendar-months' key={i}>
                <a href={'#calendar/' + this.props.year + '/' + i} >{i}</a>
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