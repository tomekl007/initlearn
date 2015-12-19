import React from 'react';
import $ from '../lib/jquery';
import TeacherComponent from './teacher';

/*improve Teachers class to ES6*/
var Teachers = React.createClass({

    getInitialState() {
        return {data: []};
    },
    loadTeachersFromServer() {
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
            <TeacherComponent data={this.state.data} />
        );
    }
});

module.exports = Teachers;