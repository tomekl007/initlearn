import React from 'react';

import AboutUsComponent from '../components/aboutUs';
import ModalComponent from '../components/modal';
import ModalMessageNotificationComponent from '../components/modalMessageNotification';


var SuccessPayment = React.createClass({
    getInitialState() {
        return {modalOpen: true};
    },
    render() {

        var $modalComponent;
        if (this.state.modalOpen) {
            $modalComponent = <ModalComponent
                parent={this}
                content={<ModalMessageNotificationComponent
                    type={'success'}
                    message={'Successful Payment'}
                />}/>
        }

        return (
            <div>
                <AboutUsComponent />
                {$modalComponent}
            </div>
        );
    }
});

module.exports = SuccessPayment;