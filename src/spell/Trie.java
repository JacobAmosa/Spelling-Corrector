package spell;

public class Trie implements ITrie{
    Node root = new Node();
    int wordCount = 0;
    int nodeCount = 1;

    @Override
    public void add(String word) {
        Node n = root;
        //Creating the Trie.
        for (int i = 0; i < word.length(); i++){
            if (n.nodes[word.charAt(i) - 'a'] == null){
                n.nodes[word.charAt(i) - 'a'] = new Node();
                n = n.nodes[word.charAt(i) - 'a'];
                nodeCount++;
            }
            else{
                n = n.nodes[word.charAt(i) - 'a'];
            }
        }
        //Checking if the word already exists.
        if (n.count < 1){
            wordCount++;
        }
        n.incrementValue();
        //System.out.println(word);
    }

    @Override
    public INode find(String word) {
        Node n = root;
        for (int i = 0; i < word.length(); ++i){
            //If the letter is null, it doesn't exist in the dictionary.
            if (n.nodes[word.charAt(i) - 'a'] == null){
                return null;
            }
            else {
                n = n.nodes[word.charAt(i) - 'a'];
                //If we reach the end of the word,and the word count exists, we return the object.
                if ((i == word.length() - 1) && (n.count >= 1)){
                    return n;
                }
            }
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
    public String toString(){
        Node n = root;
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        toStringTraversal(n, sb1, sb2);
        return sb2.toString();
    }

    @Override
    public void toStringTraversal(Node node, StringBuilder word, StringBuilder words){
        //If a word exists, add the word and a newline to WORDS.
        if (node.count > 0){
            words.append(word.toString());
            words.append('\n');
        }
        //Loop through all the child nodes. If node exists, add to WORD and recall toStringTraversal().
        for (int i = 0; i < node.getChildren().length; i++){
            if (node.getChildren()[i] != null){
                word.append((char)(i + 'a'));
                toStringTraversal(node.getChildren()[i], word, words);
            }
        }
        //Keeps deleting the last character of word.
        if (word.length() != 0){
            word.delete(word.length() - 1, word.length());
        }
    }

    @Override
    public int hashCode(){
        int index = 0;
        //Finding the first null node of the root for a unique hash value.
        for (int i = 0; i < root.getChildren().length; i++) {
            if (root.getChildren()[i] != null) {
                index = i;
                break;
            }
        }
        return nodeCount * wordCount * index * 31;
    }

    @Override
    public boolean equals(Object o){
        if (o == null || !(o instanceof Trie)){
            return false;
        }
        Trie obj = (Trie)o;
        return equalsTraversal(this.root , obj.root);
    }

    public boolean equalsTraversal(Node node1, Node node2) {
        //The order of conditional statements is important!
        for (int i = 0; i < node1.getChildren().length; i++) {
            if ((node1.getChildren()[i] == null) && (node2.getChildren()[i] == null)) {
                continue;
            }
            if ((node1.getChildren()[i] == null) && (node2.getChildren()[i] != null)) {
                return false;
            }else if ((node1.getChildren()[i] != null) && (node2.getChildren()[i] == null)) {
                return false;
            }else if (node1.getChildren()[i].getValue() == node2.getChildren()[i].getValue()) {
                if (!equalsTraversal(node1.getChildren()[i], node2.getChildren()[i])){
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
