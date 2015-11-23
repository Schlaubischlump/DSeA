// Wrapper class, there is no need for customization
class Tree<T extends Comparable<T>> extends AbstractTree<T> {
	
	public Tree(T key) {
		root = new Node<T>(key);
	}

	public boolean insert(T key) {
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
		
		System.out.println("---------Preorder---------------");
		T.preorder();
		System.out.println("---------Inorder----------------");
		T.inorder();
		System.out.println("---------Postorder--------------");
		T.postorder();
	}
}