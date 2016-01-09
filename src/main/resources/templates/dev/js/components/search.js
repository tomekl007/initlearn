import React from 'react';
import ReactTypeahead from 'react-typeahead';
import $ from '../lib/jquery';

import config from '../ajax/config';

/*TODO improve Teachers class to ES6*/
var Search = React.createClass({

    getInitialState() {
        return {
            options: []
        };
    },
    componentWillMount() {
        this.loadOptions();
    },
    loadOptions() {
        /*TODO improve AJAX CALLS*/
        $.ajax({
            type: 'get',
            url: 'https://initlearn.herokuapp.com/skills',
            success: function (data) {
                this.setState({
                    options: data
                });
            }.bind(this),
            error: function (jqXHR, statusString, err) {
                console.log(err);
            }
        });
    },
    render() {
        return (
            <ReactTypeahead.Typeahead
                options={this.state.options}
                placeholder={'search for skills'}
                customClasses={
                {
                    input: 'main-search-input',
                    results: 'main-search-list',
                    listItem: 'main-search-list-item',
                    listAnchor: 'main-search-list-item-anchor',
                    hover: 'main-search-list-item-hover'
                }
                    }
                onOptionSelected={function (skill) {
                    console.log(skill);
                    /*TODO change to react route*/
                    //Router.Navigation.transitionTo('router', config.searchTeachersBySkillPath + skill);
                    window.document.location.hash = config.searchTeachersBySkillPath + skill;
                }}

            />
        );
    }
});

module.exports = Search;