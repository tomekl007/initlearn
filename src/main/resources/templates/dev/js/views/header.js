import React from 'react';

import NavigationComponent from '../components/navigation';

class Header extends React.Component {
    render() {
        return (
            <header id='main-header' className='main-header'>
                <NavigationComponent />
            </header>
        );
    }
}

module.exports = Header;