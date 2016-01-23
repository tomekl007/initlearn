import React from 'react';
import tapOrClick from 'react-tap-or-click';

import DateComponent from './date';
import ModalComponent from '../modal';
import ModalReservation from '../modalReservation';

var Reservations = React.createClass({

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

        return (
            <div>
                {this.props.parent.state.reservations.map(function (reservation, key) {
                    return <div className='main-schedule-item' key={key} >
                        <DateComponent date={reservation.date} />
                        <div className='main-schedule-item-content'>
                            {reservation.data.reserved_by}
                        </div>
                        <div className='main-schedule-item-cancel' {...tapOrClick($thisComponent.remove)}>
                            <i className='fa fa-times'></i>
                        </div>
                    </div>;
                })}

                {$modalComponent}
            </div>
        );
    }
});

module.exports = Reservations;