import React, { Component } from 'react';
// Step 8: Import the CSS Module
import styles from './CohortDetails.module.css';

class CohortDetails extends Component {
    render() {
        const { cohort } = this.props;

        // Step 10: Define style for <h3> - green for "Ongoing", blue otherwise
        const headingStyle = {
            color: cohort.status.toLowerCase() === 'ongoing' ? 'green' : 'blue'
        };

        return (
            // Step 9: Apply the box class to the container div
            <div className={styles.box}>
                <h3 style={headingStyle}>{cohort.id} -{cohort.name}</h3>
                <dl>
                    <dt>Started On</dt>
                    <dd>{cohort.startedOn}</dd>
                    <dt>Current Status</dt>
                    <dd>{cohort.status}</dd>
                    <dt>Coach</dt>
                    <dd>{cohort.coach}</dd>
                    <dt>Trainer</dt>
                    <dd>{cohort.trainer}</dd>
                </dl>
            </div>
        );
    }
}

export default CohortDetails;
