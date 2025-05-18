package pages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    public int convertDurationToMinutes(String duration) {
        int totalMinutes = 0;

        Pattern pattern = Pattern.compile("(?:(\\d+)g)?\\s*(?:(\\d+)sa)?\\s*(?:(\\d+)dk)?");
        Matcher matcher = pattern.matcher(duration.trim());

        if (matcher.matches()) {
            String days = matcher.group(1); // gün
            String hours = matcher.group(2); // saat
            String minutes = matcher.group(3); // dakika

            if (days != null) {
                totalMinutes += Integer.parseInt(days) * 24 * 60;
            }
            if (hours != null) {
                totalMinutes += Integer.parseInt(hours) * 60;
            }
            if (minutes != null) {
                totalMinutes += Integer.parseInt(minutes);
            }
        }

        return totalMinutes;
    }

}
