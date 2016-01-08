import React from 'react';
import ReactDOM from 'react-dom';
import tapOrClick from 'react-tap-or-click';

import NavigationListComponent from './navigationList';

var Navigation = React.createClass({
    open(event) {
        event.stopPropagation();
        ReactDOM.findDOMNode(this).classList.add('is-open');
    },
    close(event) {
        event.stopPropagation();
        ReactDOM.findDOMNode(this).classList.remove('is-open');
    },
    render() {
        return (
            <nav className='main-nav sticky pos-top pos-left'>
                <a href='#'>
                    <h1 className='main-logo'>inITLearn</h1>
                </a>
                <div className='main-nav-icon' {...tapOrClick(this.open)}>
                    <div className='main-nav-icon-line line-1'></div>
                    <div className='main-nav-icon-line line-2'></div>
                    <div className='main-nav-icon-line line-3'></div>
                    <div className='main-nav-icon-area sticky pos-left pos-top' {...tapOrClick(this.close)}></div>
                </div>
                <NavigationListComponent/>
            </nav>
        );
    }
});

module.exports = Navigation;