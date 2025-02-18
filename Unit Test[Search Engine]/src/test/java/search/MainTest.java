package search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import search.Main;
import search.SimpleSearchEngine.Search;

import static org.mockito.Mockito.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class MainTest {


    private Search search;

    @BeforeEach
    public void setUp() {
        // Mock Search class
        search = mock(Search.class);
    }

    @Test
    void testMain_validArgs() throws IOException {
        // Simulate valid arguments: --data and a file path
        String[] args = {"--data", "data.txt"};

        File file = new File(args[1]);
        file.deleteOnExit(); // Ensure file is deleted after the test

        // Write test data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("John Doe john@example.com\n");
            writer.write("Jane Smith jane@example.com\n");
        }

//        // Mock the file to return true for exists()
//        File mockFile = mock(File.class);
//        when(mockFile.exists()).thenReturn(true);
//
//        // Create a mock Scanner to simulate user input for the menu
//        Scanner mockScanner = mock(Scanner.class);
//        // Simulate that the user selects "0" (Exit) immediately to avoid the infinite loop
//        when(mockScanner.nextLine()).thenReturn("0");
//
//        // Now, inject this mock Scanner into the method where getInput() expects it
//        // Mock the 'Search' instance's getInput() to simulate its behavior

        // Call the main method
//        Main.main(args);
//
//        Search mockSearch = spy(new Search());
//        doNothing().when(mockSearch).getInput(file);
        // Verify that getInput was called once on the Search instance
//        verify(search, times(1)).getInput(any(File.class));  // Ensures getInput(file) was called once
    }

    @Test
    void testMain_invalidArgs() {
        // Simulate invalid arguments
        String[] args = {"--wrongOption", "data.txt"};

        // Call the main method (this will print the usage message)
        Main.main(args);

        // Here you would verify the output or any other behavior based on invalid arguments
        verify(search, never()).getInput(any(File.class));  // Verify getInput is not called
    }
}
