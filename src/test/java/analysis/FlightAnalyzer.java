package analysis;

import java.io.*;
import java.util.*;

import org.slf4j.Logger;

import pages.HomePage;

public class FlightAnalyzer {
    public static final Logger logger = org.slf4j.LoggerFactory.getLogger(HomePage.class);


    public static void flightGraphs(String fromCity, String toCity) {
        Map<String, List<Integer>> pricesAirlines = new HashMap<>();

        String csvFilePath = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/flights_" + fromCity + "_" + toCity + ".csv";
        String outputCsvPath = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/data/graph/flight_"+fromCity+"+"+toCity+"_output.csv";

        try (Scanner scanner = new Scanner(new FileReader(csvFilePath));
             PrintWriter writer = new PrintWriter(new FileWriter(outputCsvPath))) {

            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                String airline = fields[2];
                int price = Integer.parseInt(fields[3]);

                pricesAirlines.computeIfAbsent(airline, k -> new ArrayList<>()).add(price);
            }

            writer.println("Airline,Max Price,Min Price,Avg Price");

            for (Map.Entry<String, List<Integer>> entry : pricesAirlines.entrySet()) {
                String airline = entry.getKey();
                List<Integer> prices = entry.getValue();
                int max = Collections.max(prices);
                int min = Collections.min(prices);
                int avg = prices.stream().mapToInt(Integer::intValue).sum() / prices.size();



                writer.printf("%s,%d,%d,%d%n", airline, max, min, avg);
            }

            logger.info("csv file created: " + outputCsvPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void paretoOptimal(String fromCity, String toCity) {
        List<Flight> flights = new ArrayList<>();

        String csvFilePath = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/flights_" + fromCity + "_" + toCity + ".csv";
        String csvOutputPath = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/data/pareto/pareto_" + fromCity + "_" + toCity + ".csv";

        try (
                Scanner scanner = new Scanner(new FileReader(csvFilePath));
                PrintWriter writer = new PrintWriter(new FileWriter(csvOutputPath))
        ) {
            scanner.nextLine(); // Skip header

            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                String airline = fields[2];
                int price = Integer.parseInt(fields[3]);
                int transfers = Integer.parseInt(fields[4]);
                int duration = Integer.parseInt(fields[5]);

                flights.add(new Flight(airline, price, duration, transfers));
            }

            List<Flight> paretoOptimal = new ArrayList<>();
            for (Flight f1 : flights) {
                boolean dominated = false;
                for (Flight f2 : flights) {
                    if (f2 != f1 && f2.dominates(f1)) {
                        dominated = true;
                        break;
                    }
                }
                if (!dominated) {
                    paretoOptimal.add(f1);
                }
            }

            writer.println("Airline,Price,Duration,Transfers");

            for (Flight flight : paretoOptimal) {
                writer.printf("%s,%d,%d,%d%n",
                        flight.airline, flight.price, flight.duration, flight.transfers);
            }


            logger.info("CSV file created: " + csvOutputPath);

        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }


    public static void paretoOptimalPenalty(String fromCity, String toCity) {
    List<Flight> flights = new ArrayList<>();

    String csvFilePath = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/flights_" + fromCity + "_" + toCity + ".csv";
    String csvOutputPath = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/data/pareto/pareto_" + fromCity + "_" + toCity + ".csv";

        try (Scanner scanner = new Scanner(new FileReader(csvFilePath));
             PrintWriter writer = new PrintWriter(new FileWriter(csvOutputPath))
        ) {
        scanner.nextLine();

        while (scanner.hasNextLine()) {
            String[] fields = scanner.nextLine().split(",");
            String airline = fields[2];
            int price = Integer.parseInt(fields[3]);
            int transfers = Integer.parseInt(fields[4]);
            int duration = Integer.parseInt(fields[5]);

            flights.add(new Flight(airline, price, duration, transfers));
        }
        writer.println("Airline,Price,Duration,Transfers");

        int minScore = Integer.MAX_VALUE;
        List<Flight> bestFlights = new ArrayList<>();

        for (Flight flight : flights) {
            int penaltyScore = flight.getPenaltyScore();

            if (penaltyScore < minScore) {
                minScore = penaltyScore;
                bestFlights.clear();
                bestFlights.add(flight);
            } else if (penaltyScore == minScore) {
                bestFlights.add(flight);
            }
        }


        for (Flight flight : bestFlights) {
            int score = flight.price + (flight.transfers * 1000) + (flight.duration * 10);
            writer.printf("%s,%d,%d,%d%n",
                    flight.airline, flight.price, flight.duration, flight.transfers);
        }

    } catch (Exception e) {
        logger.error("Error reading CSV file: " + e.getMessage());
    }
}

}
