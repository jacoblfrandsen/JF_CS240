package spell;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector{

    Trie trie = new Trie();
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNext()){
            String tempString = scanner.next();
            trie.add(tempString.toLowerCase());
        }

    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        Set<String> setEdit1 = new TreeSet<>();
        Set<String> setEdit2 = new TreeSet<>();
        String theWord = new String();
        if(trie.find(inputWord.toLowerCase()) != null){
            return inputWord.toLowerCase();
        }

        deletion(inputWord, setEdit1);
        insertion(inputWord, setEdit1);
        alteration(inputWord, setEdit1);
        transposition(inputWord, setEdit1);

        theWord = suggestingWord(setEdit1);

        if(theWord == null){
            for(String s: setEdit1){
                deletion(s, setEdit2);
                insertion(s, setEdit2);
                alteration(s, setEdit2);
                transposition(s, setEdit2);
            }
            theWord = suggestingWord(setEdit2);

            if(theWord == null){
                return null;
            }
        }
        return theWord;
    }

    private String suggestingWord(Set<String> set) {
        String theWord = null;
        INode tempNode;
        int freqWord = 0;
        for(String word: set){
            tempNode = trie.find(word);
            if(tempNode != null){
                if(tempNode.getValue()> freqWord){
                    freqWord = tempNode.getValue();
                    theWord = word;
                }
            }
        }
        return theWord;

    }

    private void deletion(String inputWord, Set<String> set) {
        StringBuilder tempString = new StringBuilder(inputWord);
        for (int i = 0; i < inputWord.length(); i++) {
            tempString = tempString.deleteCharAt(i);
            set.add(tempString.toString());
            tempString = new StringBuilder(inputWord);
        }
    }

    private void insertion(String inputWord, Set<String> set) {
        StringBuilder tempString = new StringBuilder(inputWord);
        for (int i = 0; i < inputWord.length() + 1; i++) {
            for(char c = 'a'; c <= 'z'; c++){
                tempString = new StringBuilder(inputWord);
                tempString = tempString.insert(i,c);
                set.add(tempString.toString());
            }

        }
    }

    private void alteration(String inputWord, Set<String> set) {
        char[] charArray = inputWord.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            for(char c = 'a'; c <= 'z'; c++){
                charArray[i]= c;
                String tempString = new String(charArray);
                set.add(tempString);
            }
            charArray = inputWord.toCharArray();
        }
    }

    private void transposition(String inputWord, Set<String> set) {
        char[] charArray = inputWord.toCharArray();
        char[] tempCharArray = inputWord.toCharArray();
        for (int i = 0; i < charArray.length - 1; i++) {
            charArray[i] = tempCharArray[i+1];
            charArray[i+1] = tempCharArray[i];
            String tempString = new String(charArray);
            set.add(tempString);
            charArray = inputWord.toCharArray();
        }
    }
}
