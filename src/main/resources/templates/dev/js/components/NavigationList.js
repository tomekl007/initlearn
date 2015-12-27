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
            isLoggedIn: false,
            isModalOpen: false,
            isLoginForm: false,
            isCreateAccountForm: false,
            isAutomaticLogin: false,
            data: []
        };
    },
    componentDidMount() {
        this.login();
    },
    login() {

        var $thisComponent = this;

        /*TODO improve AJAX CALLS*/
        $.ajax({
            url: config.isUserLoggedInUrl,
            success: function (isLooggedIn) {
                if (isLooggedIn === true) {
                    $.ajax({
                        url: config.loggedUserUrl,
                        headers: {
                            /*TODO improve local storage*/
                            'Authorization': localStorage.isAvailable() ? config.authorizationPrefix + window.localStorage.getItem('user-token') || '' : ''
                        },
                        success: function (data) {

                            $thisComponent.setState({
                                isLoggedIn: true,
                                data: data[0]
                            });
                        },
                        error: function (jqXHR, statusString, err) {
                            console.log(err);
                        }
                    });
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
            url: config.logoutUserUrl,
            headers: {
                'Authorization': localStorage.isAvailable() ? config.authorizationPrefix + window.localStorage.getItem('user-token') || '' : ''
            },
            success: function () {

                if (localStorage.isAvailable()) {
                    /*TODO improve FB logout*/
                    //FB.logout(function(response) {});
                    window.localStorage.clear();
                    $thisComponent.setState({isLoggedIn: false});
                }
            },
            error: function (jqXHR, statusString, err) {
                console.log(err);
            }
        });
    },
    /*TODO delete in the future*/
    openLoginForm() {

        this.setState({
            isModalOpen: true,
            isLoginForm: true,
            isCreateAccountForm: false
        });
    },
    /*TODO delete in the future*/
    openCreateAccountForm() {

        this.setState({
            isModalOpen: true,
            isLoginForm: false,
            isCreateAccountForm: true
        });
    },
    render() {

        var $loginElements;
        var $modalElement;

        if (this.state.isLoggedIn) {

            $loginElements = [
                <li className='main-nav-list-item main-user-name' key={2}>
                    <a href={config.myProfileHash}>{this.state.data.fullName}</a>
                </li>,
                <li className='main-nav-list-item main-user-logout' {...tapOrClick(this.logout)} key={3}>
                    <a href='#'>logout</a>
                </li>
            ];
        } else {
            $loginElements = [
                <li className='main-nav-list-item main-create-account' {...tapOrClick(this.openCreateAccountForm)} key={4}>
                    <a href='#create-account-form' className='is-active'>create free account</a>
                </li>,
                <li className='main-nav-list-item main-sign-in' {...tapOrClick(this.openLoginForm)} key={5}>
                    <a href='#sign-in-form'>sign in</a>
                </li>
            ]
        }

        if (this.state.isModalOpen) {
            $modalElement = <ModalComponent data={this}/>;
        }


        return (
            <ul className='main-nav-list sticky pos-top pos-left'>
                <li className='main-nav-list-item' key={1}>
                    <a href='#teachers'>teachers</a>
                </li>
                {$loginElements}
                <ReactCSSTransitionGroup transitionName="main-modal-transition" transitionEnterTimeout={300} transitionLeaveTimeout={300}>
                    {$modalElement}
                </ReactCSSTransitionGroup>
            </ul>
        );
    }
});

module.exports = NavigationList;