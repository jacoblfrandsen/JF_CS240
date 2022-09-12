package spell;

public class Node implements INode{

    //constructor
    Node[] children = new Node[26];
    int countOnNode = 0;


    public void setChild(char c){
        children [c - 'a'] = new Node(); //create new node on index c - a
    }

    public Node getChild(char c){ // get the node on c - a
        return children[c - 'a'];
    }

    @Override
    public int getValue() { //return the value on the node
        return countOnNode;
    }

    @Override
    public void incrementValue() { // we ended on this node meaning that was a new word
        countOnNode++;
    }

    @Override
    public Node[] getChildren() {
        return children;
    }
}
