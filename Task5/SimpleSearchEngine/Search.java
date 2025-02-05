package search.SimpleSearchEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Search {

    public void getInput(String fileName) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> nameWithMail = readFile(fileName);
        if (nameWithMail.isEmpty()) {
            System.out.println("No data found in the file.");
            return;
        }

        Map<String, Set<Integer>> invertedIndex = buildInvertedIndex(nameWithMail);

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");

            int userChoice = sc.nextInt();
            sc.nextLine();

            switch (userChoice) {
                case 1:
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
                    System.exit(0);

                default:
                    System.out.println("\nIncorrect option! Try again.");
                    break;
            }
        }
    }

    private ArrayList<String> readFile(String fileName) {
        ArrayList<String> nameWithMail = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                nameWithMail.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return nameWithMail;
    }

    private Map<String, Set<Integer>> buildInvertedIndex(ArrayList<String> nameWithMail) {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();

        for (int index = 0; index < nameWithMail.size(); index++) {
            String line = nameWithMail.get(index);
            String[] words = line.toLowerCase().split(" ");

            for (String word: words) {
                invertedIndex.putIfAbsent(word, new HashSet<>());
                invertedIndex.get(word).add(index);
            }
        }

        return invertedIndex;
    }

    public ArrayList<String> searchFoundPeople(String query, Map<String, Set<Integer>> invertedIndex, ArrayList<String> nameWithMail) {
        Set<Integer> resultIndexes = new HashSet<>();

        String[] queryWords = query.split(" ");

        for (String word: queryWords) {
            word = word.trim().toLowerCase();
            if (invertedIndex.containsKey(word)) {
                resultIndexes.addAll(invertedIndex.get(word));
            }
        }

        ArrayList<String> foundPeople = new ArrayList<>();
        for (int index: resultIndexes) {
            foundPeople.add(nameWithMail.get(index));
        }
        return foundPeople;
    }

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
