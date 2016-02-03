import React from 'react';

import HeaderView from './header';
import ContainerView from './container';
import FooterView from './footer';

class Layout extends React.Component {
    render() {
        return (
            <div className='main-layout'>
                <HeaderView />
                <ContainerView />
                <FooterView />
            </div>
        );
    }
}

module.exports = Layout;