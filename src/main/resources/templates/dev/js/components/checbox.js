import React from 'react';
import ReactDOM from 'react-dom';
import tapOrClick from 'react-tap-or-click';

var Checkbox = React.createClass({

        getInitialState() {
            return {
                checked: false
            };
        },
        toggleItem() {
            var checked = !this.state.checked;

            this.setState({checked: checked});
            this.props.parent.setState({checkboxChecked: checked});
        },
        render() {
            return (
                <span className={'main-checkbox checkbox-blue ' + (this.state.checked ? 'checked' : '')} {...tapOrClick(this.toggleItem)}>
                    <i className='fa fa-check'></i>
                </span>
            );
        }
    })
    ;

module.exports = Checkbox;
