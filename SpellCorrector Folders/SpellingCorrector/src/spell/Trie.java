package spell;

import java.util.TreeSet;
public class Trie implements ITrie {


    private Node root;
    private int wordCount;
    private int nodeCount;
    private TreeSet<String> tSet;
//    Constructor for Trie class
    public Trie(){
        tSet = new TreeSet<String>();
        root = new Node();
        wordCount = 0;
        nodeCount = 1;

    }



//    Do i need to @Override with add find and get?

    @Override
    public void add(String word) {

//        System.out.println("the word is:" + word);
        Node tempNode = root;
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            if(tempNode.getChild(c) == null){
                tempNode.setChild(c);
                nodeCount++;
            }
            // no else statement here you always want this to execute
            tempNode = tempNode.getChild(c);
//            System.out.println("Char is: " + c);
        }
        if(tempNode.getValue() == 0){
            wordCount++;
        }
        tempNode.incrementValue();
//        System.out.println("WordCount : " + wordCount +"\nNode Count: " + nodeCount);
    }

    @Override
    public INode find(String word) {
        Node tempNode = root;
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
//            System.out.println("C is: " + c);
            if(tempNode.getChild(c) == null){
                return null;
            }
            //if(tempNode.getChild(c) != null){
            tempNode = tempNode.getChild(c);
            //}
        }
        if(tempNode.getValue() >= 1){
            return tempNode;
        }
        else{
            return null;
        }
    }

    @Override
    public int getWordCount() {

        return wordCount;
    }

    @Override
    public int getNodeCount() {

        return nodeCount;
    }

    public Node getRoot() {
        return root;
    }

    @Override
    public int hashCode() {
        int notNullIndex = 0;
        for (int i = 0; i < root.children.length; i++){
            if (root.getChildren()[i] != null){
                notNullIndex = notNullIndex + i;
            }
        }
        return notNullIndex * wordCount * nodeCount;

    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if (obj.getClass() != this.getClass()){
            return false;
        }
        Trie t = (Trie) obj; // cast obj to a trie

        if(t.wordCount != this.wordCount){
            return false;
        }
        if(t.nodeCount != this.nodeCount){
            return false;
        }
//       recusivly call the equals Helper
        return equals_Helper(this.root, t.root);
    }


    private boolean equals_Helper(Node n1, Node n2){
        if(n1 == null || n2 == null){
            return true;
        }
        else if(n1.getValue() != n2.getValue()){
            return false;
        }
        for(char c = 'a'; c<'z';c++){
            if(!equals_Helper(n1.getChild(c),n2.getChild(c))){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder currentWord = new StringBuilder();
        StringBuilder output = new StringBuilder();

        toString_Helper(root,currentWord,output);

        return output.toString();
    }

    private void toString_Helper(Node n, StringBuilder currentWord, StringBuilder output){
        if (n.getValue() > 0){
            //append nodes word to output
            output.append(currentWord.toString());
            output.append("\n");
        }
        for (int i = 0; i < n.children.length; i++){
            Node child = n.getChildren()[i];
            if (child != null){
                char childLetter = (char) ('a' + i);
                currentWord.append(childLetter);

                toString_Helper(child,currentWord,output);

                currentWord.deleteCharAt(currentWord.length() -1);
            }
        }
    }



}
