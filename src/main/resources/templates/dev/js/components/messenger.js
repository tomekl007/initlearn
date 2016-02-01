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
        /*TODO code refactoring*/
        var $thisComponentNode = ReactDOM.findDOMNode(this);
        var $input = $thisComponentNode.querySelector('.main-messenger-text-input');
        var messages = this.state.messages;
        var message = {text: $input.value, emailTo: this.props.email};

        messages.push({text: $input.value});
        this.setState({
            messages: messages
        });

        api.sendMessage(message)
            .then(function (data) {
                console.log(data);
                var $messageList = $thisComponentNode
                    .querySelector('.main-messenger-messages-list');
                $messageList.scrollTop = $messageList.scrollHeight;
            })
            ['catch'](function (jqXHR) {
            console.log(jqXHR);
        });

        $input.value = '';
    },
    getMessages() {
        /*TODO code refactoring*/
        var $thisComponent = this;
        var $messageList = ReactDOM.findDOMNode(this)
            .querySelector('.main-messenger-messages-list');

        api.getMessages(this.props.email)
            .then(function (messages) {
                var isNewMessage = messages.length !== $thisComponent.state.messages.length;
                $thisComponent.setState({messages: messages, messagesListVisibility: true});
                isNewMessage ? $messageList.scrollTop = $messageList.scrollHeight : false;
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

        this.calculateInputHeight(event.target);
    },
    removeShiftKey(event) {
        if (!systemDetection.isMobileOrTabletDevice) {
            if (event.keyCode === 16) {
                this.shiftKeyUp = false;
            }
        }
    },
    calculateInputHeight(target) {
        var defaultHeight = parseInt(target.getAttribute('data-height'));
        var maxHeight = parseInt(target.getAttribute('data-max-height'));
        var scrollTop = target.scrollTop;

        if (defaultHeight + scrollTop < maxHeight) {
            target.setAttribute('style', 'height : ' + (defaultHeight + scrollTop) + 'px');
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
                            <textarea className='main-messenger-text-input' type='text' data-height='50' data-max-height='80'
                                onKeyDown={this.checkKeyUp} onKeyUp={this.removeShiftKey}></textarea>
                            <span className='main-messenger-button-submit' {...tapOrClick(this.sendMessage)}>
                                <i className='fa fa-paper-plane'></i>
                            </span>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
});

module.exports = Messenger;