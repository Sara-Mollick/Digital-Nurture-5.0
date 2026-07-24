import React from 'react';

// Destructuring: Display Odd Team Players (1st, 3rd, 5th)
export function OddPlayers([first, , third, , fifth]) {
    return (
        <div>
            <li> First : {first} </li>
            <li> Third : {third} </li>
            <li> Fifth : {fifth}</li>
        </div>
    );
}

// Destructuring: Display Even Team Players (2nd, 4th, 6th)
export function EvenPlayers([, second, , fourth, , sixth]) {
    return (
        <div>
            <li> Second : {second} </li>
            <li> Fourth : {fourth} </li>
            <li> Sixth : {sixth}</li>
        </div>
    );
}

// Merge T20Players and RanjiTrophyPlayers using Spread operator
const T20Players = ['First Player', 'Second Player', 'Third Player'];
const RanjiTrophyPlayers = ['Fourth Player', 'Fifth Player', 'Sixth Player'];
export const IndianPlayers = [...T20Players, ...RanjiTrophyPlayers];

// Component to display merged Indian Players list
export function ListofIndianPlayers(props) {
    const players = props.IndianPlayers;
    return (
        players.map((item, index) => {
            return (
                <div key={index}>
                    <li>Mr. {item}</li>
                </div>
            );
        })
    );
}
