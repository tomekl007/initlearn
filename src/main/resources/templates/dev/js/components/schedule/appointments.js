import React from 'react';
import tapOrClick from 'react-tap-or-click';

import DateComponent from './date';
import ModalComponent from '../modal';
import ModalReservation from '../modalReservation';

var Appointments = React.createClass({

    getInitialState() {
        return {
            modalOpen: false
        };
    },
    remove() {
        this.setState({modalOpen: true});
    },
    render() {
        var $modalComponent;
        var $thisComponent = this;

        if (this.state.modalOpen) {
            $modalComponent = <ModalComponent
                parent={this}
                content={<ModalReservation
                    parent={this}

                />}/>
        }
        //{...tapOrClick($thisComponent.remove)}
        return (
            <div>
                {this.props.parent.state.appointments.map(function (appointment, key) {
                    return <div className='main-schedule-item' key={key} >
                        <DateComponent date={appointment.date} />
                        <div className='main-schedule-item-content'>
                            {appointment.data.teacher}
                        </div>
                        <div className='main-schedule-item-cancel'>
                            <i className='fa fa-times'></i>
                        </div>
                    </div>;
                })}

                {$modalComponent}
            </div>
        );
    }
});

module.exports = Appointments;