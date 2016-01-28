import React from 'react';
import tapOrClick from 'react-tap-or-click';

import ModalComponent from '../components/modal';
import ModalMessageNotificationComponent from '../components/modalMessageNotification';

import config from '../ajax/config';
import userData from '../ajax/userData';
import api from '../ajax/api';

var User = React.createClass({

    componentWillReceiveProps(state) {
        this.setState({data: state.data});
    },
    getInitialState() {
        return {
            data: this.props.data,
            modalOpen: false,
            rateFeedbackType: '',
            rateFeedbackMessage: ''
        };
    },
    rate(event) {
        var $rateStar = event.currentTarget;
        var $rateWrapper = $rateStar.parentNode;

        var rate = {
            rate: parseInt($rateStar.getAttribute('data-rate'))
        };

        api.addUserRating(this.props.email, rate)
            .then(this.successRate)
            ['catch'](this.failureRate);

        $rateWrapper.classList.add('disable');
    },
    successRate(data) {
        console.log(data);
        this.setState({
            data: data[0],
            modalOpen: true,
            rateFeedbackType: 'success',
            rateFeedbackMessage: 'Thanks for your vote'
        });
    },
    failureRate(jqXHR) {
        console.log(jqXHR);
        var message = 'something went wrong';
        if (jqXHR.status === 412) {
            message = 'You already rate that teacher';
        }
        this.setState({modalOpen: true, rateFeedbackType: 'cancel', rateFeedbackMessage: message});
    },
    render() {

        var $userRateInfo;
        var $modalComponent;

        var $userSkills = this.state.data.skills.map(function (skill, key) {
            return (
                <span className='main-user-profile-skill color-white fw-700' key={key}>{skill}</span>
            );
        });

        var $userLinks = this.state.data.links.map(function (link, key) {
            return (
                <span className='main-user-profile-link' key={key}>{link}</span>
            );
        });

        var $userRatingStars = userData.get() ? <div className='main-user-profile-rating-wrapper'>
            <span className='main-user-profile-rating-star' data-rate='5' {...tapOrClick(this.rate)}>
                <i className='fa fa-star-o'></i>
                <i className='fa fa-star full-star color-gold'></i>
            </span>
            <span className='main-user-profile-rating-star ' data-rate='4' {...tapOrClick(this.rate)}>
                <i className='fa fa-star-o'></i>
                <i className='fa fa-star full-star color-gold'></i>
            </span>
            <span className='main-user-profile-rating-star' data-rate='3' {...tapOrClick(this.rate)}>
                <i className='fa fa-star-o'></i>
                <i className='fa fa-star full-star color-gold'></i>
            </span>
            <span className='main-user-profile-rating-star' data-rate='2' {...tapOrClick(this.rate)}>
                <i className='fa fa-star-o'></i>
                <i className='fa fa-star full-star color-gold'></i>
            </span>
            <span className='main-user-profile-rating-star' data-rate='1' {...tapOrClick(this.rate)}>
                <i className='fa fa-star-o'></i>
                <i className='fa fa-star full-star color-gold'></i>
            </span>
        </div> : [];

        if (this.state.data.average !== null) {
            $userRateInfo =
                [<p className='main-user-profile-label-average-rate' key={1} >Average rate:</p>,
                    <p className='main-user-profile-average-rate'key={2} >
                        <i className='fa fa-star color-gold'></i>
                    {this.state.data.average}
                    </p>,
                    <p className='main-user-profile-label-number-of-rates'key={3} >Number of rates:</p>,
                    <p className='main-user-profile-number-of-rates'key={4} >{this.state.data.numberOfRates}</p>];
        }

        if (this.state.modalOpen) {
            $modalComponent = <ModalComponent
                parent={this}
                content={<ModalMessageNotificationComponent
                    type={this.state.rateFeedbackType}
                    message={this.state.rateFeedbackMessage}
                />}/>
        }

        /*TODO add current year to calendar*/
        return (
            <div className='col s12 m6'>
                <div className='card-wrapper fw-100'>
                {$modalComponent}
                    <a className='main-user-profile-nav-profile' href={config.usersHash + this.props.email}></a>
                    <a className='card-header-img bg-white' href={config.usersHash + this.props.email}>
                        <img src={this.state.data.img} alt='teacher profile image'/>
                    </a>
                    <div className='card-header'>
                        <h3 className='main-user-profile-name color-white'>{this.state.data.fullName}</h3>
                    </div>
                    <div className='card-content'>
                        <h3 className='main-user-profile-name color-purple'>{this.state.data.fullName}</h3>
                        <p className='main-user-profile-label-screenhero'>ScreenHero:</p>
                        <p className='main-user-profile-screenhero'>{this.state.data.screenHero}</p>
                        <p className='main-user-profile-label-rate'>Hour rate:</p>
                        <p className='main-user-profile-hourRate'>{this.state.data.hourRate}</p>
                        <p className='main-user-profile-label-skills'>Skills:</p>
                        <div className='main-user-profile-skill-list'>
                        {$userSkills}
                        </div>
                        <p className='main-user-profile-label-links'>Links:</p>
                        <div className='main-user-profile-link-list'>
                        {$userLinks}
                        </div>
                        <p className='main-user-profile-label-bio'>Bio:</p>
                        <p className='main-user-profile-bio'>{this.state.data.bio}</p>
                        {$userRateInfo}
                        {$userRatingStars}

                        <div className='main-user-profile-more-info-btn main-btn fw-700 color-purple bg-white'>more</div>
                    </div>
                    <a className='main-user-profile-nav-messages' href={config.messagesHash + this.props.email}>
                        <i className='fa fa-envelope'></i>
                    </a>
                    <a className='main-user-profile-nav-calendar' href={config.usersHash + this.props.email + config.calendarPath + '2016'}>
                        <i className='fa fa-calendar'></i>
                    </a>
                </div>
            </div>
        );
    }
});

module.exports = User;