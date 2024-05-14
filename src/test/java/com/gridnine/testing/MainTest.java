package com.gridnine.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    private List<Flight> flights;

    @BeforeEach
    public void setUp() {
        flights = FlightBuilder.createFlights();
    }

    @Test
    public void filterByDepartureToTheCurrentMomentInTime() {
        Filter flightFilter = new DepartureFilter();
        List<Flight> filtered = flightFilter.filterFlights(flights);

        assertEquals(5, filtered.size());
        assertTrue(filtered.get(0).getSegments().get(0).getDepartureDate().isAfter(LocalDateTime.now()));
    }

    @Test
    public void filterByArrivalBeforeDeparture() {
        Filter flightFilter = new ArrivalFilter();
        List<Flight> filtered = flightFilter.filterFlights(flights);

        assertEquals(5, filtered.size());
        assertTrue(filtered.get(0).getSegments().get(0).getArrivalDate()
                .isAfter(filtered.get(0).getSegments().get(0).getDepartureDate()));
    }

    @Test
    public void filterByGroundTimeMoreThanTwoHours() {
        Filter flightFilter = new TimeFilter();
        List<Flight> filtered = flightFilter.filterFlights(flights);

        assertEquals(4, filtered.size());
        assertTrue(hasByLongGroundTime(filtered.get(0)));
        assertTrue(hasByLongGroundTime(filtered.get(1)));
    }

    private boolean hasByLongGroundTime(Flight flight) {
        List<Segment> segments = flight.getSegments();
        long groundTime = 0;

        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime currentArrival = segments.get(i).getArrivalDate();
            LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();
            groundTime += currentArrival.until(nextDeparture, ChronoUnit.HOURS);
        }
        return groundTime < 2;
    }
}