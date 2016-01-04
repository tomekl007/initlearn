import React from 'react';
import tapOrClick from 'react-tap-or-click';
import $ from '../lib/jquery';

import config from '../ajax/config';

/*TODO improve Teachers class to ES6*/
var Massenger = React.createClass({

    getMessagesIntervalId: 0,
    getInitialState() {

        return {
            messages: [],
            messageThreadList: [],
            messagesListVisibility: false
        };
    },
    sendMessage() {
        var input = this.refs.mainInput.getDOMNode();
        var messages = this.state.messages;

        messages.push({text: input.value});
        this.setState({
            messages: messages
        });

        $.ajax({
            method: 'post',
            url: config.messagesUrl,
            headers: config.apiCallHeader(),
            data: JSON.stringify({
                text: input.value,
                emailTo: this.props.email
            }),
            success: function (data) {
                console.log(data);
            },
            error: function (jqXHR, statusString, err) {
                console.log(err);
            }
        });

        input.value = '';
    },
    getMessages() {
        console.log('get messages');

        $.ajax({
            method: 'get',
            url: this.props.url,
            headers: config.apiCallHeader(),
            success: function (messages) {
                this.setState({messages: messages, messagesListVisibility: true});
            }.bind(this),
            error: function (jqXHR, statusString, err) {
                console.log(err);
            }
        });
    },
    getMessageThreadList() {

        $.ajax({
            method: 'get',
            url: config.messagesOverviewUrl,
            headers: config.apiCallHeader(),
            success: function (messageThreadList) {
                this.setState({messageThreadList: messageThreadList});
            }.bind(this),
            error: function (jqXHR, statusString, err) {
                console.log(err);
            }
        });
    },
    reloadMessagesList() {
        this.setState({messagesListVisibility: false})
    },
    componentDidMount() {

        this.getMessageThreadList();
        this.getMessages();
        this.getMessagesIntervalId = setInterval(this.getMessages, 5000);
    },
    componentWillUnmount() {
        clearInterval(this.getMessagesIntervalId);
    },
    render() {

        var isSameEmail = false;
        var $thisComponent = this;
        var $currentThreadListItem = [];
        var $messagesListItems = [];
        var $Loader = [];

        var $messageThreadList = this.state.messageThreadList.map(function (messageThread, key) {
            messageThread.toEmail === $thisComponent.props.email ? (isSameEmail = true) : false;

            return (
                <li key={key + 1} {...tapOrClick(this.reloadMessagesList)} >
                    <a className='main-massenger-message-thread-list-item' href={config.messagesHash + messageThread.emailTo}>
                        <span className='main-massenger-message-thread-list-item-email ellipsis' >{messageThread.emailTo}</span>
                        <span className='main-massenger-message-thread-list-item-last-message ellipsis' >{messageThread.lastMessage.text}</span>
                    </a>
                </li>
            );
        });

        if (isSameEmail || $messageThreadList.length < 1) {
            $currentThreadListItem = <li key={0} {...tapOrClick(this.reloadMessagesList)} >
                <a className='main-massenger-message-thread-list-item' href={config.messagesHash + this.props.email}>
                    <span className='main-massenger-message-thread-list-item-email ellipsis' >{this.props.email}</span>
                </a>
            </li>;
        }

        if (this.state.messagesListVisibility) {
            $messagesListItems = this.state.messages.map(function (message, key) {
                var listClass = message.fromEmail !== $thisComponent.props.email ? 'main-massenger-messages-list-item' : 'main-massenger-messages-list-item email-to';

                return (
                    <li className={listClass} key={key}>
                        <span>{message.text}</span>
                    </li>
                );
            });
        } else {
            $Loader = <div className='main-loader'>
                        <i className='fa fa-spinner'></i>
                    </div>;
        }

        return (
            <div className='main-massenger'>
                <div className='main-massenger-wrapper'>
                    <div className='main-massenger-header fw-700'>
                        conversation with: {this.props.email}
                    </div>
                    <div className='main-massenger-message-thread-list-wrapper'>
                        <ul className='main-massenger-message-thread-list'>
                            {$currentThreadListItem}
                            {$messageThreadList}
                        </ul>
                    </div>
                    <div className='main-massenger-messages-wrapper'>
                        <ul className='main-massenger-messages-list'>
                            {$messagesListItems}
                        </ul>
                        {$Loader}
                        <div className='main-massenger-text-input-wrapper'>
                            <textarea className='main-massenger-text-input' ref='mainInput' type='text'></textarea>
                            <button className='main-massenger-button-submit main-btn btn-blue fw-700' {...tapOrClick(this.sendMessage)}>send</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
});

module.exports = Massenger;