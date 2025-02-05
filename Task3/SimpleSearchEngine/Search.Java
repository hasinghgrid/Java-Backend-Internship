package search.SimpleSearchEngine;

import java.util.ArrayList;
import java.util.Scanner;

public class Search {
    public void getInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of people:");
        System.out.print("> ");
        int numberOfPeople = sc.nextInt();
        sc.nextLine();


        System.out.println("\nEnter all people:");

        ArrayList<String> nameWithMail = new ArrayList<>();
        for(int number=0; number<numberOfPeople; number++){
            System.out.print("> ");
            String line = sc.nextLine().trim();
            nameWithMail.add(line);
        }

//        System.out.println("\nEnter the number of search queries:");
//        System.out.print("> ");



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

