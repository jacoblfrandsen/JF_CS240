package spell;

public class Trie implements  ITrie{

    Node root = new Node();
    int nodeCount = 1;
    int wordCount = 0;


    @Override
    public void add(String word) {
        Node tempNode = root;
        for (int i = 0; i <word.length() ; i++) {
            char c = word.charAt(i);
            if(tempNode.getChild(c) == null){
                tempNode.setChild(c);
                nodeCount++;
            }
            tempNode = tempNode.getChild(c); // when not null get the next node
        }
//        System.out.println("word Added is: " + word);
        if(tempNode.getValue() == 0){
            wordCount++;
        }

        tempNode.incrementValue();
    }

    @Override
    public INode find(String word) {
        Node tempNode = root;
        for (int i = 0; i <word.length() ; i++) {
            char c = word.charAt(i);
            if(tempNode.getChild(c)==null){
                return null;
            }
            if(tempNode.getChild(c)!= null){
                tempNode = tempNode.getChild(c);
            }
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
        for (int i = 0; i < root.children.length; i++) {
            if(root.getChildren()[i] != null){
                tempInt = tempInt + i;
            }
        }
        return tempInt * wordCount * nodeCount ;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if (obj.getClass() != this.getClass()){
            return false;
        }
        Trie t = (Trie) obj;
        if(t.wordCount != this.wordCount){
            return false;
        }
        if (t.nodeCount != this.nodeCount){
            return false;
        }
        // return equals_Helper(t.root, this.root);
        return false;
    }

    public boolean equals_Helper(Node n1, Node n2) {
        if (n1 == null || n2 == null) {
            return true;
        }
        if(n1.getValue() != n2.getValue()){
            return false;
        }
        for(char c = 'a'; c < 'z'; c++){
            if(!equals_Helper(n1.getChild(c), n2.getChild(c))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toString_Helper(root,curWord,output);
//        System.out.println("string is: "+ output.toString());
        return output.toString();
    }
    public void toString_Helper(Node n, StringBuilder curWord, StringBuilder output){
        if(n.getValue()> 0){
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
