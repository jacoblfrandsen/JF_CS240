package spell;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Set;

public class SpellCorrector implements ISpellCorrector{
    Trie trie = new Trie();
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner scan = new Scanner(new File(dictionaryFileName));
        String tempString = null;
        while(scan.hasNext()){
            tempString = scan.next();
//            System.out.println("reading in: " + tempString);
            trie.add(tempString.toLowerCase());
        }
        scan.close();

    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        Set<String> setEdit1 = new TreeSet<>();
        Set<String> setEdit2 = new TreeSet<>();
        String theWord;
        if(trie.find(inputWord.toLowerCase())!= null){
            return inputWord.toLowerCase();
        }

        deletion(inputWord, setEdit1);
        insertion(inputWord, setEdit1);
        alteration(inputWord, setEdit1);
        transposition(inputWord, setEdit1);

        theWord = sugestWord(inputWord,setEdit1);
        if(theWord == null){
            for (String s: setEdit1) {

                deletion(s, setEdit2);
                insertion(s, setEdit2);
                alteration(s, setEdit2);
                transposition(s, setEdit2);
                theWord = sugestWord(s,setEdit2);

            }
        }
        return theWord;
    }

    private String sugestWord(String inputWord, Set<String> set) {
        String theWord = null;
        int freqWord = 0;
        INode tempNode;
        for(String s: set){
            tempNode = trie.find(s.toLowerCase());
            if(tempNode!= null){
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
        for (int i = 0; i < inputWord.length()+1; i++) {
            for(char c = 'a'; c < 'z'; c++){
                StringBuilder tempString = new StringBuilder(inputWord);
                tempString = tempString.insert(i,c);
                set.add(tempString.toString());
//                System.out.println("adding word: " +tempString.toString());
            }
        }
    }
    private void alteration(String inputWord, Set<String> set) {
        char charArray[] = inputWord.toCharArray();
        for(int i = 0; i < inputWord.length(); i++){
            for(char c = 'a'; c < 'z'; c++){
                charArray[i] = c;
                String tempString = new String(charArray);
                set.add(tempString);
            }
            charArray = inputWord.toCharArray();
        }
    }
    private void transposition(String inputWord, Set<String> set) {
        char charArray[] = inputWord.toCharArray();
        char tempArray[] = inputWord.toCharArray();
        for(int i = 0; i < inputWord.length() -1; i++){
            tempArray[i] = charArray[i+1];
            tempArray[i+1] = charArray[i];
            String tempString = new String(tempArray);
            set.add(tempString);
            tempArray = inputWord.toCharArray();
        }
    }
}
