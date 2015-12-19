import React from 'react';
import AboutUsComponent from '../components/about_us';
import TeachersView from '../views/teachers';


class Home extends React.Component {
    render() {
        return (
            <div>
                <AboutUsComponent />
                <TeachersView />
            </div>
        );
    }
}

module.exports = Home;