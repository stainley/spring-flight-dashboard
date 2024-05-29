import {useState, useEffect} from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import WebSocketComponent from "./component/WebSocketComponent.jsx";
import FlightInfo from "./component/FlightInfo.jsx";

function App() {
    const [count, setCount] = useState(0)
    const [flights, setFlights] = useState([]);


    useEffect(() => {
        const ws = new WebSocket('ws://localhost:8080/websocket');

        ws.onmessage = (event) => {
            const flight = JSON.parse(event.data);
            setFlights(prevMsg => [...prevMsg.slice(-10), flight]);
        };

        return () => {
            ws.close();
        };
    }, []);


    return (
        <>
            <div>
                <h1>Flight Board</h1>
                <a href="https://vitejs.dev" target="_blank">
                    <img src={viteLogo} className="logo" alt="Vite logo" style={{width: '50px', height: '50px'}}/>
                </a>
            </div>

            <div>
                <FlightInfo data={flights} flights={flights}/>
            </div>
        </>
    )
}

export default App
