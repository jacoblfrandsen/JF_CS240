package spell;

public class Node implements INode{

    public Node[] children = new Node[26];
    int countOnNode = 0;


    public void setChild(char c){
        children[c-'a'] = new Node();
    }
    public Node getChild(char c){
        // get the node at the spot c - 'a
        return children[c-'a'];
    }

    @Override
    public int getValue() {
        return countOnNode;
    }

    @Override
    public void incrementValue() {
        countOnNode++;
    }

    @Override
    public Node[] getChildren() {

        return children;
    }
}
