package analysis;

import java.io.*;
import java.util.*;

public class FlightAnalyzer {


    public static void flightGraphs(String fromCity, String toCity) {
        Map<String, List<Integer>> pricesAirlines = new HashMap<>();

        String csvFilePath = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/flights_"+fromCity+"_"+toCity+".csv";


        try (Scanner scanner = new Scanner(new FileReader(csvFilePath))) {
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                String airline = fields[2];
                int price = Integer.parseInt(fields[3]);

                pricesAirlines.computeIfAbsent(airline, k -> new ArrayList<>()).add(price);
            }

            System.out.println("Airlines  Max Price  Min Price  Avg Price");
            System.out.println("-----------------------------------------");

            for (Map.Entry<String, List<Integer>> entry : pricesAirlines.entrySet()) {
                String airline = entry.getKey();
                List<Integer> prices = entry.getValue();
                int sum = prices.stream().mapToInt(Integer::intValue).sum();
                System.out.printf("%s  %d  %d  %d%n", airline,
                        Collections.max(prices),
                        Collections.min(prices),
                        sum / prices.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void paretoOptimal(String fromCity, String toCity) {
        List<Flight> flights = new ArrayList<>();

        String csvFilePath = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/flights_"+fromCity+"_"+toCity+".csv";

        try (Scanner scanner = new Scanner(new FileReader(csvFilePath))) {
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

            System.out.println("\nPareto Optimal Flights:");
            for (Flight flight : paretoOptimal) {
                System.out.printf("%s | %d TL | %d dk | %d aktarma%n",
                        flight.airline, flight.price, flight.duration, flight.transfers);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
