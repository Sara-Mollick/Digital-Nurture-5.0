import React, { Component } from 'react';
import CohortDetails from './CohortDetails';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cohorts: [
                {
                    id: 'INTADMDF10',
                    name: '.NET FSD',
                    startedOn: '22-Feb-2022',
                    status: 'Scheduled',
                    coach: 'Aathma',
                    trainer: 'Jojo Jose'
                },
                {
                    id: 'ADM21JF014',
                    name: 'Java FSD',
                    startedOn: '10-Sep-2021',
                    status: 'Ongoing',
                    coach: 'Apoorv',
                    trainer: 'Elisa Smith'
                },
                {
                    id: 'CDBJF21025',
                    name: 'Java FSD',
                    startedOn: '24-Dec-2021',
                    status: 'Ongoing',
                    coach: 'Aathma',
                    trainer: 'John Doe'
                }
            ]
        };
    }

    render() {
        return (
            <div>
                <h1>Cohorts Details</h1>
                {this.state.cohorts.map((cohort, index) => (
                    <CohortDetails key={index} cohort={cohort} />
                ))}
            </div>
        );
    }
}

export default App;
