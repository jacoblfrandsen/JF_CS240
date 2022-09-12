package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class EvilHangman {

    public static void main(String[] args) {
        String dictionary = new String(args[0]);
        String wordL = new String(args[1]);
        String numG = new String(args[2]);

        File file = new File(dictionary);
        int wordLength = Integer.parseInt(wordL);
        int numGuess = Integer.parseInt(numG);

        EvilHangmanGame hangman = new EvilHangmanGame();

        try{
            hangman.startGame(file,wordLength);
        } catch (EmptyDictionaryException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tempGuesses = numGuess;
        Set<String> possibleSet = new TreeSet<>();
        for (int i = 0; i < tempGuesses; i++) {
            System.out.println("You have: " + tempGuesses + " guesses left!");
            System.out.println("Used letters: " + hangman.getSetGuesses());
            System.out.println("Word: " + hangman.masterKey);
            System.out.println("Enter Guess: ");
            Scanner userInput = new Scanner(System.in);
            String userString = userInput.next().toLowerCase();
//            System.out.println("Your Guess is: "+ userString);
            //check to see if userString is valid String
            char c = userString.toCharArray()[0];

            try{
                possibleSet = hangman.makeGuess(c);
            } catch (GuessAlreadyMadeException e) {
                System.out.println("Guess already made");
                continue;

            }

            tempGuesses--;

        }

    }

}
