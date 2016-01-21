import React from 'react';
import tapOrClick from 'react-tap-or-click';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import $ from '../lib/jquery';

import config from '../ajax/config';
import localStorage from '../common/localStorage';

import ModalComponent from './modal';
import LoginFormComponent from './loginForm';
import CreateAccountForm from './createAccountForm';
import AddUserDataForm from './addUserDataForm';
import MessageThreadList from './messageThreadList';
import Search from './search';
import Tooltip from './tooltip';

var NavigationList = React.createClass({


    getInitialState() {
        return {
            visibility: false,
            loggedIn: false,
            modalOpen: false,
            loginForm: false,
            createAccountForm: false,
            addUserDataForm: false,
            automaticLogin: false,
            modalContent: [],
            data: []
        };
    },
    resetFormStates() {
        this.setState({
            modalOpen: false,
            automaticLogin: false,
            loginForm: false,
            createAccountForm: false,
            addUserDataForm: false
        });
    },
    componentDidMount() {
        this.automaticLogin();
    },
    automaticLogin() {

        var $thisComponent = this;

        /*TODO improve AJAX CALLS*/
        $.ajax({
            type: 'get',
            url: config.isUserLoggedInUrl,
            headers: config.apiCallHeader(),
            success: function (isLooggedIn) {
                if (isLooggedIn === true) {
                    $.ajax({
                        type: 'get',
                        url: config.getUserDataUrl,
                        headers: config.apiCallHeader(),
                        success: function (data) {

                            $thisComponent.setState({visibility: true, loggedIn: true, data: data[0]});
                        },
                        error: function (jqXHR, statusString, err) {
                            console.log(err);
                        }
                    });
                } else {
                    $thisComponent.hideLoader();
                }
            },
            error: function (jqXHR, statusString, err) {
                console.log(err);
            }
        });
    },
    logout() {

        var $thisComponent = this;

        /*TODO improve AJAX CALLS*/
        $.ajax({
            type: 'post',
            url: config.logoutUserUrl,
            headers: config.apiCallHeader(),
            success: function () {

                if (localStorage.isAvailable()) {
                    /*TODO improve FB logout*/
                    //FB.logout(function(response) {});
                    window.localStorage.clear();
                    $thisComponent.setState({loggedIn: false});
                }
            },
            error: function (jqXHR, statusString, err) {
                console.log(err);
            }
        });
    },
    hideLoader() {
        this.setState({visibility: true});
    },
    /*TODO delete in the future*/
    openLoginForm() {

        var $modalContent = <LoginFormComponent navigation={this}/>;
        this.setState({modalOpen: true, loginForm: true, createAccountForm: false, modalContent: $modalContent});
    },
    /*TODO delete in the future*/
    openCreateAccountForm() {

        var $modalContent = <CreateAccountForm navigation={this}/>;
        this.setState({
            modalOpen: true,
            loginForm: false,
            addUserDataForm: false,
            createAccountForm: true,
            modalContent: $modalContent
        });
    },
    /*TODO delete in the future*/
    openUserDataForm() {

        var $modalContent = <AddUserDataForm navigation={this}/>;
        this.setState({automaticLogin: false, loginForm: false, addUserDataForm: true, modalContent: $modalContent})
    },
    /*TODO delete in the future*/
    loadMessageThreadList() {
        this.refs.messageThreadList.getMessageThreadList();
    },
    render() {

        var $loginElements;
        var $Loader;
        var $modalElement;

        if (this.state.visibility) {
            if (this.state.loggedIn) {

                $loginElements = [
                    <li className='main-nav-list-item main-user-name' key={3}>
                        <a href={config.myProfileHash}>{this.state.data.fullName}</a>
                    </li>,
                    <li className='main-nav-list-item main-nav-messages' key={4}
                        onMouseEnter={this.loadMessageThreadList}
                    {...tapOrClick(this.loadMessageThreadList)}>
                        <a href='javascript: void 0;'>messages
                            <i className='fa fa-comments'></i>
                        </a>
                        <Tooltip content={<MessageThreadList interval={false} ref='messageThreadList' />} />
                    </li>,
                    <li className='main-nav-list-item main-user-logout' {...tapOrClick(this.logout)} key={5}>
                        <a href='#'>logout
                            <i className='fa fa-sign-out'></i>
                        </a>
                    </li>
                ];
            } else {
                $loginElements = [
                    <li className='main-nav-list-item main-create-account' {...tapOrClick(this.openCreateAccountForm)} key={6}>
                        <a href='#create-account-form' className='is-active'>create free account
                            <i className='fa fa-key'></i>
                        </a>
                    </li>,
                    <li className='main-nav-list-item main-sign-in' {...tapOrClick(this.openLoginForm)} key={7}>
                        <a href='#sign-in-form'>sign in
                            <i className='fa fa-sign-in'></i>
                        </a>
                    </li>
                ]
            }
        } else {
            $Loader = <li className='main-nav-list-item main-load txt-center'>
                <i className='fa fa-circle main-loader-inline-item-1'></i>
                <i className='fa fa-circle main-loader-inline-item-2'></i>
                <i className='fa fa-circle main-loader-inline-item-3'></i>
            </li>;
        }

        if (this.state.modalOpen) {
            $modalElement = <ModalComponent ref='modal' parent={this} content={this.state.modalContent}/>;
        }

        /*TODO move search component */
        return (
            <ul className='main-nav-list sticky pos-top pos-left'>
                <li className='main-nav-list-item main-search fw-700' key={0}>
                    <a href='javascript: void 0;'>
                        <i className='fa fa-search main-search-icon'></i>
                        <Search />
                    </a>
                </li>
                <li className='main-nav-list-item' key={1}>
                    <a href='#teachers'>teachers
                        <i className='fa fa-users'></i>
                    </a>
                </li>
                <li className='main-nav-list-item' key={2}>
                    <a href='#schedule'>schedule
                        <i className='fa fa-list-alt'></i>
                    </a>
                </li>
                {$Loader}
                {$loginElements}
                <ReactCSSTransitionGroup transitionName='main-modal-transition' transitionEnterTimeout={300} transitionLeaveTimeout={300}>
                    {$modalElement}
                </ReactCSSTransitionGroup>
            </ul>
        );
    }
});

module.exports = NavigationList;