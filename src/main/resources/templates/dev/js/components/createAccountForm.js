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
        //var $navigationComponent = this.props.data.navigationComponent;

        /*TODO improve AJAX CALLS*/
        $.ajax({

            type: $target.getAttribute('method'),
            url: config.registerAccountUrl,
            data: JSON.stringify(FormSerialize($target, {hash: true})),
            headers: {
                'Content-Type': 'application/json'
            },

            success: function (data) {
                console.log(data);
                $modalComponent.close();
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