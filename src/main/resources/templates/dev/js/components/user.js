import Config from '../ajax/config';
import React from 'react';

class User extends React.Component {

    render() {
        var teacherNodes = this.props.data.map(function (user, key) {

            var userSkills = user.skills.map(function (skill, key) {
                return (
                    <span className='user-skill color-white fw-700' key={key}>{skill}</span>
                );
            });

            return (
                <div className='col s12 m6' key={key}>
                    <a className='card-wrapper fw-100' href={Config.usersHash + user.email}>
                        <div className='card-header-img'>
                            <img src={user.img} alt='teacher profile image'/>
                        </div>
                        <div className='card-header'>
                            <h3 className='user-name color-white'>{user.fullName}</h3>
                        </div>
                        <div className='card-content'>
                            <h3 className='user-name color-purple'>{user.fullName}</h3>
                            <p className='user-label-screenhero'>ScreenHero:</p>
                            <p className='user-screenhero'>{user.screenHero}</p>
                            <p className='user-label-rate'>Hour rate:</p>
                            <p className='user-hourRate'>{user.hourRate}</p>
                            <p className='user-label-skills'>Skills:</p>
                            <div className='user-skill-list'>
                                {userSkills}
                            </div>
                        </div>
                    </a>
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