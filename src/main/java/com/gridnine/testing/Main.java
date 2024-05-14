package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        Filter departureFilter = new DepartureFilter();
        List<Flight> filteredFlightsByDepartureBeforeNow = departureFilter.filterFlights(flights);
        System.out.println("Вылет до текущего момента времени - "
                + filteredFlightsByDepartureBeforeNow);

        Filter arrivalFilter = new ArrivalFilter();
        List<Flight> filteredFlightsByArrivalBeforeDeparture = arrivalFilter.filterFlights(flights);
        System.out.println("Имеются сегменты с датой прилёта раньше даты вылета - "
                + filteredFlightsByArrivalBeforeDeparture);

        Filter timeFilter = new TimeFilter();
        List<Flight> filteredFlightsByGroundTimeMoreThanTwoHours = timeFilter.filterFlights(flights);
        System.out.println("Общее время, проведённое на земле превышает два часа - "
                + filteredFlightsByGroundTimeMoreThanTwoHours);

        System.out.println("Исходные данные равняются - " + flights);
    }
}