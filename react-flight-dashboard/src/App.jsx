import {useState, useEffect} from 'react'
import viteLogo from '/vite.svg'
import './App.css'
import FlightInfo from "./component/FlightInfo.jsx";

const EXTERNA_URL = 'ws://192.168.2.175:8081/websocket';
const LOCAL_HOST = 'ws://127.0.0.1:8081/websocket';
function App() {

    const [flights, setFlights] = useState([]);

    useEffect(() => {
        const ws = new WebSocket(LOCAL_HOST);

        ws.onmessage = (event) => {
            const flight = JSON.parse(event.data);
            setFlights(prevMsg => [...prevMsg.slice(-10), flight]);
        };

        return () => {
            ws.close();
        };
    }, []);

    const emptyInfo = {
        unplannedEventsCount: 0,
        plannedEventsCount: flights.length,
    }

    return (
        <>
            <div>
                <h1>Flight Board</h1>
                <a href="https://vitejs.dev" target="_blank">
                    <img src={viteLogo} className="logo" alt="Vite logo" style={{width: '50px', height: '50px'}}/>
                </a>
            </div>

            <div>
                <FlightInfo data={emptyInfo} flights={flights}/>
            </div>
        </>
    )
}

export default App
