import React from 'react';

import OutsideAreaComponent from '../components/outsideArea';

import HeaderView from './header';
import ContainerView from './container';

class Layout extends React.Component {
    render() {
        return (
            <div className='main-layout'>
                <HeaderView />
                <ContainerView />
                <OutsideAreaComponent />
            </div>
        );
    }
}

module.exports = Layout;