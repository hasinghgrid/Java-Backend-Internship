package search.SimpleSearchEngineTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import search.SearchEngineInterface.AllSearchStrategy;
import search.SearchEngineInterface.AnySearchStrategy;
import search.SearchEngineInterface.NoneSearchStrategy;
import search.SimpleSearchEngine.Search;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class SearchTest {

    private Search search;

    @BeforeEach
    public void setUp() {
        search = new Search();
    }

    // Tests for the search strategies
    @Test
    public void testSearchFoundPeople_AllStrategy() {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();
        Set<Integer> johnDoeIndexes = new HashSet<>();
        johnDoeIndexes.add(0);
        invertedIndex.put("john", johnDoeIndexes);

        Set<Integer> janeDoeIndexes = new HashSet<>();
        janeDoeIndexes.add(1);
        invertedIndex.put("jane", janeDoeIndexes);


        ArrayList<String> nameWithMail = new ArrayList<>();
        nameWithMail.add("John Doe john@example.com");
        nameWithMail.add("Jane Doe jane@example.com");


        search.setSearchStrategy(new AllSearchStrategy());
        ArrayList<String> foundPeople = search.searchFoundPeople("john", invertedIndex, nameWithMail);

        assertEquals(1, foundPeople.size());
        assertEquals(1, foundPeople.size());
        assertEquals("John Doe john@example.com", foundPeople.get(0));
    }

    @Test
    public void testSearchFoundPeople_AnyStrategy() {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();
        Set<Integer> johnDoeIndexes = new HashSet<>();
        johnDoeIndexes.add(0);
        invertedIndex.put("john", johnDoeIndexes);

        Set<Integer> janeDoeIndexes = new HashSet<>();
        janeDoeIndexes.add(1);
        invertedIndex.put("jane", janeDoeIndexes);

        ArrayList<String> nameWithMail = new ArrayList<>();
        nameWithMail.add("John Doe john@example.com");
        nameWithMail.add("Jane Doe jane@example.com");

        search.setSearchStrategy(new AnySearchStrategy());

        ArrayList<String> foundPeople = search.searchFoundPeople("john", invertedIndex, nameWithMail);
        assertEquals(1, foundPeople.size());
        assertEquals("John Doe john@example.com", foundPeople.get(0));

        foundPeople = search.searchFoundPeople("jane", invertedIndex, nameWithMail);
        assertEquals(1, foundPeople.size());
        assertEquals("Jane Doe jane@example.com", foundPeople.get(0));
    }

    @Test
    public void testSearchFoundPeople_NoneStrategy() {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();
        Set<Integer> johnDoeIndexes = new HashSet<>();
        johnDoeIndexes.add(0);
        invertedIndex.put("john", johnDoeIndexes);
        Set<Integer> janeDoeIndexes = new HashSet<>();
        janeDoeIndexes.add(1);
        invertedIndex.put("jane", janeDoeIndexes);

        ArrayList<String> nameWithMail = new ArrayList<>();
        nameWithMail.add("John Doe john@example.com");
        nameWithMail.add("Jane Doe jane@example.com");

        search.setSearchStrategy(new NoneSearchStrategy());
        ArrayList<String> foundPeople = search.searchFoundPeople("john", invertedIndex, nameWithMail);

        // Only Jane Doe should be returned
        assertEquals(1, foundPeople.size());
        assertEquals("Jane Doe jane@example.com", foundPeople.get(0));
    }

    // Tests for building inverted index
    @Test
    public void testBuildInvertedIndex() {
        ArrayList<String> nameWithMail = new ArrayList<>();
        nameWithMail.add("John Doe john@example.com");
        nameWithMail.add("Jane Doe jane@example.com");

        Map<String, Set<Integer>> invertedIndex = search.buildInvertedIndex(nameWithMail);

        assert invertedIndex.containsKey("john");
        assert invertedIndex.containsKey("doe");
        assert invertedIndex.get("john").contains(0);
        assert invertedIndex.get("doe").contains(0);
        assert invertedIndex.get("jane").contains(1);
    }

    // Tests for displaying found people
    @Test
    public void testDisplayFoundPeople_EmptyList() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream mockOut = new PrintStream(outputStream);
        System.setOut(mockOut);

        search.displayFoundPeople(new ArrayList<>());

//        assert outputStream.toString().contains("No matching people found.");
        assertEquals("No matching people found.", outputStream.toString().trim());
        System.setOut(System.out);
    }

    @Test
    public void testDisplayFoundPeople_WithResults() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream mockOut = new PrintStream(outputStream);
        System.setOut(mockOut);

        ArrayList<String> foundPeople = new ArrayList<>();
        foundPeople.add("John Doe john@example.com");
        search.displayFoundPeople(foundPeople);

        assertEquals("1 persons found:", outputStream.toString().split("\n")[0].trim());
        assertEquals("John Doe john@example.com", outputStream.toString().split("\n")[1].trim());
//        assertEquals("1 persons found:\nJohn Doe john@example.com", outputStream.toString().trim());

        System.setOut(System.out);
    }

    // Tests for the getInput method
    @Disabled
    public void testGetInput_ValidFile() throws IOException {
        // Create a temporary file to simulate the test file
        File tempFile = File.createTempFile("testFile", ".txt");
        tempFile.deleteOnExit(); // Ensure file is deleted after the test

        // Write test data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("John Doe john@example.com\n");
            writer.write("Jane Smith jane@example.com\n");
        }

        // Simulate user input using System.setIn()
        String simulatedInput = "1\nALL\njohn\n0\n"; // Simulate the menu with user input for finding a person and then exit
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Create a spy for the Search class
        Search spySearch = spy(new Search());

        // Mock the readFile method to return an ArrayList<String> and ensure it receives a File object
        ArrayList<String> mockData = new ArrayList<>();
        mockData.add("John Doe john@example.com");
        mockData.add("Jane Smith jane@example.com");

        // Correctly mock readFile() with a File argument and return an ArrayList
        doReturn(mockData).when(spySearch).readFile(Mockito.any(File.class)); // Ensure File type is used

        // Mock buildInvertedIndex() to return a valid inverted index (useful for searching)
        Map<String, Set<Integer>> mockInvertedIndex = new HashMap<>();
        mockInvertedIndex.put("john", new HashSet<>(Arrays.asList(0))); // Map "john" to index 0
        doReturn(mockInvertedIndex).when(spySearch).buildInvertedIndex(mockData);

        // Capture output to check if the correct data was processed
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream mockOut = new PrintStream(outputStream);
        System.setOut(mockOut);

        // Call the method with the File object (not a String path)
        spySearch.getInput(tempFile); // This calls the getInput method, which internally calls readFile

        // Ensure that correct output is generated
        assertTrue(outputStream.toString().contains("=== MENU ==="));
        assertTrue(outputStream.toString().contains("1. Find a person"));
        assertTrue(outputStream.toString().contains("Select a matching strategy: ALL, ANY, NONE"));
        assertTrue(outputStream.toString().contains("Enter a name or email to search all suitable people."));
        assertTrue(outputStream.toString().contains("John Doe john@example.com"));
        assertTrue(outputStream.toString().contains("Jane Smith jane@example.com"));

        // Ensure that the program exits after choice 0
        assertTrue(outputStream.toString().contains("Bye!"));

        // Restore System.out and System.in
        System.setOut(System.out);
        System.setIn(System.in); // Reset System.in
    }

    @Test
    public void testGetInput_WithAnyStrategy() throws IOException {
        // Create a temporary file to simulate the test file
        File tempFile = File.createTempFile("testFile", ".txt");
        tempFile.deleteOnExit(); // Ensure file is deleted after the test

        // Write test data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("John Doe john@example.com\n");
            writer.write("Jane Smith jane@example.com\n");
        }

        // Simulate user input using System.setIn()
        String simulatedInput = "1\nANY\njohn\n0\n"; // Simulate user input for finding a person using ANY strategy
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Create a spy for the Search class
        Search spySearch = spy(new Search());

        // Mock the readFile method to return an ArrayList<String> and ensure it receives a File object
        ArrayList<String> mockData = new ArrayList<>();
        mockData.add("John Doe john@example.com");
        mockData.add("Jane Smith jane@example.com");

        // Correctly mock readFile() with a File argument and return an ArrayList
        doReturn(mockData).when(spySearch).readFile(Mockito.any(File.class)); // Ensure File type is used

        // Mock buildInvertedIndex() to return a valid inverted index (useful for searching)
        Map<String, Set<Integer>> mockInvertedIndex = new HashMap<>();
        mockInvertedIndex.put("john", new HashSet<>(Arrays.asList(0))); // Map "john" to index 0
        doReturn(mockInvertedIndex).when(spySearch).buildInvertedIndex(mockData);

        // Capture output to check if the correct data was processed
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream mockOut = new PrintStream(outputStream);
        System.setOut(mockOut);

        // Call the method with the File object (not a String path)
        spySearch.getInput(tempFile);

        // Ensure the "ANY" strategy is applied correctly
        assertTrue(outputStream.toString().contains("Select a matching strategy: ALL, ANY, NONE"));
        assertTrue(outputStream.toString().contains("Enter a name or email to search all suitable people."));
        assertTrue(outputStream.toString().contains("John Doe john@example.com"));

        // Ensure that the program exits after choice 0
        assertTrue(outputStream.toString().contains("Bye!"));

        // Restore System.out and System.in
        System.setOut(System.out);
        System.setIn(System.in); // Reset System.in
    }

    @Test
    public void testGetInput_WithAllStrategy() throws IOException {
        // Create a temporary file to simulate the test file
        File tempFile = File.createTempFile("testFile", ".txt");
        tempFile.deleteOnExit(); // Ensure file is deleted after the test

        // Write test data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("John Doe john@example.com\n");
            writer.write("Jane Smith jane@example.com\n");
        }

        // Simulate user input using System.setIn()
        String simulatedInput = "1\nALL\njohn\n0\n"; // Simulate user input for finding a person using ALL strategy
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Create a spy for the Search class
        Search spySearch = spy(new Search());

        // Mock the readFile method to return an ArrayList<String> and ensure it receives a File object
        ArrayList<String> mockData = new ArrayList<>();
        mockData.add("John Doe john@example.com");
        mockData.add("Jane Smith jane@example.com");

        // Correctly mock readFile() with a File argument and return an ArrayList
        doReturn(mockData).when(spySearch).readFile(Mockito.any(File.class)); // Ensure File type is used

        // Mock buildInvertedIndex() to return a valid inverted index (useful for searching)
        Map<String, Set<Integer>> mockInvertedIndex = new HashMap<>();
        mockInvertedIndex.put("john", new HashSet<>(Arrays.asList(0))); // Map "john" to index 0
        doReturn(mockInvertedIndex).when(spySearch).buildInvertedIndex(mockData);

        // Capture output to check if the correct data was processed
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream mockOut = new PrintStream(outputStream);
        System.setOut(mockOut);

        // Call the method with the File object (not a String path)
        spySearch.getInput(tempFile);

        // Ensure the "ALL" strategy is applied correctly
        assertTrue(outputStream.toString().contains("Select a matching strategy: ALL, ANY, NONE"));
        assertTrue(outputStream.toString().contains("Enter a name or email to search all suitable people."));
        assertTrue(outputStream.toString().contains("John Doe john@example.com"));

        // Ensure that the program exits after choice 0
        assertTrue(outputStream.toString().contains("Bye!"));

        // Restore System.out and System.in
        System.setOut(System.out);
        System.setIn(System.in); // Reset System.in
    }

    @Disabled
    public void testGetInput_WithNoneStrategy() throws IOException {
        // Create a temporary file to simulate the test file
        File tempFile = File.createTempFile("testFile", ".txt");
        tempFile.deleteOnExit(); // Ensure file is deleted after the test

        // Write test data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("John Doe john@example.com\n");
            writer.write("Jane Smith jane@example.com\n");
        }

        // Simulate user input using System.setIn()
        String simulatedInput = "1\nNONE\njohn\n0\n"; // Simulate user input for finding a person using NONE strategy
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Create a spy for the Search class
        Search spySearch = spy(new Search());

        // Mock the readFile method to return an ArrayList<String> and ensure it receives a File object
        ArrayList<String> mockData = new ArrayList<>();
        mockData.add("John Doe john@example.com");
        mockData.add("Jane Smith jane@example.com");

        // Correctly mock readFile() with a File argument and return an ArrayList
        doReturn(mockData).when(spySearch).readFile(Mockito.any(File.class)); // Ensure File type is used

        // Mock buildInvertedIndex() to return a valid inverted index (useful for searching)
        Map<String, Set<Integer>> mockInvertedIndex = new HashMap<>();
        mockInvertedIndex.put("john", new HashSet<>(Arrays.asList(0))); // Map "john" to index 0
        doReturn(mockInvertedIndex).when(spySearch).buildInvertedIndex(mockData);

        // Capture output to check if the correct data was processed
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream mockOut = new PrintStream(outputStream);
        System.setOut(mockOut);

        // Call the method with the File object (not a String path)
        spySearch.getInput(tempFile);

        // Ensure the "NONE" strategy is applied correctly
        assertTrue(outputStream.toString().contains("Select a matching strategy: ALL, ANY, NONE"));
        assertTrue(outputStream.toString().contains("Enter a name or email to search all suitable people."));
        assertTrue(outputStream.toString().contains("John Doe john@example.com"));

        // Ensure that the program exits after choice 0
        assertTrue(outputStream.toString().contains("Bye!"));

        // Restore System.out and System.in
        System.setOut(System.out);
        System.setIn(System.in); // Reset System.in
    }






    @Test
    public void testGetInput_InvalidFilePath() {
        String invalidFilePath = "nonexistent_file.txt";

        // Mock user input
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(1);

        // Capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream mockOut = new PrintStream(outputStream);
        System.setOut(mockOut);

        search.getInput(new File(invalidFilePath)); // Run method

        // Assert that error message for file not found is shown
        assertTrue(outputStream.toString().contains("File not found: " + invalidFilePath));
        System.setOut(System.out);
    }


    @Disabled
    public void testReadFile_EmptyFile() throws IOException {
        File emptyFile = File.createTempFile("emptyTestFile", ".txt");
        emptyFile.deleteOnExit(); // Ensure file is deleted after the test

        // Mocking readFile to simulate an empty file scenario using doReturn
        search = spy(search); // To spy on Search class and mock methods
        doReturn(new ArrayList<>())  // Return an empty list
                .when(search).readFile(any()); // Correct use of doReturn for spies

        // Capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream mockOut = new PrintStream(outputStream);
        System.setOut(mockOut);

        // Ensure "No data found in the file." is printed
        search.readFile(emptyFile); // This will trigger the message
        assertTrue(outputStream.toString().contains("No data found in the file."));

        // Restore System.out
        System.setOut(System.out);
    }


    @Test
    public void testReadFile_ValidFile() throws IOException {
        File tempFile = File.createTempFile("validTestFile", ".txt");
        tempFile.deleteOnExit();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("John Doe john@example.com\n");
            writer.write("Jane Smith jane@example.com\n");
        }

        ArrayList<String> result = search.readFile(tempFile);
        assertEquals(2, result.size());
        assertTrue(result.contains("John Doe john@example.com"));
        assertTrue(result.contains("Jane Smith jane@example.com"));
    }

}
