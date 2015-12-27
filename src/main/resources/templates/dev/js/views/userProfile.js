import config from '../ajax/config';
import React from 'react';
import Router from 'react-router';
import UsersComponent from '../components/users';

class UserProfile extends React.Component {
    render() {
        return (
            <section id='profile' className='main-section-user-profile'>
                <UsersComponent url={config.appUrl + Router.HashLocation.getCurrentPath()} />
            </section>
        );
    }
}

module.exports = UserProfile;