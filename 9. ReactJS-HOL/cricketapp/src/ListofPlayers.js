import React from 'react';

// Component to display all players using map()
function ListofPlayers(props) {
    const players = props.players;
    return (
        players.map((item) => {
            return (
                <div key={item.name}>
                    <li>Mr. {item.name} <span>{item.score}</span></li>
                </div>
            );
        })
    );
}

export default ListofPlayers;
