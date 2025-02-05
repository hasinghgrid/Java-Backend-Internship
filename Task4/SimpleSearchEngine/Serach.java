package search.SimpleSearchEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Search {
    public void getInput(String fileName) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> nameWithMail = readFile(fileName);
        if (nameWithMail.isEmpty()) {
            System.out.println("No data found in the file.");
            return;
        }

        while(true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");

            int userChoice = sc.nextInt();
            sc.nextLine();

            switch(userChoice){
                case 1:
                    System.out.println("\nEnter a name or email to search all suitable people.");
                    System.out.print("> ");
                    String wordToSearch = sc.nextLine().toLowerCase().trim();
                    ArrayList<String> foundPeople = searchFoundPeople(wordToSearch, nameWithMail);
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

    public ArrayList<String> searchFoundPeople(String wordToSearch, ArrayList<String> nameWithMail) {
        ArrayList<String> foundList = new ArrayList<>();
        for (int index = 0; index < nameWithMail.size(); index++) {
            if (nameWithMail.get(index).toLowerCase().contains(wordToSearch)) {
                foundList.add(nameWithMail.get(index));
            }
        }
        return foundList;
    }

    public void displayFoundPeople(ArrayList<String> foundPeople){
        if(foundPeople.isEmpty()){
            System.out.println("No matching people found.");
        }
        else{
//            System.out.println("Found People:");
            for(int index=0; index<foundPeople.size(); index++){
                System.out.println(foundPeople.get(index));
            }
        }
    }

}

