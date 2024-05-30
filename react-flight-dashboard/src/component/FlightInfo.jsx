import PropTypes from 'prop-types';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faCalendarDay} from '@fortawesome/free-solid-svg-icons'; // Example icon
import './flight_info.scss';
import {useMemo} from "react";

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

    const formattedHours = hours.toString().padStart(2, '0');
    const formattedMinutes = remainingMinutes.toString().padStart(2, '0');

    // Return formatted string with sign for negative values
    return `${formattedHours}h:${formattedMinutes}m`;
}

const FlightInfo = ({data, flights}) => {

    const {unplannedEventsCount, plannedEventsCount} = data;

    const totalDelay = useMemo(() => {
        return flights.filter(flight => flight.delay < 0).reduce((acc, flight) => acc + parseFloat(flight.delay), 0);
    }, [flights]);


    const delayPercentage = useMemo(() => {
        return flights.length > 0 && !isNaN(totalDelay) ?
            (Math.abs(totalDelay) / flights.length).toFixed(2) : 0;
    }, [totalDelay, flights.length]);

    return (
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
                <div
                    className="event-count">Delays: {delayPercentage}%
                </div>
            </div>

            <table className="table">
                <thead>
                <tr>
                    <th><span><FontAwesomeIcon icon={faCalendarDay} style={{marginRight: "6px"}}/>Flight Number</span>
                    </th>
                    <th>Type</th>
                    <th>From Airport</th>
                    <th>From Scheduled</th>
                    <th>To Airport</th>
                    <th>To Scheduled</th>
                    <th>Delay</th>
                    <th>Status</th>
                    <th>Cancellation</th>
                </tr>
                </thead>
                <tbody>
                {flights.map((flight, index) => (
                    <tr key={index}>
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
                    </tr>
                ))}
                </tbody>
            </table>

        </div>
    );
}

FlightInfo.propTypes = {
    data: PropTypes.shape({
        unplannedEventsCount: PropTypes.number,
        plannedEventsCount: PropTypes.number,
    }),

    flights: PropTypes.arrayOf(
        PropTypes.shape({
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
    ),
};

export default FlightInfo;
