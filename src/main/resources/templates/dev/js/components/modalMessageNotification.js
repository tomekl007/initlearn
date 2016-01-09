import React from 'react';

/*TODO improve Teachers class to ES6*/
var ModalMessageNotification = React.createClass({

    render() {
        var typeClass = this.props.type || '';
        return (
            <div className={'main-modal-message-notification ' + typeClass}>
                <div className='main-modal-message-notification-icon success'>
                    <i className='fa fa-check-circle-o'>
                    </i>
                </div>
                <div className='main-modal-message-notification-icon cancel'>
                    <i className='fa fa-times-circle-o'>
                    </i>
                </div>
                <div className='main-modal-message-notification-content'>{this.props.message}</div>
            </div>
        );
    }
});

module.exports = ModalMessageNotification;