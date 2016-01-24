import React from 'react';

import api from '../ajax/api';

import UserComponent from './user';

/*TODO improve Teachers class to ES6*/
var Users = React.createClass({

    componentWillReceiveProps(updatedProps) {
        this.loadUsersFromServer(updatedProps.url);
    },
    getInitialState() {
        return {
            data: []
        };
    },
    loadUsersFromServer(url) {
        var $thisComponent = this;
        api.getUsers(url || this.props.url)
            .then(function(data) {
                $thisComponent.setState({ data: data, url: url});
            });
    },
    componentWillMount() {
        this.loadUsersFromServer(this.props.url);
    },
    render() {
        var $thisComponent = this;
        return (
            <div>
            {this.state.data.map(function (data, key) {
                return <UserComponent parent={$thisComponent} data={data} email={data.email} key={key}/>
            })}
            </div>
        );
    }
});

module.exports = Users;