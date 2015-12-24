import Config from '../ajax/config';
import React from 'react';
import Router from 'react-router';
import UsersComponent from '../components/users';

class MyProfile extends React.Component {
    render() {
        return (
            <section id='profile' className='main-section-user-profile'>
                <UsersComponent url={Config.loggedUserUrl} />
            </section>
        );
    }
}

module.exports = MyProfile;