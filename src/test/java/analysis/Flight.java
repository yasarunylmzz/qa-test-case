package analysis;


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

    public int getPenaltyScore() {
    return price + (transfers * 1000) + (duration * 10);
}



}

