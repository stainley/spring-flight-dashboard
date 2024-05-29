package org.salapp.springkafka.springflightshared.model;


public class Flight {
    private String number;
    private String origin;
    private String destination;
    private String type;
    private String station;
    private String begin;
    private String end;
    private String nextFlight;
    private String to;
    private String scheduledDepartureTime;
    private String estimatedDepartureTime;
    private String actualDepartureTime;
    private String delay;
    private String gateNumber;
    private FlightStatus status;

    public String getNumber() {
        return number;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getType() {
        return type;
    }

    public String getStation() {
        return station;
    }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public String getNextFlight() {
        return nextFlight;
    }

    public String getTo() {
        return to;
    }

    public String getScheduledDepartureTime() {
        return scheduledDepartureTime;
    }

    public String getEstimatedDepartureTime() {
        return estimatedDepartureTime;
    }

    public String getActualDepartureTime() {
        return actualDepartureTime;
    }

    public String getDelay() {
        return delay;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public static class Builder {
        private String number;
        private String origin;
        private String destination;
        private String type;
        private String station;
        private String begin;
        private String end;
        private String nextFlight;
        private String to;
        private String scheduledDepartureTime;
        private String estimatedDepartureTime;
        private String actualDepartureTime;
        private String delay;
        private String gateNumber;
        private FlightStatus status;


        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder origin(String origin) {
            this.origin = origin;
            return this;
        }

        public Builder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder status(FlightStatus status) {
            this.status = status;
            return this;
        }

        public Builder begin(String begin) {
            this.begin = begin;
            return this;
        }

        public Builder end(String end) {
            this.end = end;
            return this;
        }

        public Builder nextFlight(String nextFlight) {
            this.nextFlight = nextFlight;
            return this;
        }

        public Builder delay(String delay) {
            this.delay = delay;
            return this;
        }

        public Flight build() {
            Flight flight = new Flight();
            flight.number = this.number;
            flight.type = this.type;
            flight.origin = this.origin;
            flight.destination = this.destination;
            flight.status = this.status;
            flight.begin = this.begin;
            flight.end = this.end;
            flight.delay = this.delay;
            return flight;
        }
    }

}
