package search.SimpleSearchEngine;

import search.SearchEngineInterface.AllSearchStrategy;
import search.SearchEngineInterface.AnySearchStrategy;
import search.SearchEngineInterface.NoneSearchStrategy;
import search.SearchEngineInterface.SimpleSearchEngine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Search {

    private SimpleSearchEngine searchStrategy;

    // Set the search strategy at runtime
    public void setSearchStrategy(SimpleSearchEngine searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public void getInput(File file) {
        // Use a Scanner with UTF-8 encoding for user input
        Scanner sc = new Scanner(System.in, "UTF-8");

        // Read the data from the file
        ArrayList<String> nameWithMail = readFile(file);
        if (nameWithMail.isEmpty()) {
            System.out.println("No data found in the file.");
            return;
        }

        // Build the inverted index for the data
        Map<String, Set<Integer>> invertedIndex = buildInvertedIndex(nameWithMail);

        // Interactive menu for the user
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");

            int userChoice = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            switch (userChoice) {
                case 1:
                    System.out.println("\nSelect a matching strategy: ALL, ANY, NONE");
                    System.out.print("> ");
                    String strategy = sc.nextLine().toUpperCase().trim();

                    // Dynamically set the search strategy based on user input
                    switch (strategy) {
                        case "ALL":
                            setSearchStrategy(new AllSearchStrategy());
                            break;
                        case "ANY":
                            setSearchStrategy(new AnySearchStrategy());
                            break;
                        case "NONE":
                            setSearchStrategy(new NoneSearchStrategy());
                            break;
                        default:
                            System.out.println("Invalid strategy! Defaulting to ANY.");
                            setSearchStrategy(new AnySearchStrategy());
                            break;
                    }

                    System.out.println("\nEnter a name or email to search all suitable people.");
                    System.out.print("> ");
                    String query = sc.nextLine().toLowerCase().trim();

                    ArrayList<String> foundPeople = searchFoundPeople(query, invertedIndex, nameWithMail);
                    displayFoundPeople(foundPeople);
                    break;

                case 2:
                    System.out.println("\n=== List of people ===");
                    displayFoundPeople(nameWithMail);
                    break;

                case 0:
                    System.out.print("\nBye!");
                    return;

                default:
                    System.out.println("\nIncorrect option! Try again.");
                    break;
            }
        }
    }

    // Read the file and return a list of names with emails
    public ArrayList<String> readFile(File file) {
        ArrayList<String> nameWithMail = new ArrayList<>();

        // Ensure the file exists and is accessible
        if (!file.exists()) {
            System.out.println("File not found: " + file.getPath());
            return nameWithMail;
        }

        // Use a BufferedReader with proper exception handling
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespaces
                if (!line.isEmpty()) { // Skip empty lines
                    nameWithMail.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return nameWithMail;
    }

    // Build an inverted index for fast searching
    public Map<String, Set<Integer>> buildInvertedIndex(ArrayList<String> nameWithMail) {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();

        for (int index = 0; index < nameWithMail.size(); index++) {
            String line = nameWithMail.get(index);
            String[] words = line.toLowerCase().split(" "); // Convert to lowercase to ensure case-insensitive search

            for (String word : words) {
                invertedIndex.putIfAbsent(word, new HashSet<>());
                invertedIndex.get(word).add(index);
            }
        }

        return invertedIndex;
    }

    // Search for people based on the current search strategy
    public ArrayList<String> searchFoundPeople(String query, Map<String, Set<Integer>> invertedIndex, ArrayList<String> nameWithMail) {
        String[] queryWords = query.split(" ");

        // Use the current strategy to perform the search
        Set<Integer> resultIndexes = searchStrategy.search(queryWords, invertedIndex);

        ArrayList<String> foundPeople = new ArrayList<>();
        for (int index : resultIndexes) {
            foundPeople.add(nameWithMail.get(index));
        }
        return foundPeople;
    }

    // Display the results to the user
    public void displayFoundPeople(ArrayList<String> foundPeople) {
        if (foundPeople.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            System.out.println(foundPeople.size() + " persons found:");
            for (String person : foundPeople) {
                System.out.println(person);
            }
        }
    }
}
