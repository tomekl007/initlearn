import React from 'react';
import Router from 'react-router';

import config from '../ajax/config';

import MessengerComponent from '../components/messenger';

var Messages = React.createClass({

    getUserEmail() {
        var pathArray = Router.HashLocation.getCurrentPath().split('/');

        return pathArray[pathArray.length - 1];
    },
    render() {
        return (
            <section id='messages' className='main-section-messages'>
                <MessengerComponent url={config.appUrl + Router.HashLocation.getCurrentPath()} email={this.getUserEmail()}/>
            </section>
        );
    }
});

module.exports = Messages;