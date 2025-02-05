package search;
import java.util.ArrayList;
import java.util.Scanner;

class Search {
    public void getInput() {
        Scanner sc = new Scanner(System.in);
        System.out.print("> ");
        String input = sc.nextLine();

        System.out.print("> ");
        String word = sc.next();

        ArrayList<String> wordsInInput = insertInWordInputArray(input);

        foundCheck(wordsInInput, word);

    }

    public ArrayList<String> insertInWordInputArray(String input) {
        ArrayList<String> wordsInInput = new ArrayList<String>();
        input += " ";
        String tempWord = "";
        for (int index = 0; index < input.length(); index++) {
            if (input.charAt(index) != ' ') {
                tempWord += input.charAt(index);
            } else {
                wordsInInput.add(tempWord);
                tempWord = "";
            }
        }
        return wordsInInput;
    }

    public void foundCheck(ArrayList<String> wordsInInput, String word) {
        boolean found = false;
        int positionOfWord = -1;
        for (int index = 0; index < wordsInInput.size(); index++) {
            if (word.equals(wordsInInput.get(index))) {
                found = true;
                positionOfWord = index + 1;
                break;
            }
        }
        if (found) {
            System.out.println(positionOfWord);
        } else {
            System.out.println("Not found");
        }
    }

}


public class Main {
    public static void main(String[] args) {
        Search s1 = new Search();
        s1.getInput();
    }
}
