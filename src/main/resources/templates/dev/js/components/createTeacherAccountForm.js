import React from 'react';
import FormSerialize from 'form-serialize';

import config from '../ajax/config';

import Input from './input';

var CreateTeacherAccountForm = React.createClass({
    addData(event) {

        event.preventDefault();

        var $target = event.target;
        var $modalComponent = this.props.data.modalComponent;

        var data = JSON.stringify(FormSerialize($target, {hash: true}));

        /*TODO improve AJAX CALLS*/
        /*TODO code refactoring needed*/
        $.ajax({

            type: $target.getAttribute('method'),
            url: config.updateUserDataUrl($modalComponent.state.formData.email),
            data: data,
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
        console.log(this);
        return (
            <div className='main-form-wrapper'>
                <form method='post' role='form' className='main-form' onSubmit={this.addData}>
                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'hourRate', type: 'text'}}/>
                        <label className='main-label'>hour rate</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'screenHero', type: 'text'}}/>
                        <label className='main-label'>screenHero</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'linkedIn', type: 'text', required: 'required'}}/>
                        <label className='main-label'>linkedIn</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'skills[]', type: 'text'}}/>
                        <label className='main-label'>skills</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'links[]', type: 'text'}}/>
                        <label className='main-label'>links</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <textarea className='main-input' name='bio' type='text' />
                        <label className='main-label'>bio</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'img', type: 'text'}}/>
                        <label className='main-label'>img</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <button type='submit' className='main-btn btn-primary fw-700'>add</button>
                </form>
            </div>
        );
    }
});

module.exports = CreateTeacherAccountForm;