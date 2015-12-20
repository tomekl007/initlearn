import Config from '../ajax/config';
import React from 'react';

class User extends React.Component {
    render() {
        var teacherNodes = this.props.data.map(function (user) {
            var userSkills = user.skills.map(function(skill) {
                return (
                    <p>{skill}</p>
                );
            });
            return (
                <div className='col s12 m6'>
                    <div className='card-wrapper'>
                        <div className='card-header-img'>
                            <a href={Config.usersHash + user.email}>
                                <img src={user.img} alt='teacher profile image'/>
                            </a>
                        </div>
                        <div className='card-header'>
                            <p className='user-label-name'>Name:</p>
                            <p className='user-name'>{user.fullName}</p>
                            <p className='user-label-screenhero'>ScreenHero:</p>
                            <p className='user-screenhero'>{user.screenHero}</p>
                            <p className='user-label-rate'>Hour rate:</p>
                            <p className='user-hourRate'>{user.hourRate}</p>
                            <p className='user-label-skills'>Skills:</p>
                            <p className='user-skills'>{userSkills}</p>
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

module.exports = User;