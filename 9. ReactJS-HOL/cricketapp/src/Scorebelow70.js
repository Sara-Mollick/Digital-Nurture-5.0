import React from 'react';

// Component to filter and display players with scores below 70
function Scorebelow70(props) {
    const players = props.players;
    let players70 = [];

    // Filter players with scores <= 70 using arrow functions
    players.map((item) => {
        if (item.score <= 70) {
            players70.push(item);
        }
    });

    return (
        players70.map((item) => {
            return (
                <div key={item.name}>
                    <li>Mr. {item.name} <span>{item.score}</span></li>
                </div>
            );
        })
    );
}

export default Scorebelow70;
