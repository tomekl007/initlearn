import React from 'react';

import LoginFormComponent from './loginForm';
import CreateAccountForm from './createAccountForm';

var Modal = React.createClass({

    getInitialState() {

        return {
            isOpen: true
        };
    },
    close() {
        this.setState({isOpen: false});
        this.props.data.setState({isModalOpen: false});

    },
    render() {

        var $loginForm;
        var $createAccountForm;

        if (this.props.data.state.isLoginForm) {
            $loginForm = <LoginFormComponent data={
                    {
                        navigationComponent: this.props.data,
                        modalComponent: this
                    }
                }/>;
        } else if (this.props.data.state.isCreateAccountForm) {
            $createAccountForm = <CreateAccountForm data={
                    {
                        navigationComponent: this.props.data,
                        modalComponent: this
                    }
                }/>;
        }

        return (
            <div className='main-modal-window sticky pos-top pos-left is-active'>
                <div className='main-modal-window-content'>
                {$loginForm}
                {$createAccountForm}
                </div>
                <div className='main-modal-window-close' onClick={this.close}>
                </div>
            </div>
        );
    }
});

module.exports = Modal;