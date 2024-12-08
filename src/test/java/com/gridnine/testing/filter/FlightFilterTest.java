package com.gridnine.testing.filter;

import com.gridnine.testing.Flight;
import com.gridnine.testing.FlightBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightFilterTest {

    List<Flight> flights = FlightBuilder.createFlights();


    @Test
    void excludeFlightsBeforeDepartureTime() {
        LocalDateTime departureTime = LocalDateTime.now().minusDays(1);
        List<Flight> flightList = FlightFilter.excludeFlightsBeforeDepartureTime(flights, departureTime);
        assertEquals(5, flightList.size());
    }

    @Test
    void excludeArrivalTimeEarlierThanDepartureTime() {
        List<Flight> flightList = FlightFilter.excludeArrivalTimeEarlierThanDepartureTime(flights);
        assertEquals(5, flightList.size());
    }

    @Test
    void excludeFlightsWhereEarthTimeMoreThanTwoHours() {
        List<Flight> flightList = FlightFilter.excludeFlightsWhereEarthTimeMoreThanTwoHours(flights);
        assertEquals(4, flightList.size());
    }
}