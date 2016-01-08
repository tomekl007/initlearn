import React from 'react';
import tapOrClick from 'react-tap-or-click';


/*TODO improve Teachers class to ES6*/
var Tooltip = React.createClass({

    render() {
        return (
            <span className='main-tooltip'>
                {this.props.content}
            </span>
        );
    }
});

module.exports = Tooltip;