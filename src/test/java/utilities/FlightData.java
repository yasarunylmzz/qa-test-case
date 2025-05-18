package utilities;

public class FlightData {
    private String departureTime;
    private String arrivalTime;
    private String airlineName;
    private String price;
    private String connectionInfo;
    private String duration;

    public FlightData(String departureTime, String arrivalTime, String airlineName,
                      String price, String connectionInfo, String duration) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airlineName = airlineName;
        this.price = price;
        this.connectionInfo = connectionInfo;
        this.duration = duration;
    }

    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public String getAirlineName() { return airlineName; }
    public String getPrice() { return price; }
    public String getConnectionInfo() { return connectionInfo; }
    public String getDuration() { return duration; }


}
