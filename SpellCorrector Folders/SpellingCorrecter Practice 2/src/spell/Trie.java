package spell;

public class Trie implements ITrie{

    Node root = new Node();
    int nodeCount = 1;
    int wordCount;

    @Override
    public void add(String word) {
        Node tempNode = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (tempNode.getNode(c) == null){
                tempNode.setNode(c);
                nodeCount++;
            }
            tempNode = tempNode.getNode(c);
        }
        if(tempNode.getValue() == 0){
            wordCount++;
        }
        tempNode.incrementValue();
    }


    @Override
    public Node find(String word) {
        Node tempNode = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(tempNode.getNode(c) == null){
                return null;
            }
            tempNode = tempNode.getNode(c);

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
        int tempInt = 0;
        Node tempNode = root;
        for(int i= 0; i < tempNode.getChildren().length; i++){
            if(tempNode.getChildren()[i] != null){
                tempInt = tempInt + i;
            }
        }
        return tempInt * wordCount * nodeCount;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(this == null){
            return false;
        }
        if (obj.getClass() != this.getClass()){
            return false;
        }
        if(obj == this){
            return true;
        }
        Trie t = (Trie)obj;
        if(t.getWordCount() != this.getWordCount()){
            return false;
        }
        if(t.getNodeCount() != this.getNodeCount()){
            return false;
        }
        return equals_helper(t.root, this.root);
    }
    public boolean equals_helper(Node n1, Node n2){
        return false;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        StringBuilder curWord = new StringBuilder();

        toString_Helper(root, curWord, output);
        return output.toString();
    }
    public void toString_Helper(Node n, StringBuilder curWord, StringBuilder output){
        if(n.getValue() > 0){
            output.append(curWord);
            output.append("\n");
        }
        for (int i = 0; i < n.children.length; i++) {
            Node child = n.getChildren()[i];
            if(child != null){
                char tempChar = (char)('a'+i);
                curWord.append(tempChar);
                toString_Helper(child, curWord, output);
                curWord.deleteCharAt(curWord.length()-1);
            }
        }
    }

}
