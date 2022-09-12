package spell;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector{
    Trie trie = new Trie();
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {

        Scanner scanner = new Scanner(new File (dictionaryFileName));
        while(scanner.hasNext()){
            trie.add(scanner.next().toLowerCase());
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        Set<String> setEdit1 = new TreeSet<>();
        Set<String> setEdit2 = new TreeSet<>();
        String theWord = null;
        if(trie.find(inputWord.toLowerCase()) != null){
            return inputWord.toLowerCase();
        }

        deletion(inputWord, setEdit1);
        insertion(inputWord, setEdit1);
        alteration(inputWord, setEdit1);
        transposition(inputWord, setEdit1);

        theWord = suggestWord(setEdit1);
        if(theWord == null){
            for(String s: setEdit1){
                deletion(s, setEdit2);
                insertion(s, setEdit2);
                alteration(s, setEdit2);
                transposition(s, setEdit2);
            }
            theWord = suggestWord(setEdit2);
        }
        return theWord;
    }

    private String suggestWord(Set<String> set) {
        int freqWord = 0;
        String theWord = null;
        INode tempNode;
        for(String s: set){
            tempNode = trie.find(s.toLowerCase());
            if(tempNode != null){
                if(tempNode.getValue() > freqWord){
                    freqWord = tempNode.getValue();
                    theWord = s;
                }
            }
        }
        return theWord;
    }

    private void deletion(String inputWord, Set<String> set) {

        for (int i = 0; i < inputWord.length(); i++) {
            StringBuilder tempString = new StringBuilder(inputWord);
            tempString = tempString.deleteCharAt(i);
            set.add(tempString.toString());
        }
    }

    private void insertion(String inputWord, Set<String> set) {
        for (int i = 0; i < inputWord.length() + 1; i++) {
            for(char c = 'a'; c < 'z'; c++){
                StringBuilder tempString = new StringBuilder(inputWord);
                tempString = tempString.insert(i,c);
                set.add(tempString.toString());
            }
        }
    }

    private void alteration(String inputWord, Set<String> set) {
        char[] charArray = inputWord.toCharArray();
        for (int i = 0; i < charArray.length ; i++) {
            for (char c = 'a'; c < 'z'; c++) {
                charArray[i] = c;
                String tempString = new String(charArray);
                set.add(tempString);
            }
            charArray = inputWord.toCharArray();
        }
    }

    private void transposition(String inputWord, Set<String> set) {
        char[] charArray = inputWord.toCharArray();
        char[] tempArray = inputWord.toCharArray();
        for (int i = 0; i < charArray.length - 1 ; i++) {
            charArray[i] = tempArray[i+1];
            charArray[i+1] = tempArray[i];
            String tempString = new String(charArray);
            set.add(tempString);
            charArray = inputWord.toCharArray();
        }
    }
}
