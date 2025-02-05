package search;

import search.SimpleSearchEngine.Search;

class Main{
    public static void main(String[] args) {
        if (args.length == 2 && "--data".equals(args[0])) {
            String fileName = args[1];
            Search search = new Search();
            search.getInput(fileName);
        } else {
            System.out.println("Usage: java Search --data <filename>");
        }
    }
}

