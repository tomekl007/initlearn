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

            var userLinks = user.links.map(function (link, key) {
                return (
                    <span className='user-link' key={key}>{link}</span>
                );
            });

            var userRateInfo = [];

            if (user.average !== null) {
                userRateInfo = [
                    <p className='user-label-average-rate'  key={1}>Average rate:</p>,
                    <p className='user-average-rate' key={2}>{user.average}</p>,
                    <p className='user-label-number-of-rates' key={3}>Number of rates:</p>,
                    <p className='user-number-of-rates' key={4}>{user.numberOfRates}</p>
                ];
            }

            return (
                <div className='col s12 m6' key={key}>
                    <div className='card-wrapper fw-100'>
                        <a className='user-nav-profile' href={config.usersHash + user.email}></a>
                        <a className='card-header-img bg-white' href={config.usersHash + user.email}>
                            <img src={user.img} alt='teacher profile image'/>
                        </a>
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
                            <p className='user-label-links'>Links:</p>
                            <div className='userlink-list'>
                                {userLinks}
                            </div>
                            <p className='user-label-bio'>Bio:</p>
                            <p className='user-bio'>{user.bio}</p>
                            {userRateInfo}

                            <div className='user-more-info-btn main-btn fw-700 color-purple bg-white'>more</div>
                        </div>
                        <a className='user-nav-messages' href={config.messagesHash + user.email}>
                            <i className='fa fa-envelope'></i>
                        </a>
                        <a href={config.paymentPath + user.email} data-paypal-button='true'>
                            <img src='//www.paypalobjects.com/en_US/i/btn/btn_paynow_LG.gif' alt='Pay Now' />
                        </a>
                    </div>
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