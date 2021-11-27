
public class RedBlackTree implements Tree {

	
	//Root must always be black.
	//Every node starts red when inserted
	//Parent and children of red node should be black nodes
	
	//Case 1: Uncle is red, swap colours root becomes red, parent and uncle nodes black(parent is left child)
	//Case 2: Uncle is black and node x is a right child, left rotate node x's parent
	//Case 3: Uncle is black and node x is a left child, right rotate node's grandparent
	//Case 4: Uncle is red, but parent node is a right child. Same as case 1.
			
	
	private Node root;
	
	@Override
	public void traverse() {
		
		if(root != null) {
			inOrderTraversal(root);
		}
		
		
	}

	private void inOrderTraversal(Node node) {
		
		if(node.leftChild != null) {
			inOrderTraversal(node.leftChild);
		}
		
		System.out.println(node +  " - ");
		
		if(node.rightChild != null) {
			inOrderTraversal(node.rightChild);
		}
		
		
	}
	
	private void rightRotate(Node node) {
		
		System.out.println("Rotating to the right on Node: " + node.data);
		
		Node tempLeftNode = node.leftChild;
		node.leftChild = tempLeftNode.rightChild;
		
		if(node.leftChild != null) {
			node.leftChild.parent = node;
		}
		
		tempLeftNode.parent = node.parent;
		
		if(tempLeftNode.parent == null) {
			root = tempLeftNode;
		} else if(node == node.parent.leftChild) {
			node.parent.leftChild = tempLeftNode;
		} else {
			node.parent.rightChild = tempLeftNode;
		}
		
		tempLeftNode.rightChild = node;
		node.parent = tempLeftNode;
	}

	
	private void leftRotate(Node node) {
		
		System.out.println("Rotating to the left on Node: " + node.data);
		
		Node tempRightNode = node.rightChild;
		node.rightChild = tempRightNode.leftChild;
		
		if(node.rightChild != null) {
			node.rightChild.parent = node;
		}
		
		tempRightNode.parent = node.parent;
		
		if(tempRightNode.parent == null) {
			root = tempRightNode;
		} else if(node == node.parent.rightChild) {
			node.parent.rightChild = tempRightNode;
		} else {
			node.parent.rightChild = tempRightNode;
		}
		
		tempRightNode.leftChild = node;
		node.parent = tempRightNode;
	}

	@Override
	public void insert(int data) {
		
		Node node = new Node(data);
		
		root = insertIntoTree(root, node);
		
		fixViolations(node);
		
		
	}

	private void fixViolations(Node node) {
		
		Node parentNode = null;
		Node grandParentNode = null;
		
		while(node != root && node.color == NodeColor.RED && node.parent.color == NodeColor.RED) {
			
			parentNode = node.parent;
			grandParentNode = parentNode.parent;
			
			if(parentNode == grandParentNode.leftChild) {
				
				Node uncle = grandParentNode.rightChild;
				
				//If uncle is red violates case 1 & case 4
				if(uncle != null && uncle.color == NodeColor.RED) {
					grandParentNode.color = NodeColor.RED;
					parentNode.color = NodeColor.BLACK;
					uncle.color = NodeColor.BLACK;
					node = grandParentNode;
					
				} else {
					
					//Violates case 2
					if(node == parentNode.rightChild) {
						
						leftRotate(parentNode);
						node = parentNode;
						parentNode = node.parent;
						
					}
					
					//Violates case 3
					rightRotate(grandParentNode);
					
					NodeColor tempColor = parentNode.color;
					parentNode.color = grandParentNode.color;
					grandParentNode.color = tempColor;
					
					node = parentNode;
					
				}
				
			//Same but asymmetric operations if parent node is right child.
			} else {
				
				Node uncle = grandParentNode.leftChild;
				
				//If uncle is red violates case 1 & case 4
				if(uncle != null && uncle.color == NodeColor.RED) {
					
					grandParentNode.color = NodeColor.RED;
					parentNode.color = NodeColor.BLACK;
					uncle.color = NodeColor.BLACK;
					node = grandParentNode;
					
					
				} else {
					
					//Violates case 2
					if(node == parentNode.leftChild) {
						
						rightRotate(parentNode);
						node = parentNode;
						parentNode = node.parent;
						
					}
					
					//Violates case 3
					leftRotate(grandParentNode);
					

					NodeColor tempColor = parentNode.color;
					parentNode.color = grandParentNode.color;
					grandParentNode.color = tempColor;
					
					node = parentNode;
					
					
				}
				
			}
			
			
		}
		
		if(root.color == NodeColor.RED) {
			root.color = NodeColor.BLACK;
		}
		
	}

	private Node insertIntoTree(Node root, Node node) {
		
		if(root == null) {
			return node;
		}
		
		if(node.data < root.data) {
			
			root.leftChild = insertIntoTree(root.leftChild, node);
			root.leftChild.parent = root;
			
		} else if(node.data > root.data) {
			root.rightChild = insertIntoTree(root.rightChild, node);
			root.rightChild.parent = root;
		}
		
		
		
		return root;
	}
	
}
