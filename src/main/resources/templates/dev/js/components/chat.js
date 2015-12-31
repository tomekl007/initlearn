import React from 'react';
import tapOrClick from 'react-tap-or-click';
import $ from '../lib/jquery';

import config from '../ajax/config';
import localStorage from '../common/localStorage';

/*TODO improve Teachers class to ES6*/
var Chat = React.createClass({

    getInitialState() {
        return {
            open: false,
            messages: []
        };
    },
    sendMessage() {
        var input = this.refs.mainInput.getDOMNode();

        $.ajax({
            method: 'post',
            url: config.messageUrl,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.isAvailable() ? config.authorizationPrefix + window.localStorage.getItem('user-token') || '' : ''
            },
            data: JSON.stringify({
                text: input.value,
                emailTo: 'tomekl007@gmail.com'
            }),
            success: function (data) {
                console.log(data);
            },
            error: function (jqXHR, statusString, err) {
                console.log(err);
            }
        });
    },
    getMessages() {

        $.ajax({
            method: 'get',
            url: config.getMessageUrl('tomekl007@gmail.com'),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.isAvailable() ? config.authorizationPrefix + window.localStorage.getItem('user-token') || '' : ''
            },
            success: function (messages) {
                this.setState({messages: messages});
            }.bind(this),
            error: function (jqXHR, statusString, err) {
                console.log(err);
            }
        });
    },
    componentDidMount() {
        this.getMessages();
        setInterval(this.getMessages, 5000);
    },
    render() {
        console.log(this);

        var messagesListItems = this.state.messages.map(function (message, key) {
            return (
                <li className='main-chat-messages-list-item' key={key}>{message.text}</li>
            );
        });

        return (
            <div className='main-chat'>
                <div className='main-chat-wrapper'>
                    <div className='main-chat-header fw-700'>
                        <span className='main-chat-user-name'>tomek</span>
                        <span className='main-chat-header-bar'>
                            <span className='main-chat-button-minimize green'>-</span>
                            <span className='main-chat-button-close green'>x</span>
                        </span>
                    </div>
                    <div className='main-chat-user-description'>
                        <div className='main-chat-user-img'>img</div>
                        <div className='main-chat-user-name fw-700'>tomek</div>
                        <div className='main-chat-user-status'>online</div>
                    </div>
                    <div className='main-chat-messages-container'>
                        <ul className='main-chat-messages-list' ref='mainChatList'>
                            {messagesListItems}
                        </ul>

                        <div className='main-chat-text-input-wrapper'>
                            <textarea className='main-chat-text-input' ref='mainInput' type='text'></textarea>
                            <button className='main-chat-button-submit main-btn btn-green fw-700' {...tapOrClick(this.sendMessage)}>send</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
});

module.exports = Chat;