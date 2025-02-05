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

        System.out.println("\nEnter the number of search queries:");
        System.out.print("> ");
        int numberOfSearchQueries = sc.nextInt();
        sc.nextLine();


        while(numberOfSearchQueries > 0 ) {
            System.out.println("\nEnter data to search people:");
            System.out.print("> ");
            String wordToSearch = sc.nextLine().toLowerCase().trim();
            ArrayList<String> foundPeople = searchFoundPeople(numberOfSearchQueries, wordToSearch, nameWithMail);
            displayFoundPeople(foundPeople,numberOfSearchQueries);
            numberOfSearchQueries--;
        }
    }


    public ArrayList<String> searchFoundPeople(int numberOfSearchQueries, String wordToSearch, ArrayList<String> nameWithMail) {
        ArrayList<String> foundList = new ArrayList<>();
        for (int index = 0; index < nameWithMail.size(); index++) {
            if (nameWithMail.get(index).toLowerCase().contains(wordToSearch)) {
//                System.out.println("here ");
                foundList.add(nameWithMail.get(index));
            }
        }
        return foundList;
    }


    public void displayFoundPeople(ArrayList<String> foundPeople, int numberOfSearchQueries){
        if(foundPeople.isEmpty()){
            System.out.println("No matching people found.");
        }
        else{
            System.out.println("Found People:");
            for(int index=0; index<foundPeople.size(); index++){
                System.out.println(foundPeople.get(index));
            }
        }
    }

}

