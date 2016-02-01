import React from 'react';
import ReactDOM from 'react-dom';
import tapOrClick from 'react-tap-or-click';

import api from '../ajax/api';
import systemDetection from '../common/systemDetection';

import MessageThreadList from './messageThreadList';

/*TODO improve Teachers class to ES6*/
var Messenger = React.createClass({

    getMessagesIntervalId: 0,
    shiftKeyUp: false,
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
    checkKeyUp(event) {
        if (!systemDetection.isMobileOrTabletDevice) {
            if (event.keyCode === 16) {
                this.shiftKeyUp = true;
            }

            if ((event.which === 13 && !this.shiftKeyUp) ||
                (event.keyCode === 13 && !this.shiftKeyUp)) {
                event.preventDefault();
                this.sendMessage();
            }
        }
    },
    removeShiftKey(event) {
        if (!systemDetection.isMobileOrTabletDevice) {
            if (event.keyCode === 16) {
                this.shiftKeyUp = false;
            }
        }
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
                    <div className='main-messenger-header'>
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
                        <form className='main-messenger-text-input-wrapper'>
                            <textarea className='main-messenger-text-input' ref='mainInput' type='text'
                                onKeyDown={this.checkKeyUp} onKeyUp={this.removeShiftKey}></textarea>
                            <span className='main-messenger-button-submit' {...tapOrClick(this.sendMessage)}><i className='fa fa-paper-plane'></i></span>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
});

module.exports = Messenger;