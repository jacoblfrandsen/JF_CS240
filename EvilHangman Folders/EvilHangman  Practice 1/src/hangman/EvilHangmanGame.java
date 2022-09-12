package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame{


    Set<String> setWords;
    Set<Character> setGuesses;
    String masterKey;
    public String getSetGuesses(){
        StringBuilder stringBuilder = new StringBuilder();
        for(char c: setGuesses){
            stringBuilder.append(c);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        setWords = new TreeSet<>();
        setGuesses = new TreeSet<>();
        masterKey = null;

        Scanner scanner = new Scanner(dictionary);
        while(scanner.hasNext()){
            String curWord = new String();
            curWord = scanner.next();
            if(curWord.length() == wordLength){
                setWords.add(curWord);
            }
        }
        if(setWords.size() == 0){
            throw new EmptyDictionaryException();
        }


        if(setWords.size() == 0){
            throw new EmptyDictionaryException();
        }
        //set masterKey to blank
        StringBuilder tempString = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            tempString = tempString.insert(i,'_');
        }
        masterKey = tempString.toString();

    }
    private String createKey(char guess, String word) {
        StringBuilder tempString = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i) == guess){
                tempString.insert(i,guess);
            }
            else{
                tempString.insert(i,'_');
            }
        }
        return tempString.toString();
    }
    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);
        if(setGuesses.contains(guess)){
            throw new GuessAlreadyMadeException();
        }
        else{
            setGuesses.add(guess);
        }
        Set<String> finalSet = new TreeSet<>();
        Map<String,Set<String>> mapSets = new TreeMap<>();

        for(String s: setWords){

            String tempKey = new String(createKey(guess,s));
            if(!mapSets.containsKey(tempKey)){
                mapSets.put(tempKey,new TreeSet<>());
            }
            mapSets.get(tempKey).add(s);
        }

        mapSets = getBiggestSet(mapSets); // has to be a map becuase it could have more than on set in it
        finalSet = getPrioritySet(mapSets,guess);

        return finalSet;
    }



    private Map<String, Set<String>> getBiggestSet(Map<String, Set<String>> mapSets) {

        Map<String, Set<String>> changedMap = new TreeMap<>();
        int largestSize = 0;
        for(Set<String> set: mapSets.values()){
            if(set.size() > largestSize){
                largestSize = set.size();
            }
        }
        Set<String> setHolder = new HashSet<>();
        for(String mapKey: mapSets.keySet()){
            setHolder = mapSets.get(mapKey);
            if(setHolder.size() == largestSize){
                changedMap.put(mapKey,setHolder);
            }
        }


        return changedMap;
    }
    private Set<String> getPrioritySet(Map<String, Set<String>> mapSets, char guess) {

        ArrayList<String> returnSet = new ArrayList<>();

        int leastNumLetters = Integer.MAX_VALUE;
        //choose one with least amount of letters
        for(String setKey: mapSets.keySet()){
            char[] charArray = setKey.toCharArray();
            int numLetters = 0;
            for (char c: charArray) {
                if(c == guess){
                    numLetters++; //letters per word
                }
            }

            if(numLetters < leastNumLetters){
                leastNumLetters = numLetters;
                returnSet.clear(); //clear because smallest is found
                returnSet.add(setKey);
            }
            else if(numLetters == leastNumLetters){
                returnSet.add(setKey);// dont clear because smallest is same
            }
        }

        while(returnSet.size()>1){
            char[] topWord1Char = returnSet.get(0).toCharArray();
            char[] topWord2Char = returnSet.get(1).toCharArray();
            for(int i = topWord2Char.length-1; i > 0; i--){
                if (topWord1Char[i] == guess && topWord2Char[i] != guess){
                    returnSet.remove(1);
                    break;
                }else if (topWord2Char[i] == guess && topWord1Char[i] != guess){
                    returnSet.remove(0);
                    break;
                }
            }
        }


        String finalKey = String.join(",",returnSet);

        // this is for adding the two the privious key and the new key togeather
        StringBuilder tempString = new StringBuilder(masterKey);
        for (int i = 0; i < finalKey.length(); i++) {
            if (finalKey.charAt(i) != '_') {
                tempString.setCharAt(i, finalKey.charAt(i));
            }
        }
        masterKey = tempString.toString();
        setWords = mapSets.get(finalKey);
        return setWords;

    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return null;
    }
}
