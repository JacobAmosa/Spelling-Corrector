package spell;

public class Node implements INode{
    Node[] nodes = new Node[26];
    int count = 0;

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public Node[] getChildren() {
        return nodes;
    }
}
