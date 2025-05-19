package analysis;

import java.util.List;

class Flight {
    String airline;
    int price;
    int duration;
    int transfers;

    public Flight(String airline, int price, int duration, int transfers) {
        this.airline = airline;
        this.price = price;
        this.duration = duration;
        this.transfers = transfers;
    }

    public boolean dominates(Flight flight) {

        boolean betterOrEqual = (this.price <= flight.price) &&
                (this.duration <= flight.duration) &&
                (this.transfers <= flight.transfers);


        boolean strictlyBetter = (this.price < flight.price) ||
                (this.duration < flight.duration) ||
                (this.transfers < flight.transfers);


        return betterOrEqual && strictlyBetter;
    }

    public static List<Flight> findParetoOptimalParallel(List<Flight> flights) {
        return flights.parallelStream()
                .filter(f1 -> flights.stream().noneMatch(f2 -> f2 != f1 && f2.dominates(f1)))
                .toList();
    }




}

