package pages;

public class FlightPage {

    public int parseConnectionInfo(String connectionInfo) {
        if (connectionInfo == null) {
            return -1;
        }

        connectionInfo = connectionInfo.toLowerCase().trim();

        if (connectionInfo.contains("direkt")) {
            return 0;
        } else if (connectionInfo.contains("1 aktarma")) {
            return 1;
        } else if (connectionInfo.contains("2 aktarma")) {
            return 2;
        } else {

            return -1;
        }
    }
}
