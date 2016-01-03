import React from 'react';
import tapOrClick from 'react-tap-or-click';
import $ from '../lib/jquery';

import config from '../ajax/config';

/*TODO improve Teachers class to ES6*/
var Massenger = React.createClass({

    getMessagesIntervalId: 0,
    getInitialState() {

        return {
            open: false,
            messages: [],
            messageThreadList: []
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
            headers: config.loggedHeader,
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

        $.ajax({
            method: 'get',
            url: this.props.url,
            headers: config.loggedHeader,
            success: function (messages) {
                this.setState({messages: messages});
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
            headers: config.loggedHeader,
            success: function (messageThreadList) {
                this.setState({messageThreadList: messageThreadList});
            }.bind(this),
            error: function (jqXHR, statusString, err) {
                console.log(err);
            }
        });
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

        var $thisComponent = this;

        var currentThreadListItem = <li key={0}>
            <a className='main-massenger-message-thread-list-item' href={config.messagesHash + this.props.email}>{this.props.email}</a>
        </li>;

        var messageThreadList = this.state.messageThreadList.map(function(messageThread, key) {
            return (
              <li key={key + 1}>
                <a className='main-massenger-message-thread-list-item' href={config.messagesHash + messageThread.emailTo}>{messageThread.emailTo}</a>
              </li>
            );
        });

        var messagesListItems = this.state.messages.map(function (message, key) {

            var listClass = message.toEmail === $thisComponent.props.email ? 'main-massenger-messages-list-item email-to' : 'main-massenger-messages-list-item';
            return (
                <li className={listClass} key={key}><span>{message.text}</span></li>
            );
        });

        return (
            <div className='main-massenger'>
                <div className='main-massenger-wrapper'>
                    <div className='main-massenger-header fw-700'>
                        conversation with: {this.props.email}
                    </div>
                    <div className='main-massenger-message-thread-list-wrapper'>
                        <ul className='main-massenger-message-thread-list'>
                            {currentThreadListItem}
                            {messageThreadList}
                        </ul>
                    </div>
                    <div className='main-massenger-messages-wrapper'>
                        <ul className='main-massenger-messages-list'>
                            {messagesListItems}
                        </ul>

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