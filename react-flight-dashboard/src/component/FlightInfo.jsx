import PropTypes from 'prop-types';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendarDay } from '@fortawesome/free-solid-svg-icons'; // Example icon

import CA from "../assets/airlines/CA9012.jpg"
import AA from "../assets/airlines/AA1234.png";

import './flight_info.scss';
import {useEffect, useState} from "react";

const FlightInfo = ({ data, flights } ) => {

    const {
        unplannedEventsCount,
        plannedEventsCount,
        snapshotTime,
    } = data;

    const [totalDelay, setTotalDelay] = useState(0);

    useEffect(() => {
        const calculateTotalDelay = () => {
            const total = flights.filter(f => f.delay < 0).reduce((acc, flight) => {
                //console.log(delay, " DELAY");
                acc += parseFloat(flight.delay);
                return acc;
            }, 0);
            setTotalDelay(total);
        };
        calculateTotalDelay();
    }, [flights]);

    return(
        <div className="performance-dashboard">
            <div className="header">
                <h1 className="title">Performance Dashboard</h1>
                <p className="date">{formatUTCHoursMinutes(new Date().getTime())}</p>
            </div>
            <div className="summary">
                <div className="event-count">
                    Unplanned Events: {unplannedEventsCount}
                </div>
                <div className="downtime">{`Delay: ${convertMinutesToHMS(totalDelay)}`}</div>
            </div>
            <div className="summary">
                <div className="event-count">Planned Events: {plannedEventsCount}</div>
                <div className="event-count">Delays: {!isNaN(Math.abs(totalDelay/flights.length).toFixed(2)) ?  Math.abs(totalDelay/flights.length).toFixed(2) : 0}%</div>
            </div>

            <table className="table">
                <thead>
                <tr>
                    <th><span><FontAwesomeIcon icon={faCalendarDay} style={{ marginRight: "6px" }} />Flight Number</span></th>
                    <th>Type</th>
                    <th>From Airport</th>
                    <th>From Scheduled</th>
                    <th>To Airport</th>
                    <th>To Scheduled</th>
                    <th>Delay</th>
                    <th>Status</th>
                    <th>Cancellation</th>
                    <th>On Time Return</th>
                </tr>
                </thead>
                <tbody>
                {flights.map((flight, index) => (
                    <tr key={flight.index}>

                        <td>
                            {flight.number}</td>
                        <td>{flight.type}</td>
                        <td>{flight.origin}</td>
                        <td>{flight.begin}</td>
                        <td>{flight.destination}</td>
                        <td>{flight.end}</td>
                        <td>{flight.delay < 0 ? convertMinutesToHMS(flight.delay) : ''}</td>
                        <td className={flight.delay > 0 ? 'on-time' : 'delay'}>{flight.status}</td>
                        <td>{flight.cancellation}</td>
                        <td>{flight.onTimeReturn ? "Yes" : "No"}</td>
                    </tr>
                ))}
                </tbody>
            </table>

        </div>
    );
}

FlightInfo.propTypes = {

    flights: PropTypes.shape({
        flightNo: PropTypes.string.isRequired,
        flightType: PropTypes.string.isRequired,
        aircraftType: PropTypes.string.isRequired,
        registration: PropTypes.object.isRequired,
        fromAirport: PropTypes.string.isRequired,
        fromCode: PropTypes.string.isRequired,
        fromScheduledTime: PropTypes.string.isRequired,
        fromEstimatedTime: PropTypes.string.isRequired,
        fromActualTime: PropTypes.string.isRequired,
        fromDelay: PropTypes.string.isRequired,
        toAirport: PropTypes.string.isRequired,
        toCode: PropTypes.string.isRequired,
        toScheduledTime: PropTypes.string.isRequired,
        toActualTime: PropTypes.string.isRequired,
        downtime: PropTypes.string.isRequired,
        cancellation: PropTypes.string.isRequired,
    }).isRequired,
};

function formatUTCHoursMinutes(timestamp) {
    const date = new Date(timestamp);
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');

    return `${hours}:${minutes} ETC`;
}

function convertMinutesToHMS(minutes) {
    minutes = Math.abs(minutes);

    // Calculate hours
    const hours = Math.floor(minutes / 60);

    // Calculate remaining minutes
    const remainingMinutes = minutes % 60;

    // Format output with leading zeros
    const formattedHours = hours.toString().padStart(2, '0');
    const formattedMinutes = remainingMinutes.toString().padStart(2, '0');

    // Return formatted string with sign for negative values
    return `${formattedHours}h:${formattedMinutes}m`;
}


export default FlightInfo;