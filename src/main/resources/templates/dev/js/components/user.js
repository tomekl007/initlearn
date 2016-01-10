import React from 'react';
import tapOrClick from 'react-tap-or-click';

import config from '../ajax/config';

var User = React.createClass({

    rate(event) {
        /*TODO improve AJAX CALLS*/

        var rate = {
            rate: parseInt(event.currentTarget.getAttribute('data-rate'))
        };

        $.ajax({
            type: 'post',
            url: config.userRatingUrl(this.props.email),
            data: JSON.stringify(rate),
            headers: config.apiCallHeader(),
            success: function (data) {
                console.log(data);
            },

            error: function (jqXHR) {
                console.log(jqXHR);
            }
        });
    },
    render() {

        var userRateInfo;

        var userSkills = this.props.data.skills.map(function (skill, key) {
            return (
                <span className='main-user-profile-skill color-white fw-700' key={key}>{skill}</span>
            );
        });

        var userLinks = this.props.data.links.map(function (link, key) {
            return (
                <span className='main-user-profile-link' key={key}>{link}</span>
            );
        });

        if (this.props.data.average !== null) {
            userRateInfo =
                [<p className='main-user-profile-label-average-rate' key={1} >Average rate:</p>,
                <p className='main-user-profile-average-rate'key={2} >
                    <i className='fa fa-star color-gold'></i>
                    {this.props.data.average}
                </p>,
                <p className='main-user-profile-label-number-of-rates'key={3} >Number of rates:</p>,
                <p className='main-user-profile-number-of-rates'key={4} >{this.props.data.numberOfRates}</p>];
        }
        
        return (
            <div className='col s12 m6'>
                <div className='card-wrapper fw-100'>
                    <a className='main-user-profile-nav-profile' href={config.usersHash + this.props.email}></a>
                    <a className='card-header-img bg-white' href={config.usersHash + this.props.email}>
                        <img src={this.props.data.img} alt='teacher profile image'/>
                    </a>
                    <div className='card-header'>
                        <h3 className='main-user-profile-name color-white'>{this.props.data.fullName}</h3>
                    </div>
                    <div className='card-content'>
                        <h3 className='main-user-profile-name color-purple'>{this.props.data.fullName}</h3>
                        <p className='main-user-profile-label-screenhero'>ScreenHero:</p>
                        <p className='main-user-profile-screenhero'>{this.props.data.screenHero}</p>
                        <p className='main-user-profile-label-rate'>Hour rate:</p>
                        <p className='main-user-profile-hourRate'>{this.props.data.hourRate}</p>
                        <p className='main-user-profile-label-skills'>Skills:</p>
                        <div className='main-user-profile-skill-list'>
                        {userSkills}
                        </div>
                        <p className='main-user-profile-label-links'>Links:</p>
                        <div className='main-user-profile-link-list'>
                        {userLinks}
                        </div>
                        <p className='main-user-profile-label-bio'>Bio:</p>
                        <p className='main-user-profile-bio'>{this.props.data.bio}</p>
                    {userRateInfo}
                        <div className='main-user-profile-rating'>
                            <span className='main-user-profile-rating-star-wrapper' data-rate='5' {...tapOrClick(this.rate)}>
                                <i className='fa fa-star-o'></i>
                                <i className='fa fa-star full-star color-gold'></i>
                            </span>
                            <span className='main-user-profile-rating-star-wrapper ' data-rate='4' {...tapOrClick(this.rate)}>
                                <i className='fa fa-star-o'></i>
                                <i className='fa fa-star full-star color-gold'></i>
                            </span>
                            <span className='main-user-profile-rating-star-wrapper' data-rate='3' {...tapOrClick(this.rate)}>
                                <i className='fa fa-star-o'></i>
                                <i className='fa fa-star full-star color-gold'></i>
                            </span>
                            <span className='main-user-profile-rating-star-wrapper' data-rate='2' {...tapOrClick(this.rate)}>
                                <i className='fa fa-star-o'></i>
                                <i className='fa fa-star full-star color-gold'></i>
                            </span>
                            <span className='main-user-profile-rating-star-wrapper' data-rate='1' {...tapOrClick(this.rate)}>
                                <i className='fa fa-star-o'></i>
                                <i className='fa fa-star full-star color-gold'></i>
                            </span>
                        </div>

                        <div className='main-user-profile-more-info-btn main-btn fw-700 color-purple bg-white'>more</div>
                    </div>
                    <a className='main-user-profile-nav-messages' href={config.messagesHash + this.props.email}>
                        <i className='fa fa-envelope'></i>
                    </a>
                    <a href={config.paymentPath + this.props.email} className='main-user-profile-payment-btn' data-paypal-button='true'>
                        <img src='//www.paypalobjects.com/en_US/i/btn/btn_paynow_LG.gif' alt='Pay Now' />
                    </a>
                </div>
            </div>
        );
    }
});

module.exports = User;