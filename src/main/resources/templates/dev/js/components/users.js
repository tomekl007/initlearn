import React from 'react';
import $ from '../lib/jquery';
import UserComponent from './user';

/*TODO improve Teachers class to ES6*/
var Users = React.createClass({

    getInitialState() {
        return {data: []};
    },
    loadTeachersFromServer() {

        /*TODO improve AJAX CALLS*/
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function(data) {
                this.setState({data: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    componentDidMount() {
        this.loadTeachersFromServer();
    },
    render() {
        return (
            <UserComponent data={this.state.data} />
        );
    }
});

module.exports = Users;