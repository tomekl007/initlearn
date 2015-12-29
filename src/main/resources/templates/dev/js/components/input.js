import React from 'react';

var Input = React.createClass({
    componentDidMount() {
        this.isInputFilled();
    },
    isInputFilled() {

        var $input = this.getDOMNode();
        var textLength = $input.value.trim().length;

        if (textLength > 0) {
            $input.classList.add('is-filled');
            return;
        }

        $input.classList.remove('is-filled');
    },
    render() {
        return (
            <input className='main-input' name={this.props.data.name} type={this.props.data.type}
                defaultValue={this.props.data.defaultValue}required={this.props.data.required}
                autofocus={this.props.data.autofocus} onBlur={this.isInputFilled} />
        );
    }
})
;

module.exports = Input;
