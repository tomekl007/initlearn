import React from 'react';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import FormSerialize from 'form-serialize';

import api from '../ajax/api';

import Input from './input';

var CreateAccountForm = React.createClass({

    getInitialState() {
        return {
            errorMessage: false,
            errorMessageText: '',
            teacherCheckbox: false
        }
    },
    createAccount(event) {
        event.preventDefault();

        var $target = event.target;
        var $thisComponent = this;
        var $navigationComponent = this.props.navigation;
        var $modalComponent = $navigationComponent.refs.modal;

        var data = FormSerialize($target, {hash: true, empty: true});
        console.log(data);

        /*TODO code refactoring needed*/

        api.registerAccount(data)
            .then(function (data) {
                console.log(data);

                /*TODO refactor mapping*/
                var dataToMap = FormSerialize($target, {hash: true, empty: true});

                Object.keys(dataToMap).map(function (key) {
                    if (key === 'email') {
                        dataToMap['username'] = dataToMap[key];
                    }
                });

                /*TODO improve - 2 times render call*/
                $modalComponent.setState({formData: dataToMap, teacherCheckbox: $thisComponent.state.teacherCheckbox});
                $navigationComponent.setState({automaticLogin: true, addUserDataForm: true});
                $navigationComponent.openLoginForm();
            })
            ['catch'](function (jqXHR) {
            console.log(jqXHR);
            var errorMessageText = '';

            if (jqXHR.status === 406) {
                errorMessageText = 'Account email address is in an invalid format';
            } else if (jqXHR.status === 409) {
                errorMessageText = 'Account with that email already exists';
            } else if (jqXHR.status === 412) {
                errorMessageText = 'Password should have at least 6 characters';
            }

            $thisComponent.setState({errorMessage: true, errorMessageText: errorMessageText});
        });
    },
    isTeacher() {

        this.setState({
            teacherCheckbox: !this.state.teacherCheckbox
        });
    },
    render() {
        var $errorMessage = [];

        if (this.state.errorMessage) {
            $errorMessage = <p className='main-form-message main-form-message-error' key={1}>{this.state.errorMessageText}</p>
        }
        return (
            <div className='main-form-wrapper'>
                <ReactCSSTransitionGroup transitionName='main-form-message-transition' transitionEnterTimeout={300} transitionLeaveTimeout={300}>
                    {$errorMessage}
                </ReactCSSTransitionGroup>
                <form id='create-account-form' method='post' role='form' className='main-form' action='/registerAccount' onSubmit={this.createAccount}>
                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'givenName', type: 'text', required: 'required'}}/>
                        <label className='main-label'>name</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'surname', type: 'text', required: 'required'}}/>
                        <label className='main-label'>surname</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'email', type: 'email', required: 'required'}}/>
                        <label className='main-label'>mail</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'password', type: 'password', required: 'required'}}/>
                        <input name='grant_type' type='hidden' value='password'/>
                        <label className='main-label'>password</label>

                        <div className='main-input-bg'></div>
                    </div>
                    <div form-group='true' className='main-input-wrapper'>
                        <input type='checkbox' onChange={this.isTeacher}/>
                        <label className='main-label main-label-checkbox'>Teacher</label>
                    </div>

                    <button type='submit' className='main-btn btn-primary fw-700'>create account</button>
                </form>
            </div>
        );
    }
});

module.exports = CreateAccountForm;