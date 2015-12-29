import React from 'react';

class Messages extends React.Component {
    render() {
        return (
            <section id='teachers' className='main-section-messages'>
                <div className='row'>
                    <div className='col s12'>
                        <h2 className='section-header color-purple'>Messages</h2>
                        <div className="main-messages-container">
                            <div className="row">
                                <div className='col s12 m3'>
                                    <div className='main-messages-user-name'>
                                        Name Surname
                                    </div>
                                </div>
                                <div className='main-messages-user-messages col s12 m9'>
                                    <div className='main-messages-user-messages'>
                                        Message
                                    </div>
                                    <div className='main-messages-text-input'>
                                        <textarea type='text' ></textarea>
                                    </div>
                                </div>
                            </div>

                            <div className="row">
                                <div className='col s12 m3'>
                                    <div className='main-messages-user-name'>
                                        Name Surname
                                    </div>
                                </div>
                                <div className='main-messages-user-messages col s12 m9'>
                                    <div className='main-messages-user-messages'>
                                        Message
                                    </div>
                                </div>
                            </div>

                            <div className="row">
                                <div className='col s12 m3'>
                                    <div className='main-messages-user-name'>
                                        Name Surname
                                    </div>
                                </div>
                                <div className='main-messages-user-messages col s12 m9'>
                                    <div className='main-messages-user-messages'>
                                        Message
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
            </section>
        );
    }
}

module.exports = Messages;