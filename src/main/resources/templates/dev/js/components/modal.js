import React from 'react';
import ReactDOM from 'react-dom';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import tapOrClick from 'react-tap-or-click';

var Modal = React.createClass({

    getInitialState() {
        return {
            formData: {}
        };
    },
    /*TODO improve - 2 times render call*/
    close(callback) {

        if (typeof(callback) === typeof(Function)) {
            callback();
            return true;
        }
        this.props.parent.setState({modalOpen: false});

    },
    render() {
        /*TODO react css transition group is not working */
        return (
            <div className='main-modal-window sticky pos-top pos-left'>
                <div className='main-modal-window-content'>
                <ReactCSSTransitionGroup transitionName='main-form-transition-step' transitionEnterTimeout={500} transitionLeave={false}>
                    {this.props.content}
                </ReactCSSTransitionGroup>
                </div>
                <div className='main-modal-window-close sticky pos-top pos-left' {...tapOrClick(this.close)}>
                </div>
            </div>
        );
    }
});

module.exports = Modal;