import React from 'react';
import ReactDOM from 'react-dom';
import tapOrClick from 'react-tap-or-click';
import $ from '../lib/jquery';

import api from '../ajax/api';

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
        var message = {text: input.value, emailTo: this.props.email};

        messages.push({text: input.value});
        this.setState({
            messages: messages
        });

        api.sendMessage(message)
            .then(function (data) {
                console.log(data);
            })
            ['catch'](function (jqXHR) {
            console.log(jqXHR);
        });

        input.value = '';
    },
    getMessages() {
        var $thisComponent = this;

        api.getMessages(this.props.email)
            .then(function (messages) {
                $thisComponent.setState({messages: messages, messagesListVisibility: true});
            })
            ['catch'](function (jqXHR) {
            console.log(jqXHR);
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
                        <MessageThreadList email={this.props.email} messengerComponent={this} interval={true} />
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