package spell;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

public class SpellCorrector implements ISpellCorrector {


    //For the test cases
    public Trie trie = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner scan  = new Scanner( new File(dictionaryFileName));
        while (scan.hasNext()){
            trie.add(scan.next().toLowerCase());
        }
        scan.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        Set<String> setEdit1 = new TreeSet<>();
        Set<String> setEdit2 = new TreeSet<>();
        String sugWord = null;
        String sugWord2 = null;
        inputWord = inputWord.toLowerCase();
        if(trie.find(inputWord) != null){
            return inputWord;
        }
        else {
            deletion(inputWord,setEdit1);
            insertion(inputWord,setEdit1);
            alteration(inputWord,setEdit1);
            transposition(inputWord,setEdit1);


            sugWord = suggestingWord(setEdit1);

            if(sugWord == null){ // if there still isnt a suggested word after one time through then go again
                for(String s: setEdit1){
//                    System.out.println("s is: " + s);

                    deletion(s,setEdit2);
                    insertion(s,setEdit2);
                    alteration(s,setEdit2);
                    transposition(s,setEdit2);
                    sugWord = suggestingWord(setEdit2);

                }
            }

//            System.out.println("sugg word is: " +sugWord);
            return sugWord;
        }

    }

    public String suggestingWord(Set<String> set){
        String sugg = null;
        INode tempNode;
        int m = 0;
        for (String s: set){
            tempNode = trie.find(s);
            if(tempNode != null){
                if (tempNode.getValue() > m){
                    m = tempNode.getValue();
                    sugg = s;
                }
            }
        }
        return sugg;
    }
//
    public void deletion(String word, Set<String> set ){
//        might need to use string builder
        for (int i = 0; i < word.length(); i++ ){
            StringBuilder tempDelete = new StringBuilder(word);
            tempDelete.deleteCharAt(i);
            set.add(tempDelete.toString());
        }
    }
    public void insertion(String word, Set<String> set ){
        for(int i = 0; i < word.length() + 1; i++){
            for(char c = 'a'; c <= 'z'; c++){
                StringBuilder tempString = new StringBuilder(word);
                tempString.insert(i,c);
                set.add(tempString.toString());
            }
        }
    }
    public void alteration(String word, Set<String> set ) {
        char tempA[] = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                tempA[i] = c;
                String tempString = new String(tempA);
                set.add(tempString);
            }
            tempA = word.toCharArray();
        }
    }
    public void transposition(String word, Set<String> set ){
        char tempA[] = word.toCharArray();
        char newTempA[] = word.toCharArray();
        for (int i = 0; i < word.length() -1; i++) {
            tempA[i] = newTempA[i +1];
            tempA[i+1] = newTempA[i];
            String tempString = new String(tempA);
            set.add(tempString);
            tempA = word.toCharArray();
        }


    }



}
