import React from 'react';
import AboutUsComponent from '../components/aboutUs';
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