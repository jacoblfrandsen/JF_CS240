package hangman;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class EvilHangman {

    public static void main(String[] args){ // throws IOException?
        String dictionary = args[0];
        String wordL= args[1]; // will need to parse to int later Interger.parseInt(wordLength);
        String g = args[2];
//        I have to change all the inputs into correct format right here
        int wordLength = Integer.parseInt(wordL);
        int guesses = Integer.parseInt(g);
        File file = new File(dictionary);
        char tempInputChar = ' ';

        int tempInt = 0;

        EvilHangmanGame hangmanGame = new EvilHangmanGame();
        try{
            hangmanGame.startGame(file,wordLength);

        }
        catch (EmptyDictionaryException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

//        Implemetnting Main

        tempInt = guesses;
        Set<String> pSet = new HashSet<>();

        for(int i =0; i < guesses; i++) {

            String curG = hangmanGame.masterKey;

//            Reading in the user input and changing it to a char
                System.out.println("You have " + tempInt + " guesses left");
                System.out.println("Used letters: " + hangmanGame.getSetGuesses());
                System.out.println("Word: " + curG );
                Scanner userInput = new Scanner(System.in);
                System.out.println("Enter letter to guess: ");

                String userString = userInput.next().toLowerCase();
                while(userString.length() > 1){
                    System.out.println("Invalid input");
                    System.out.print("Enter guess: ");
                    userString = userInput.next().toLowerCase();
                }
                tempInputChar = userString.charAt(0);
                while(!Character.isAlphabetic(tempInputChar)){
                    System.out.println("Invalid input");
                    System.out.print("Enter guess: ");
                    tempInputChar = userInput.next().charAt(0);
                }

//            making guess
            try{
                pSet = hangmanGame.makeGuess(tempInputChar);
            } catch (GuessAlreadyMadeException ex) {
                System.out.println("Guess already made!");
//                tempInt ++;
                continue;
            }
            if(hangmanGame.masterKey.equals(curG)){
                System.out.println("Sorry there are no "+tempInputChar+"'s");
                tempInt--;
            }
            else{
                int iCount = 0;
                curG = hangmanGame.masterKey;
                for(Character c: curG.toCharArray()){
                    if(c == tempInputChar){
                        iCount++;
                    }
                }
                String curWord = iCount > 1 ? "are "+iCount+" "+iCount+"'s":"is 1 " + tempInputChar;
                if(curG.indexOf('_') == -1){
                    System.out.println("You Win!\nThe word was: "+ curG);
                    System.exit(1);
                }
                System.out.println("Yes there " + curWord);
            }

        }
        System.out.println("You lose!\n hahahahah" + " Word was: " + hangmanGame.getStringFromList());

    }

}
