import Config from '../ajax/config';
import React from 'react';
import TeachersComponent from '../components/teachers';

class Teachers extends React.Component {
    render() {
        return (
            <section id='teachers' className='main-section'>
                <div className='row'>
                    <div className='col s12'>
                        <h2 className='section-header color-blue'>Our Teachers</h2>
                    </div>
                    <div>
                        <TeachersComponent url={Config.allTeachersUrl} />
                    </div>
                </div>
            </section>
        );
    }
}

module.exports = Teachers;