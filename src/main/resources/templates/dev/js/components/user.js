import config from '../ajax/config';
import React from 'react';

class User extends React.Component {

    render() {
        var userNodes = this.props.data.map(function (user, key) {

            var userSkills = user.skills.map(function (skill, key) {
                return (
                    <span className='user-skill color-white fw-700' key={key}>{skill}</span>
                );
            });

            return (
                <div className='col s12 m6' key={key}>
                    <a className='card-wrapper fw-100' href={config.usersHash + user.email}>
                        <div className='card-header-img bg-white'>
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
                            <div className='user-more-info-btn main-btn fw-700 color-purple bg-white'>more</div>
                        </div>
                    </a>
                </div>
            );

        });
        return (
            <div>
                {userNodes}
            </div>
        );
    }
}

module.exports = User;