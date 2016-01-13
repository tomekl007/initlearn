import React from 'react';
import tapOrClick from 'react-tap-or-click';
import $ from '../lib/jquery';

import config from '../ajax/config';

import api from '../ajax/api';

/*TODO improve Teachers class to ES6*/
var MessageThreadList = React.createClass({

    getMessageThreadListIntervalId: 0,
    getInitialState() {

        return {
            messageThreadList: [],
            messageThreadListVisibility: false
        };
    },
    getMessageThreadList() {
        console.log('get message thread list');
        api.getMessagesOverview()
            .then(this.refresh)
            ['catch'](function(jqXHR) {
                console.log(jqXHR);
            });
    },
    refresh(messageThreadList) {
        console.log(typeof messageThreadList);
        this.setState({messageThreadList: messageThreadList, messageThreadListVisibility: true});
    },
    /*TODO code refactoring needed*/
    reloadMessangerMessagesList() {

        var $messengerComponent = this.props.messengerComponent;
        if (typeof $messengerComponent !== 'undefined') {
            $messengerComponent.reloadMessagesList();
        }
    },
    componentDidMount() {

        this.getMessageThreadList();

        if (this.props.interval) {
            this.getMessageThreadListIntervalId = setInterval(this.getMessageThreadList, 5000);
        }
    },
    componentWillUnmount() {
        clearInterval(this.getMessageThreadListIntervalId);
    },
    render() {

        var $thisComponent = this;
        var $messageThreadList = [];
        var $Loader = [];

        if (this.state.messageThreadListVisibility) {
            $messageThreadList = this.state.messageThreadList.map(function (messageThread, key) {

                return (
                    <li key={key + 1} >
                        <a className='main-message-thread-list-item' href={config.messagesHash + messageThread.emailTo}
                        {...tapOrClick($thisComponent.reloadMessangerMessagesList)}>
                            <span className='main-message-thread-list-item-email txt-ellipsis' >{messageThread.emailTo}</span>
                            <span className='main-message-thread-list-item-last-message txt-ellipsis' >{messageThread.lastMessage.text}</span>
                        </a>
                    </li>
                );
            });

            if ($messageThreadList.length < 1) {
                if (typeof this.props.email !== 'undefined') {
                    $messageThreadList = <li key={0} >
                        <a className='main-message-thread-list-item' href={config.messagesHash + this.props.email}
                        {...tapOrClick(this.reloadMessangerMessagesList)} >
                            <span className='main-message-thread-list-item-email txt-ellipsis' >{this.props.email}</span>
                        </a>
                    </li>;
                } else {
                    $messageThreadList = <li key={0} >
                        <a className='main-message-thread-list-item'>
                            <span className='main-message-thread-list-item-email txt-ellipsis' >0 messages</span>
                        </a>
                    </li>;
                }
            }
        } else {
            $Loader = <div className='main-loader'>
                <i className='fa fa-spinner'></i>
            </div>;
        }

        return (
            <ul className='main-message-thread-list'>
                {$messageThreadList}
                {$Loader}
            </ul>
        );
    }
});

module.exports = MessageThreadList;