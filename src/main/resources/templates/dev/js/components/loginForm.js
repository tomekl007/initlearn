import React from 'react';
import $ from '../lib/jquery';

import localStorage from '../common/localStorage';

var LoginForm = React.createClass({


    componentDidMount() {
        /*TODO improve childNodes form*/
        var $target = this.getDOMNode().childNodes[0];
        this.getToken(null, $target);
    },
    getToken(event, target) {

        event !== null ? event.preventDefault() : true;

        var $target = event !== null ? event.target : target;
        var $modalComponent = this.props.data.modalComponent;
        var $navigationComponent = this.props.data.navigationComponent;

        /*TODO improve AJAX CALLS*/
        $.ajax({
            type: $target.getAttribute('method'),
            url: $target.getAttribute('action'),
            /*TODO change serialize to http://stackoverflow.com/questions/11661187/form-serialize-javascript-no-framework*/
            data: event !== null ? $($target).serialize() : $modalComponent.props.data.formData,

            success: function (data) {
                console.log(data);
                if (localStorage.isAvailable()) {
                    window.localStorage.setItem('user-token', data.access_token);
                    $navigationComponent.login();
                    $modalComponent.close();
                }
            },

            error: function (jqXHR, statusString, err) {
                console.log(err);
            }

        });
    },
    render() {
        return (
            <div className='main-form-wrapper'>
                <form id='sign-in-form' method='post' role='form' className='main-form show' action='oauth/token' onSubmit={this.getToken}>
                    <div form-group='true' className='main-input-wrapper'>
                        <input className='main-input' name='username' type='text' autofocus='autofocus' required='required'/>
                        <label className='main-label'>mail</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <input className='main-input' name='password' type='password' required='required'/>
                        <input name='grant_type' type='hidden' value='password'/>
                        <label className='main-label'>password</label>

                        <div className='main-input-bg'></div>
                    </div>
                    <div form-group='true' className='main-input-wrapper'>
                        <a href='/forgot' className='to-login' text='Forgot password'> Forgot Password </a>
                    </div>

                    <button type='submit' className='main-btn btn-primary fw-700'>login</button>

                </form>
            </div>
        );
    }
});

module.exports = LoginForm;