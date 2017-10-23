
public class RedBlack {
	static Node root= null;
	public static void main(String[] args) {
		insert(3);
		insert(4);
		insert(5);
		System.out.println("");
		inorder(root);
		insert(6);
		insert(7);
		insert(8);

		System.out.println("");
		inorder(root);
		insert(9);
		insert(10);
		insert(11);

		System.out.println("");
		inorder(root);
		insert(12);
		insert(2);
		insert(1);

		System.out.println("");
		inorder(root);
		
	}
	
	static void insert(int val) {
		insert(root, val);
	}
	static void inorder(Node t) {
		if(t == null)
			return;
		
		inorder(t.left);
		String color = (t.isred== true)?"Red":"Black";
		System.out.println("Val: "+t.val+" ||Color: "+color+"|| Black-Height: "+t.ht);
		inorder(t.right);
		
	}
	
	static void insert(Node tmp,int val) {
		Node t = tmp;
		if(tmp == null) {
			root = new Node(val);
			root.parent = null;
			root.isred = false;
			root.ht = 1;
		}
		else {
			Node parent=null;
			while(t != null) {
				parent = t;
				if(val <= t.val) {
					t = t.left;
				}
				else {
					t = t.right;
				}
			}
		
			t = new Node(val);
			if(parent.val < val)
				parent.right = t;
			else
				parent.left = t;
			t.parent = parent;
			t.isred = true;
			t.ht = 0;
			TreeAdjustment(t);
			root.isred = false;
			root.ht = height(root);
		}
	}
	
	static Node DetermineUncle(Node t) {
		if(t.parent == null || t.parent.parent ==  null)
			return null;
		else if(t.parent == t.parent.parent.left) {
			return t.parent.parent.right;
		}
		else {
			return t.parent.parent.left;
		}
	}
	
	
	static void TreeAdjustment(Node t) {
		if(t == null) {
			return;
		}
		else if(t == root) {
			t.ht = height(t);
			return;
		}
		else if(t.parent.isred == false) {
			t.parent.ht= height(t.parent);
			
		}
		else {
			Node uncle = DetermineUncle(t);
			if(uncle!= null && uncle.isred==true) {
				t.parent.isred = false;
				uncle.isred = false;
				uncle.parent.isred = true;
				uncle.ht = height(uncle);
				t.parent.ht = height(t.parent);
				
			}
			else if(uncle == null || uncle.isred == false) {

				t = DoRotations(t);
			}
			
		}
		if(t.parent!=null)
			t = t.parent.parent;
		else
			t = null;
		TreeAdjustment(t);
	}
	
	static Node DoRotations(Node t) {
		Node tmp=null;
		if(t.parent == null || t.parent.parent == null) {
			return t;
		}
		if(t.parent == t.parent.parent.left) {
			if(t.parent.left == t) {
				tmp = zig_zig_left(t);
			}
			else {
				tmp = zig_zag_left(t);
			}
		}
		else if(t.parent == t.parent.parent.right){
			if(t.parent.right == t) {
				tmp = zig_zig_right(t);
			}
			else {
				tmp = zig_zag_right(t);
			}
		}
		
		return tmp;
	}
	
	static Node zig_zag_right(Node t) {
		Node x = t;
		Node y = t.parent;
		Node z = t.parent.parent;
		
		y.left = x.right;
		if(x.right != null)
		x.right.parent = y;
		z.right = x.left;
		if(x.left != null)
		x.left.parent = z;
		
		x.left = z;
		x.right = y;
		x.parent = z.parent;
		y.parent = x;
		z.parent = x;
		
		z.isred = true;
		x.isred = false;
	
		z.ht = height(z);
		y.ht = height(y);
		x.ht = height(x);
		
		if(z == root)
			root = x;
		if(x!=root)
		x.parent.right = x;
		return x;
	
	}
	
	static Node zig_zig_right(Node t) {
		Node x = t;
		Node y = t.parent;
		Node z = t.parent.parent;
		
		z.right = y.left;
		if(y.left != null)
		y.left.parent = z;
		y.left = z;
		
		y.parent = z.parent;
		z.parent = y;
		
		
		z.isred = true;
		y.isred = false;
		
		
		x.ht = height(x);
		z.ht = height(z);
		y.ht = height(y);
		

		if(z == root)
			root = y;
		if(y!=root)
		y.parent.right = y;
		return y;
	
	}
	
	static Node zig_zag_left(Node t) {
		Node x = t;
		Node y = t.parent;
		Node z = t.parent.parent;
		
		y.right = x.left;
		if(x.left != null)
		x.left.parent = y;
		z.left = x.right;
		if(x.right != null)
		x.right.parent = z;
		
		x.left = y;
		x.right = z;
		x.parent = z.parent;
		y.parent = x;
		z.parent = x;
		
		z.isred = true;
		x.isred = false;
	
		z.ht = height(z);
		y.ht = height(y);
		x.ht = height(x);
		

		if(z == root)
			root = x;
		if(x!= root)
		x.parent.left = x;
		return x;
	}
	
	static Node zig_zig_left(Node t) {
		Node x = t;
		Node y = t.parent;
		Node z = t.parent.parent;
		
		z.left = y.right;
		if(y.right != null)
		y.right.parent = z;
		y.right = z;
		y.parent = z.parent;
		z.parent = y;
		
		
		z.isred = true;
		y.isred = false;
		
		
		x.ht = height(x);
		z.ht = height(z);
		y.ht = height(y);
		

		if(z == root)
			root = y;
		if(y!= root)
		y.parent.left = y;
		return y;
	}
	
	static int max(int a, int b) {
		return (a>b)?a:b;
	}
	
	static int height(Node t) {
		int ht=0;
		if(t==null)
			return 0;
		else if(t.isred== true){
			ht = max(height(t.left),height(t.right));
			
		}
		else if(t.isred == false) {
			ht =  max(height(t.left),height(t.right)) + 1;
		}
		return ht;
	}
}
