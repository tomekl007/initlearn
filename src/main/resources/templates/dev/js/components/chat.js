import React from 'react';
import tapOrClick from 'react-tap-or-click';
import $ from '../lib/jquery';

import config from '../ajax/config';

/*TODO improve Teachers class to ES6*/
var Chat = React.createClass({

    getInitialState() {
        return {
            open: false,
            messages: [],
            messageThreadList: []
        };
    },
    sendMessage() {
        var input = this.refs.mainInput.getDOMNode();

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
        setInterval(this.getMessages, 5000);
    },
    render() {

        var messageThreadList = this.state.messageThreadList.map(function(messageThread, key) {
            return (
              <li key={key}>{messageThread.emailTo}</li>
            );
        });

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
                            {this.props.email}
                            {messageThreadList}
                        </div>
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