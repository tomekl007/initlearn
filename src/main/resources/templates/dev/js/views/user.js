import Config from '../ajax/config';
import React from 'react';
import Router from 'react-router';
import UsersComponent from '../components/users';

class User extends React.Component {
    render() {
        return (
            <div>
                <UsersComponent url={Config.appUrl + Router.HashLocation.getCurrentPath()} />
            </div>
        );
    }
}

module.exports = User;