import React from 'react';
import FormSerialize from 'form-serialize';

import config from '../ajax/config';

import Input from './input';

var AddUserDataForm = React.createClass({
    componentDidMount() {

        var $modalComponent = this.props.navigation.refs.modal;

        if ($modalComponent.state.teacherCheckbox) {
            /*TODO AJAX Improvement */
            $.ajax({
                type: 'post',
                url: config.addUserToTeacherGroupUrl,
                headers: config.apiCallHeader(),
                success: function (data) {
                    console.log(data);
                },
                error: function (jqXHR, statusString, err) {
                    console.log(err);
                }
            });
        }
    },
    addData(event) {

        event.preventDefault();

        var $target = event.target;
        var $navigationComponent = this.props.navigation;
        var $modalComponent = $navigationComponent.refs.modal;
        /*TODO code refactoring*/
        var serializedData = FormSerialize($target, {hash: true, empty: true});

        Object.keys(serializedData).map(function (key) {
            var value = serializedData[key];

            if (Array.isArray(value)) {
                serializedData[key] = serializedData[key][0].split(',');
            }
        });

        serializedData = JSON.stringify(serializedData);

        var url = $modalComponent.state.teacherCheckbox ? config.updateUserDataUrl : config.updateScreenheroUrl;

        /*TODO improve AJAX CALLS*/
        /*TODO code refactoring needed*/
        $.ajax({

            type: $target.getAttribute('method'),
            url: url,
            data: serializedData,
            headers: config.apiCallHeader(),
            success: function (data) {
                console.log(data);

                $modalComponent.close($navigationComponent.resetFormStates);
            },
            error: function (jqXHR, statusString, err) {
                console.log(err);
            }

        });
    },
    render() {
        var $userForm;
        var $modalComponent = this.props.navigation.refs.modal;

        if ($modalComponent.state.teacherCheckbox) {
            $userForm =
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
                        <Input data={{name: 'linkedIn', type: 'text'}}/>
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
                        <textarea className='main-input' name='bio' type='text'/>
                        <label className='main-label'>bio</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'img', type: 'text'}}/>
                        <label className='main-label'>img</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <button type='submit' className='main-btn btn-primary fw-700'>add</button>
                </form>;
        } else {
            $userForm =
                <form method='post' role='form' className='main-form' onSubmit={this.addData}>
                    <div form-group='true' className='main-input-wrapper'>
                        <Input data={{name: 'screenHero', type: 'text'}}/>
                        <label className='main-label'>screenHero</label>

                        <div className='main-input-bg'></div>
                    </div>

                    <button type='submit' className='main-btn btn-primary fw-700'>add</button>
                </form>;
        }


        return (
            <div className='main-form-wrapper'>
                {$userForm}
            </div>
        );
    }
});

module.exports = AddUserDataForm;