package spell;

public class Trie implements  ITrie{

    //constructor
    Node root =  new Node();
    int nodeCount = 1; // it is equal to one because that we created a new node above
    int wordCount = 0;

    @Override
    public void add(String word) {
        //this needs to go first
        //add every letter to a node as you go through the letters in the words
        Node tempNode = root;
        for(int i = 0; i < word.length(); i++){
            //check to see if letter is already there

            char c = word.charAt(i);
            if(tempNode.getChild(c) == null){ // do the comparison == null or not null
                tempNode.setChild(c);
                nodeCount++; //increment number of nodes created because i called set child
            }
            // no else statement here you always want to execute
            tempNode = tempNode.getChild(c); // now if the node isnt null(meaning that the word was already there) we set temp node == new node
        }
        //increment word count you dont increment if the word has already been added
        if(tempNode.getValue() == 0){
            wordCount++;
        }
        tempNode.incrementValue(); // you need to increment the value on the node to show that a word is there

    }

    @Override
    public INode find(String word) { //we are trying to find if the word is in the Trie
        Node tempNode = root;
        for (int i = 0; i < word.length(); i++){
            char tempChar = word.charAt(i);
            if(tempNode.getChild(tempChar) == null){
                return null;
            }
            tempNode = tempNode.getChild(tempChar);
        }
        if(tempNode.getValue() >= 1){
            return tempNode;
        }
        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public int hashCode() {
        int notNullCount = 0;
        for (int i = 0; i < root.children.length; i++){
            if(root.children[i] != null){
                notNullCount =  notNullCount + i; //add each index where it is not null
            }
        }
        return notNullCount * getNodeCount() * getWordCount();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if (obj.getClass() != this.getClass() ){
            return false;
        }

        Trie t = (Trie) obj; //cast to type Trie

        if(this.getNodeCount() != t.getNodeCount()){
            return false;
        }
        if (this.getWordCount() != t.getWordCount()){
            return false;
        }

        return equals_Helper(this.root, t.root);
    }


    private boolean equals_Helper(Node n1, Node n2){
        if(n1 == null || n2 == null){
            return true;
        }

        if(n1.getValue() != n2.getValue()){
            return false;
        }

        for(char c = 'a'; c < 'z'; c++){
            if(!equals_Helper(n1.getChild(c), n2.getChild(c))){
                return false;
            }

        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();

        toString_Helper(root, curWord, output);
        return output.toString();
    }
    private void toString_Helper(Node n, StringBuilder curWord, StringBuilder output){
        if (n.getValue() >0){
            output.append(curWord.toString());
            output.append("\n");
        }
        for(int i = 0; i < n.children.length; i++){
            Node child = n.getChildren()[i];
            if(child != null){
                char childLetter = (char)('a'+i);
                curWord.append(childLetter);
                toString_Helper(child,curWord,output);
                curWord.deleteCharAt(curWord.length()-1);
            }
        }
    }
}
