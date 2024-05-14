package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class TimeFilter implements Filter {
    @Override
    public List<Flight> filterFlights(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> calculateGroundTime(flight) <= 2)
                .collect(Collectors.toList());
    }

    private long calculateGroundTime(Flight flight) {
        List<Segment> segments = flight.getSegments();
        long groundTime = 0;
        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime currentArrival = segments.get(i).getArrivalDate();
            LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();
            groundTime += currentArrival.until(nextDeparture, ChronoUnit.HOURS);
        }
        return groundTime;
    }
}