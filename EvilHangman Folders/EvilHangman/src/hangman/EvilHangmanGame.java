package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame{


    Set<Character> setGuesses;
    Set<String> setWords;
    String masterKey;

    public Set<Character> getSetGuesses(){
        return setGuesses;
    }
    public String getStringFromList(){
        String tempString = new String();
        for (String s:
             setWords) {
            tempString = s;
        }
        return tempString;
    }

    @Override
        public void startGame (File dictionary,int wordLength) throws IOException, EmptyDictionaryException {
//        System.out.println("word lenght is: " + wordLength);
        setGuesses = new TreeSet<>();
        setWords = new HashSet<>();
        String subsetKey = null;

        Scanner scanner = new Scanner(dictionary);
        while (scanner.hasNext()) {
            String curWord = scanner.next();
            if(curWord.length() == wordLength ){
//                System.out.println("adding word: "+curWord);
                setWords.add(curWord.toLowerCase()); // added all words with right lenght
            }
        }
        if(setWords.size() == 0){
            throw new EmptyDictionaryException();
        }
        StringBuilder key = new StringBuilder();
        int i = 0;
        while(i < wordLength){
            key.append('_');
            i++;
        }
        masterKey = key.toString();

    }


    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {

        Set<String> tempSet = new HashSet<>();
        guess = Character.toLowerCase(guess);
        if(setGuesses.contains(guess)){
            throw new GuessAlreadyMadeException();
        }
        else{
            setGuesses.add(guess);
        }

        Map<String,Set<String>> mapSets = new TreeMap<String,Set<String>>();
        for (String word: setWords){
            String tempKey = createKey(guess,word);
//            System.out.println("key is " + tempKey);

            if(!mapSets.containsKey(tempKey)){ //if map doesnt already have key then put key  in there with empty set
                mapSets.put(tempKey,new HashSet<>());
            }
            mapSets.get(tempKey).add(word); // add the word to the set at the key in the map
        }

        mapSets = getBiggestSet(mapSets);
        //update masterkey with mapSetskey

        Set<String> tempP = getPrioritySet(mapSets, guess);


        return tempP;


    }

    public String createKey(char guess, String word){
        StringBuilder stringKey = new StringBuilder();
        for(int i = 0; i < word.length(); i++){
            char p = word.charAt(i);
            if(p == guess){
                stringKey.append(guess);
            }
            else{
                stringKey.append('_');
            }
        }
        return stringKey.toString();
    }
    public Map<String,Set<String>> getBiggestSet(Map<String,Set<String>> mapSets){
        int tempLarge = 0;
        Map<String, Set<String>> changedMap = new HashMap<String, Set<String>>();
        Set<String> tempSet; // holder for the keys of the map
        for(Set<String> s: mapSets.values()){// .values returns collection view in the map(so for each set in the whole map)
            tempLarge = s.size()>tempLarge ? s.size() : tempLarge; // tempLarge = set size at that set if(set size >tempLarge) else its is equal to tempLarge (templarge is now the size of biggest set)
        }
        for (String mapKey: mapSets.keySet()){ // for each key in the map
            tempSet = mapSets.get(mapKey); //temp set is == to the set at the key
            if(tempSet.size() == tempLarge){ // of biggest set then add to changedMap
                changedMap.put(mapKey,tempSet);
            }
        }

        return changedMap;
    }
    public Set<String> getPrioritySet(Map<String,Set<String>> mapSets, char guess) {

        int leastnumofletters = Integer.MAX_VALUE;
        ArrayList<String> returnSet = new ArrayList<>();

        // return the set with the least amount of letters
        for(String value: mapSets.keySet()) {
            char[] topWordChar = value.toCharArray();
            int numLetters = 0;
            for (char c : topWordChar) {
                if (guess == c) {
                    numLetters++;
                }
            }
            if (numLetters < leastnumofletters) {
                leastnumofletters = numLetters;
                returnSet.clear();
                returnSet.add(value);
            } else if (numLetters == leastnumofletters) {
                returnSet.add(value);
            }
        }
//        if there are more sets with same num letters
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

        String mapKey = String.join(",",returnSet);

        StringBuilder tempString = new StringBuilder(masterKey);
        for (int i = 0; i < mapKey.length(); i++) {
            if (mapKey.charAt(i) != '_') {
                tempString.setCharAt(i, mapKey.charAt(i));
            }
        }
        masterKey = tempString.toString();
        setWords = mapSets.get(mapKey);
        return setWords;


    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return null;
    }
}
//        for(String mapKey: mapSets.keySet()){
//            int w = 1;
//            int cnt = 0;
//            int wgtScr = 0;
//            for (int i = mapKey.length() -1; i>=0; i--){
//                if(mapKey.charAt(i) == guess){
//                    wgtScr += ++cnt*w;
//                }
//                w *= 2;
//            }
