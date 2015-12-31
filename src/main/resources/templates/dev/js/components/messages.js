import React from 'react';
import $ from '../lib/jquery';

/*TODO improve Teachers class to ES6*/
var Messages = React.createClass({

    getInitialState() {
        return {data: []};
    },
    loadMessages() {

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
        this.loadMessages();
    },
    render() {
        return (
            <div>message compoenent</div>
        );
    }
});

module.exports = Messages;