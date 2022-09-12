package spell;

public class Node implements INode{

    private int frequencyOfWord = 0;
    public Node[] children = new Node [26];




    public void setChild(char c) {
        children[c-'a'] = new Node(); //creating new Node( with size 26) at the char c - a
    }

    public  Node getChild(char c){

        return children[c-'a'];
    }

    @Override
    public int getValue() {

        return frequencyOfWord;
    }

    @Override
    public void incrementValue() {
        frequencyOfWord++;
    }

    @Override
    public Node[] getChildren() {

        return children;
    }
}
