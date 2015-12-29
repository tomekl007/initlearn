import React from 'react';

var Tests = React.createClass({

    getInitialState() {
        return {
            clicked: false
        }
    },
    handleClick() {
        this.setState({
           clicked: !this.state.clicked
        });
        console.log(this.state.clicked);
    },
    render() {
        return (
            <div>
                <div onClick={this.handleClick}>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
                <div>jeden</div>
            </div>
        );
    }
});

module.exports = Tests;