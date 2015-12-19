import React from 'react';

class Teacher extends React.Component {
    render() {
        var teacherNodes = this.props.data.map(function (teacher) {
            return (
                <div className='col s12 m6'>
                    <div className='card-wrapper'>
                        <div className='card-header-img'>
                            <a href='https://pl.linkedin.com/in/tomaszlelek'>
                                <img src='assets/teachers/teacher_2.jpg' alt='teacher profile image'/>
                            </a>
                        </div>
                        <div className='card-header'>
                            <p>{teacher.givenName}</p>
                        </div>
                    </div>
                </div>
            );

        });
        return (
            <div>
                {teacherNodes}
            </div>
        );
    }
}

module.exports = Teacher;