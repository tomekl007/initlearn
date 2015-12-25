import React from 'react';

import HeaderView from './header';
import ContainerView from './container';

class Layout extends React.Component {
    render() {
        return (
            <div className='main-layout'>
                <HeaderView />
                <ContainerView />
            </div>
        );
    }
}

module.exports = Layout;