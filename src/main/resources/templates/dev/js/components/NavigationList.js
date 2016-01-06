import React from 'react';
import tapOrClick from 'react-tap-or-click';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import $ from '../lib/jquery';

import config from '../ajax/config';
import localStorage from '../common/localStorage';

import ModalComponent from './modal';

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
                        url: config.loggedUserUrl,
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

        this.setState({modalOpen: true, loginForm: true, createAccountForm: false});
    },
    /*TODO delete in the future*/
    openCreateAccountForm() {

        this.setState({modalOpen: true, loginForm: false, addUserDataForm: false, createAccountForm: true});
    },
    /*TODO delete in the future*/
    openUserDataForm() {

        this.setState({automaticLogin: false, loginForm: false, addUserDataForm: true})
    },
    render() {

        var $loginElements;
        var $modalElement;
        var $Loader;

        if (this.state.visibility) {
            if (this.state.loggedIn) {

                $loginElements = [
                    <li className='main-nav-list-item main-user-name' key={2}>
                        <a href={config.myProfileHash}>{this.state.data.fullName}</a>
                    </li>,
                    <li className='main-nav-list-item main-user-logout' {...tapOrClick(this.logout)} key={4}>
                        <a href='#'>logout
                            <i className='fa fa-sign-out'></i>
                        </a>
                    </li>
                ];
            } else {
                $loginElements = [
                    <li className='main-nav-list-item main-messages' key={3}>
                        <a href='#msg/willbesoon'>messages
                            <i className='fa fa-comments'></i>
                        </a>
                        <ul className='main-nav-message-thread-list'>
                            <li className='txt-ellipsis'>message thread</li>
                            <li className='txt-ellipsis'>message thread</li>
                            <li className='txt-ellipsis'>message thread</li>
                            <li className='txt-ellipsis'>message thread</li>
                        </ul>
                    </li>,
                    <li className='main-nav-list-item main-create-account' {...tapOrClick(this.openCreateAccountForm)} key={5}>
                        <a href='#create-account-form' className='is-active'>create free account
                            <i className='fa fa-key'></i>
                        </a>
                    </li>,
                    <li className='main-nav-list-item main-sign-in' {...tapOrClick(this.openLoginForm)} key={6}>
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
            $modalElement = <ModalComponent data={this}/>;
        }


        return (
            <ul className='main-nav-list sticky pos-top pos-left'>
                <li className='main-nav-list-item' key={1}>
                    <a href='#teachers'>teachers
                        <i className='fa fa-users'></i>
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