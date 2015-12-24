import React from 'react';

import NavigationListComponent from './navigationList';

var userData = {name:'bartek', href: '#/me'};

class Navigation extends React.Component {
    render() {
        return (
            <nav className='main-nav sticky pos-top pos-left'>
                <a href='#'>
                    <h1 className='main-logo'>inITLearn</h1>
                </a>
                <div className='main-nav-icon js-main-nav-menu-open'>
                    <div className='main-nav-icon-line line-1'></div>
                    <div className='main-nav-icon-line line-2'></div>
                    <div className='main-nav-icon-line line-3'></div>
                    <div className='main-nav-icon-area sticky pos-left pos-top'></div>
                </div>
                <NavigationListComponent data={userData}/>
            </nav>
        );
    }
}

module.exports = Navigation;