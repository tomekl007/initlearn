import React from 'react';
import ReactTypeahead from 'react-typeahead';
import $ from '../lib/jquery';

import config from '../ajax/config';
import api from '../ajax/api';

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
        var $thisComponent = this;
        api.getSearchAutocompleteOptions()
            .then(function (data) {
                $thisComponent.setState({options: data});
            })
            ['catch'](function (jqXHR) {
            console.log(jqXHR);
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
                    window.document.location.hash = config.searchTeachersBySkillPath + skill;
                }}

            />
        );
    }
});

module.exports = Search;