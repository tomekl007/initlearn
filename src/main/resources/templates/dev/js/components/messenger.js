import React from 'react';
import ReactDOM from 'react-dom';
import tapOrClick from 'react-tap-or-click';
import $ from '../lib/jquery';

import config from '../ajax/config';

import MessageThreadList from './messageThreadList';

/*TODO improve Teachers class to ES6*/
var Messenger = React.createClass({

    getMessagesIntervalId: 0,
    getInitialState() {

        return {
            messages: [],
            messagesListVisibility: false
        };
    },
    sendMessage() {
        var input = ReactDOM.findDOMNode(this.refs.mainInput);
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
    reloadMessagesList() {
        this.setState({messagesListVisibility: false})
    },
    componentDidMount() {

        this.getMessages();
        this.getMessagesIntervalId = setInterval(this.getMessages, 5000);
    },
    componentWillUnmount() {
        clearInterval(this.getMessagesIntervalId);
    },
    render() {

        var $thisComponent = this;
        var $messagesListItems = [];
        var $Loader = [];


        if (this.state.messagesListVisibility) {
            $messagesListItems = this.state.messages.map(function (message, key) {
                var listClass = message.fromEmail !== $thisComponent.props.email ? 'main-messenger-messages-list-item' : 'main-messenger-messages-list-item email-to';

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
            <div className='main-messenger'>
                <div className='main-messenger-wrapper'>
                    <div className='main-messenger-header fw-700'>
                        conversation with: {this.props.email}
                    </div>
                    <div className='main-messenger-message-thread-list-wrapper'>
                        <MessageThreadList email={this.props.email} messengerComponent={this}/>
                    </div>
                    <div className='main-messenger-messages-wrapper'>
                        <ul className='main-messenger-messages-list'>
                            {$messagesListItems}
                        </ul>
                        {$Loader}
                        <div className='main-messenger-text-input-wrapper'>
                            <textarea className='main-messenger-text-input' ref='mainInput' type='text'></textarea>
                            <button className='main-messenger-button-submit main-btn btn-blue fw-700' {...tapOrClick(this.sendMessage)}>send</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
});

module.exports = Messenger;