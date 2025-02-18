package search;

import search.SimpleSearchEngine.Search;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Check if the arguments are provided correctly
        if (args.length == 2 && "--data".equals(args[0])) {
            // Create a File object using the provided file path
            File file = new File(args[1]);

            // Check if the file exists
            if (file.exists()) {
                // Instantiate the Search object and pass the File object
                Search search = new Search();
                search.getInput(file); // Pass the File object to getInput
            } else {
                System.out.println("File not found.");
            }
        } else {
            // If arguments are incorrect, print usage instructions
            System.out.println("Usage: java Search --data <filename>");
        }
    }
}
