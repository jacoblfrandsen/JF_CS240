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
        while (scan.hasNext()){
            trie.add(scan.next().toLowerCase());
        }
        scan.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        Set<String> setEdit1 = new TreeSet<>();
        Set<String> setEdit2 = new TreeSet<>();

        String theWord;
        if(trie.find(inputWord) != null){
            return inputWord;
        }
        else{
            //delete insert alter traspose

            deletion(inputWord, setEdit1);
            insertion(inputWord, setEdit1);
            alteration(inputWord, setEdit1);
            transposition(inputWord, setEdit1);

            theWord = suggestWord(setEdit1);
        }
        if(theWord ==null){
            for(String s: setEdit1){
                deletion(inputWord, setEdit2);
                insertion(inputWord, setEdit2);
                alteration(inputWord, setEdit2);
                transposition(inputWord, setEdit2);
                theWord = suggestWord(setEdit2);
            }
        }
        return theWord;
    }

    public String suggestWord(Set<String> set){
        //for each word in the new set that we pass in check to see if we find it
        INode tempNode;
        String tempString = null;
        int fword = 0;
        for(String s: set){
            tempNode = trie.find(s);
            if(tempNode!=null){
                if(tempNode.getValue() > fword){
                    fword=tempNode.getValue();
                    tempString = s;
                }
            }
        }
        return tempString;
    }

    public void deletion(String inputWord,Set<String> set){
        for (int i = 0; i < inputWord.length(); i++) {
            StringBuilder tempWord = new StringBuilder(inputWord);
            tempWord.deleteCharAt(i);
            set.add(tempWord.toString());
        }
    }
    public void insertion(String inputWord,Set<String> set){

    }
    public void alteration(String inputWord,Set<String> set){

    }
    public void transposition(String inputWord,Set<String> set){

    }
}
