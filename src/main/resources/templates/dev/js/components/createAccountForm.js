import React from 'react';
import $ from '../lib/jquery';
import FormSerialize from 'form-serialize';

import config from '../ajax/config';
import localStorage from '../common/localStorage';

var CreateAccountForm = React.createClass({

    createAccount(event) {

        event.preventDefault();

        var $target = event.target;
        var $modalComponent = this.props.data.modalComponent;
        var $navigationComponent = this.props.data.navigationComponent;

        var data = JSON.stringify(FormSerialize($target, {hash: true}));

        /*TODO improve AJAX CALLS*/
        $.ajax({

            type: $target.getAttribute('method'),
            url: config.registerAccountUrl,
            data: data,
            headers: {
                'Content-Type': 'application/json'
            },

            success: function (data) {

                /*TODO refactor mapping*/
                var dataToMap = FormSerialize($target, {hash: true});
                Object.keys(dataToMap).map(function(key) {
                    if (key === 'email') {
                        dataToMap['username'] = dataToMap[key];
                    }
                });

                console.log('my serializtion');
                console.log(JSON.stringify(dataToMap));
                console.log('----------------');
                console.log('default serialization');
                console.log($($target).serialize());
                console.log('-----------------------')
                console.log(data);

                /*TODO improve - 2 times render call*/
                $modalComponent.setState({
                    /*TODO change serialize method to npm serialize*/
                    formData: JSON.stringify(dataToMap)
                });

                $navigationComponent.setState({
                    isCreateAccountForm: false,
                    isAutomaticLogin: true,
                    isLoginForm: true
                });
            },

            error: function (jqXHR, statusString, err) {
                console.log(err);
            }

        });
    },
    render() {
        return (
            <div className='main-form-wrapper'>
                <form id='create-account-form' method='post' role='form' className='main-form show' action='/registerAccount' onSubmit={this.createAccount}>

                    <div form-group='true' className='main-input-wrapper'>
                        <input className='main-input' name='givenName' type='text' autofocus='autofocus'/>
                        <label className='main-label'>name</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <input className='main-input' name='surname' type='text'/>
                        <label className='main-label'>surname</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <input className='main-input' name='email' type='email' required='required'/>
                        <label className='main-label'>mail</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <input className='main-input' name='password' type='password' required='required'/>
                        <input name='grant_type' type='hidden' value='password'/>
                        <label className='main-label'>password</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <button type='submit' className='main-btn btn-primary fw-700'>create account</button>

                </form>
            </div>
        );
    }
});

module.exports = CreateAccountForm;