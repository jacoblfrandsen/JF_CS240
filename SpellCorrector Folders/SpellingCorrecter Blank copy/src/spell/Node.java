package spell;

public class Node implements INode{

    public Node[] children = new Node[26];
    int frequencyOfWord = 0;

    public Node getChild(char c){
        return children[c-'a'];
    }

    public void setChildren(char c){
        children[c - 'a'] = new Node();
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
