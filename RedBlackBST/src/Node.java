
public class Node {

	public int data;
	public NodeColor color = NodeColor.RED;
	public Node leftChild;
	public Node rightChild;
	public Node parent;
	
	public Node (int data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return ""+this.data;
	}
	
	
}
