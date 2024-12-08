package com.gridnine.testing.filter;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class FlightFilter {

    //Exclude flights where departure time of the flight is before certain time
    public static List<Flight> excludeFlightsBeforeDepartureTime(final List<Flight> flights, final LocalDateTime departureTime) {
        List<Flight> filteredFlights = new ArrayList<>();

        for (Flight flight : flights) {
            Segment firstSegment = flight.getSegments().getFirst();
            if (firstSegment.getDepartureDate().isAfter(departureTime)) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }

    //Exclude flights where segment arrival time is earlier than departure time
    public static List<Flight> excludeArrivalTimeEarlierThanDepartureTime(final List<Flight> flights) {
        List<Flight> filteredFlights = new ArrayList<>();
        outerLoop:
        for (Flight flight : flights) {
            for (Segment segment : flight.getSegments()) {
                LocalDateTime departureTime = segment.getDepartureDate();
                LocalDateTime arrivalTime = segment.getArrivalDate();
                if (arrivalTime.isBefore(departureTime)) {
                    continue outerLoop;
                }
            }
            filteredFlights.add(flight);
        }
        return filteredFlights;
    }

    //Exclude flights where time on earth is more than two hours
    public static List<Flight> excludeFlightsWhereEarthTimeMoreThanTwoHours(final List<Flight> flights) {
        List<Flight> filteredFlights = new ArrayList<>();
        for (Flight flight : flights) {
            long earthHours = 0L;
            for (int i = 0; i < flight.getSegments().size() - 1; i++) {
                LocalDateTime arrivalTime = flight.getSegments().get(i).getArrivalDate();
                LocalDateTime nextDepartureTime = flight.getSegments().get(i + 1).getDepartureDate();
                long hourDifference = ChronoUnit.HOURS.between(arrivalTime, nextDepartureTime);
                earthHours += hourDifference;
            }
            if (earthHours < 2L) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }
}
