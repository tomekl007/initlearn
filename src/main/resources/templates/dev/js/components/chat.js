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
        var sendToMail = this.refs.sendToMail.getDOMNode();
        var input = this.refs.mainInput.getDOMNode();

        console.log('input: ');
        console.log(input.value);

        console.log('mail: ');
        console.log(sendToMail.value);

        $.ajax({
            method: 'post',
            url: config.messageUrl,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.isAvailable() ? config.authorizationPrefix + window.localStorage.getItem('user-token') || '' : ''
            },
            data: JSON.stringify({
                text: input.value,
                emailTo: sendToMail.value
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
        var messagesFromMail = this.refs.messagesFromMail.getDOMNode();

        $.ajax({
            method: 'get',
            url: config.getMessageUrl(messagesFromMail.value),
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
                        <span className='main-chat-user-name'>conversation with:</span>
                        <span className='main-chat-header-bar'>
                            <span className='main-chat-button-minimize green'>-</span>
                            <span className='main-chat-button-close green'>x</span>
                        </span>
                    </div>
                    <div className='main-chat-user-description'>
                        <div className='main-chat-user-img'>img</div>
                        <div className='main-chat-user-name fw-700'>
                            <input className='color-blue' ref='messagesFromMail' type='text'/>
                        </div>
                        <div className='main-chat-user-status'>online</div>
                    </div>
                    <div className='main-chat-messages-container'>
                        <ul className='main-chat-messages-list' ref='mainChatList'>
                            {messagesListItems}
                        </ul>

                        <div className='main-chat-text-input-wrapper'>
                            <textarea className='main-chat-text-input' ref='mainInput' type='text'></textarea>
                            <input ref='sendToMail' type='text'/>
                            <button className='main-chat-button-submit main-btn btn-green fw-700' {...tapOrClick(this.sendMessage)}>send</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
});

module.exports = Chat;