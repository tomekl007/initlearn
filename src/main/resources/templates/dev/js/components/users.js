import React from 'react';
import $ from '../lib/jquery';

import config from '../ajax/config';

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

        /*TODO improve AJAX CALLS*/
        $.ajax({
            url: url,
            dataType: 'json',
            headers: config.apiCallHeader(),
            cache: false,
            success: function (data) {
                this.setState({data: data});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    componentWillMount() {
        this.loadUsersFromServer(this.props.url);
    },
    render() {
        return (
            <div>
            {this.state.data.map(function (data, key) {
                return <UserComponent data={data} email={data.email} key={key}/>
            })}
            </div>
        );
    }
});

module.exports = Users;