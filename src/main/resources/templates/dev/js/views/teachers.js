import config from '../ajax/config';
import React from 'react';
import UsersComponent from '../components/users';

class Teachers extends React.Component {
    render() {
        return (
            <section id='teachers' className='main-section-teachers'>
                <div className='row'>
                    <div className='col s12'>
                        <h2 className='section-header color-purple'>Our Teachers</h2>
                    </div>
                    <div>
                        <UsersComponent url={config.allTeachersUrl} />
                    </div>
                </div>
            </section>
        );
    }
}

module.exports = Teachers;