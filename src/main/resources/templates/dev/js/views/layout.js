import React from 'react';

import HeaderView from './header';
import ContainerView from './container';
import ChatComponent from '../components/chat';

class Layout extends React.Component {
    render() {
        return (
            <div className='main-layout'>
                <HeaderView />
                <ContainerView />
                <ChatComponent />
            </div>
        );
    }
}

module.exports = Layout;