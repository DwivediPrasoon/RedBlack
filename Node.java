
public class Node {
	int val;
	boolean isred = true;
	Node left;
	Node right;
	Node parent;
	int ht=0;
	
	public Node(int val) {
		this.val = val;
	}
}
