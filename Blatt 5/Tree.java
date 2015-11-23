// Wrapper class, there is no need for customization
class Tree<T extends Comparable<T>> extends AbstractTree<T> {

	Node<T> currentNode = (Node<T>) root;
	
	public Tree(T key) {
		root = new Node<T>(key);
		this.currentNode = (Node<T>) root;
	}

	public boolean insert(T key) {
		if(this.currentNode.key.compareTo(key) == 0)
			return root.insert(key);
		else if(this.currentNode.key.compareTo(key) < 0)
			if(this.currentNode.right == null){
				this.currentNode.right = new Node<T>(key);
				return root.insert(key);
			}
			else this.currentNode = (Node<T>) this.currentNode.left;
		else if(this.currentNode.key.compareTo(key) > 0)
			if(this.currentNode.left == null){
				this.currentNode.left = new Node<T>(key);
				return root.insert(key);
			}
			else this.currentNode = (Node<T>) this.currentNode.left;
			
			insert(key);
			this.currentNode = (Node<T>) root;
		return root.insert(key);
	}

	public boolean isIn(T key) {
		return root.isIn(key);
	}

	// all the traversal methods! :D |--> :(
	public void preorder() {
		root.preorder();
	}

	public void inorder() {
		root.inorder();
	}

	public void postorder() {
		root.postorder();
	}

	// recursive depth method
	public int maxDepth() {
		return root.maxDepth();
	}

	public static void main(String args[]) {

		// new Integer tree (put in 2 as root key)
		Tree<Integer> T = new Tree<Integer>(2);
		// put in 3 (true)
		System.out.println(T.insert(3));
		// put in 3 (false - keys are unique!)
		System.out.println(T.insert(3));
		// 2 in tree (true)
		System.out.println(T.isIn(2));

	}
}