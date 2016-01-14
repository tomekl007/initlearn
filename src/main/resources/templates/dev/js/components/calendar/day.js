import React from 'react';

class Day extends React.Component {
    render() {
        return (
            <div className='main-calendar-day'>day: {this.props.day}</div>
        );
    }
}

module.exports = Day;