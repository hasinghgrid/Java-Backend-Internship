# HyperSkill-Simple Search Engine

## Overview

This is a Java-based simple search engine that allows users to search through a collection of names and emails using multiple search strategies. The application employs the **Strategy design pattern** to switch between different search algorithms dynamically, based on user input.

## Features

- **Multiple Search Strategies**: The program allows users to search with different strategies:
  - **ALL**: Displays results only when all search terms match.
  - **ANY**: Displays results when any of the search terms match.
  - **NONE**: Excludes results when any of the search terms match.
  
- **Inverted Indexing**: The search engine builds an inverted index to efficiently perform searches, improving performance compared to linear searches.

- **Dynamic Strategy Selection**: The search strategy is selected dynamically at runtime based on the user's choice, thanks to the Strategy design pattern.

## Design Pattern: Strategy Pattern

In this project, the **Strategy pattern** is used to define a family of algorithms (search strategies), which can be interchanged at runtime based on user selection.

- **Strategy Interface (`SimpleSearchEngine`)**: The core interface that all search strategies implement. It contains the `search()` method which is used to perform the search using different strategies.
  
- **Concrete Strategies**: 
  - **`AllSearchStrategy`**: Returns results that match all search terms.
  - **`AnySearchStrategy`**: Returns results that match any of the search terms.
  - **`NoneSearchStrategy`**: Returns results that do not match any of the search terms.

By using the Strategy pattern, the program can dynamically switch between the search algorithms (strategies) without modifying the context of the search (the `Search` class).

## Core Classes and Functionality

1. **Search Class**:
   - The main class responsible for handling user input and interacting with the selected search strategy.
   - It reads a file containing names and emails, builds an inverted index for efficient search, and offers a menu for the user to choose search options.
   
2. **Search Strategy Interface (`SimpleSearchEngine`)**:
   - Defines the common interface for all search strategies with the `search()` method.
   
3. **Concrete Search Strategies**:
   - **`AllSearchStrategy`**: Implements the logic to return results that match all query terms.
   - **`AnySearchStrategy`**: Implements the logic to return results that match at least one query term.
   - **`NoneSearchStrategy`**: Implements the logic to return results that match none of the query terms.

4. **Inverted Index**:
   - The program constructs an inverted index, which is a map of words to the set of line numbers (indexes) where they appear. This index is used to perform efficient searches.

5. **User Interaction**:
   - The user interacts with the program via a simple menu where they can select a search strategy and query for names or emails.
   - The program continuously prompts the user until they choose to exit.

## How It Works

1. **File Reading**: The program reads a file that contains names and emails, line by line. It stores each line in a list for further processing.

2. **Inverted Index Construction**: For each name/email entry, it splits the string into words and builds an inverted index, which allows fast lookups of which line contains which word.

3. **Search Execution**: The user is presented with a search menu where they can:
   - Select a search strategy: `ALL`, `ANY`, or `NONE`.
   - Enter a query (name or email) that will be matched against the indexed data.
   - View the search results based on the chosen strategy.

4. **Dynamic Strategy Selection**: Depending on the user's choice of strategy, the corresponding search algorithm is applied to the query.

## Conclusion

This project demonstrates the application of the **Strategy pattern** to implement a simple but efficient search engine. By using the Strategy pattern, the search algorithm is decoupled from the main logic of the program, making the code more flexible and maintainable. The use of an inverted index also improves the efficiency of the search process, allowing for fast lookups.

The combination of these patterns and techniques makes this search engine scalable, maintainable, and easy to extend with additional search strategies in the future.
