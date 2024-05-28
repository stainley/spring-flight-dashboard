import {useState, useEffect} from "react";
import PropTypes from "prop-types";
import './websocket.scss';

const WebSocketComponent = () => {
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        const ws = new WebSocket('ws://localhost:8080/websocket');

        ws.onmessage = (event) => {
            const flight = JSON.parse(event.data);
            setMessages(prevMesg => [...prevMesg.slice(-10), flight]);
        };

        return () => {
            ws.close();
        };
    }, []);

    return (
        <div>
            <h1>Flight Information</h1>
            <ul>
                {messages
                    .map((flight, index) => (
                    <li key={index} className="flight-row">
                        Flight No: {flight.flightNo},
                        Flight Name: {flight.flightName},
                        Flight Date: {formatDate(flight.flightDate)}
                        Flight Time: {extractTime(flight.flightDate)}
                    </li>
                ))}
            </ul>
        </div>
    );
};

WebSocketComponent.PropTypes = {
    messages: PropTypes.array.isRequired,
}


function formatDate(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;

}

function extractTime(dateString) {
    const date = new Date(dateString);
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    return `${hours}:${minutes}`;
}

export default WebSocketComponent;