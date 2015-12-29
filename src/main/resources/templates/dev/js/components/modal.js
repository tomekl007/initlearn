import React from 'react';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import tapOrClick from 'react-tap-or-click';

import LoginFormComponent from './loginForm';
import CreateAccountForm from './createAccountForm';
import CreateTeacherAccountForm from './createTeacherAccountForm';

var Modal = React.createClass({
    getInitialState() {

        return {
            open: true,
            formData: {}
        };
    },
    /*TODO improve - 2 times render call*/
    close() {
        //this.setState({open: false});
        this.props.data.setState({modalOpen: false});

    },
    render() {

        var $loginForm;
        var $createAccountForm;
        var $createTeacherAccountForm;

        if (this.props.data.state.loginForm) {
            $loginForm = <LoginFormComponent data={
            {
                navigationComponent: this.props.data,
                modalComponent: this
            }
                }/>;
        } else if (this.props.data.state.createTeacherAccountForm) {
            $createTeacherAccountForm = <CreateTeacherAccountForm data={
            {
                navigationComponent: this.props.data,
                modalComponent: this
            }
                }/>;
        } else if (this.props.data.state.createAccountForm) {
            $createAccountForm = <CreateAccountForm data={
            {
                navigationComponent: this.props.data,
                modalComponent: this
            }
                }/>;
        }

        return (
            <div className='main-modal-window sticky pos-top pos-left'>
                <div className='main-modal-window-content'>
                {$loginForm}
                <ReactCSSTransitionGroup transitionName='main-form-transition-step' transitionEnterTimeout={500} transitionLeave={false}>
                    {$createAccountForm}
                </ReactCSSTransitionGroup>
                <ReactCSSTransitionGroup transitionName='main-form-transition-step' transitionEnterTimeout={500} transitionLeaveTimeout={500}>
                    {$createTeacherAccountForm}
                </ReactCSSTransitionGroup>
                </div>
                <div className='main-modal-window-close sticky pos-top pos-left' {...tapOrClick(this.close)}>
                </div>
            </div>
        );
    }
});

module.exports = Modal;