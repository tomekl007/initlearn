import React from 'react';
import $ from '../lib/jquery';
import FormSerialize from 'form-serialize';

import config from '../ajax/config';
import localStorage from '../common/localStorage';

import Input from './input';

var CreateAccountForm = React.createClass({

    getInitialState() {
        return {
            teacherCheckbox: false
        }
    },
    createAccount(event) {

        event.preventDefault();

        var $target = event.target;
        var $modalComponent = this.props.data.modalComponent;
        var $navigationComponent = this.props.data.navigationComponent;

        var data = JSON.stringify(FormSerialize($target, {hash: true}));

        /*TODO improve AJAX CALLS*/
        /*TODO code refactoring needed*/
        $.ajax({

            type: $target.getAttribute('method'),
            url: config.registerAccountUrl,
            data: data,
            headers: {
                'Content-Type': 'application/json'
            },

            success: function (data) {
                console.log(data);

                /*TODO refactor mapping*/
                var dataToMap = FormSerialize($target, {hash: true});

                Object.keys(dataToMap).map(function (key) {
                    if (key === 'email') {
                        dataToMap['username'] = dataToMap[key];
                    }
                });

                /*TODO improve - 2 times render call*/
                $modalComponent.setState({formData: dataToMap});

                $navigationComponent.setState({
                    automaticLogin: true,
                    createAccountForm: false,
                    loginForm: true,
                    addUserDataForm: true
                });

                /*TODO improve add user to teacher group call*/
                $.ajax({

                    type: $target.getAttribute('method'),
                    url: config.addUserToTeacherGroupUrl(dataToMap.email),
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    success: function (data) {
                        console.log(data);
                        console.log('dodano teachera');
                    },
                    error: function (jqXHR, statusString, err) {
                        console.log(err);
                    }
                });
            },

            error: function (jqXHR, statusString, err) {
                console.log(err);
            }

        });
    },
    isTeacher() {

        this.setState({
            teacherCheckbox: !this.state.teacherCheckbox
        });
    },
    render() {
        return (
            <div className='main-form-wrapper'>
                <form id='create-account-form' method='post' role='form' className='main-form' action='/registerAccount' onSubmit={this.createAccount}>
                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'givenName', type: 'text', defaultValue: ' '}}/>
                        <label className='main-label'>name</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'surname', type: 'text', defaultValue: ' '}}/>
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