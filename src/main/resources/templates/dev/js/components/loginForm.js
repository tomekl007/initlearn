import React from 'react';
import ReactDOM from 'react-dom';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import $ from '../lib/jquery';

import localStorage from '../common/localStorage';

import Input from './input';

var LoginForm = React.createClass({

    getInitialState() {
        return {
            errorMessage: false
        }
    },
    componentDidMount() {

        var $navigationComponent = this.props.data.navigationComponent;

        if ($navigationComponent.state.automaticLogin) {
            /*TODO improve childNodes form*/
            var $target = ReactDOM.findDOMNode(this.refs.mainForm);
            console.log($target);
            this.login(null, $target);
        }
    },
    login(event, target) {

        event !== null ? event.preventDefault() : true;

        var $target = event !== null ? event.target : target;
        var $thisComponent = this;
        var $modalComponent = this.props.data.modalComponent;
        var $navigationComponent = this.props.data.navigationComponent;

        /*TODO improve AJAX CALLS*/
        /*TODO code refactoring needed*/
        $.ajax({
            type: $target.getAttribute('method'),
            url: $target.getAttribute('action'),
            /*TODO change serialize to http://stackoverflow.com/questions/11661187/form-serialize-javascript-no-framework*/
            /*TODO change serialize method to npm serialize*/
            data: event !== null ? $($target).serialize() : $.param($modalComponent.state.formData),

            success: function (data) {
                console.log(data);
                if (localStorage.isAvailable()) {
                    window.localStorage.setItem('user-token', data.access_token);
                    /*TODO improve - 3 times render call*/
                    $navigationComponent.automaticLogin();

                    if ($navigationComponent.state.addUserDataForm) {
                        $navigationComponent.openUserDataForm();
                    } else {
                        $modalComponent.close();
                    }
                }
            },

            error: function (jqXHR) {
                if (jqXHR.status === 400) {
                    $thisComponent.setState({errorMessage: true});
                }
            }

        });
    },
    render() {

        var $errorMessage = [];

        if (this.state.errorMessage) {
            $errorMessage = <p className='main-form-message main-form-message-error' key={1}>wrong email or password</p>
        }

        return (
            <div className='main-form-wrapper'>
                <ReactCSSTransitionGroup transitionName='main-form-message-transition' transitionEnterTimeout={300} transitionLeaveTimeout={300}>
                    {$errorMessage}
                </ReactCSSTransitionGroup>
                <form id='sign-in-form' ref='mainForm' method='post' role='form' className='main-form' action='oauth/token' onSubmit={this.login}>
                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'username', type: 'text', required: 'required', autofocus: 'autofocus'}}/>
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
                        <a href='/forgot' className='to-login' text='Forgot password'> Forgot Password </a>
                    </div>

                    <button type='submit' className='main-btn btn-primary fw-700'>login</button>

                </form>
            </div>
        );
    }
});

module.exports = LoginForm;