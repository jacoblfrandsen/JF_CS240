package spell;

public class Node implements INode{

    public Node[] children = new Node[26];
    int valOnNode; //frequency of word


    public void setNode(char c) {
        children[c-'a'] = new Node();
    }
    public Node getNode(char c){
        return children[c -'a'];
    }

    @Override
    public int getValue() {
        return valOnNode;
    }

    @Override
    public void incrementValue() {
        valOnNode++;
    }

    @Override
    public Node[] getChildren() {
        return children;
    }
}
