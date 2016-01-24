import React from 'react';

class AboutUs extends React.Component {
    render() {
        return (
            <section id='about-us' className='main-section'>
                <iframe className='main-video' width='100%' height='560px' src='https://www.youtube.com/embed/cSTEB8cdQwo' frameBorder='0' allowFullScreen='true'></iframe>
                <div className='overlay-content'>
                    <h2 className='section-header bg-gold color-white' data-hint='about us!' data-position='bottom-right-aligned'>We are learning programming in pair programming fashion.
                        <br/>
                        <span data-intro='Hello step one!'>Each lesson is designed especially for you !</span>
                        <br/>
                        <br/>
                        WE'RE COMING SOON
                    </h2>
                </div>
            </section>
        );
    }
}

module.exports = AboutUs;