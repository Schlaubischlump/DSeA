// Abstract class, there is no need for customization
abstract class AbstractTree <T extends Comparable<T>>{
	
	AbstractNode<T> root; // create instance of this in constructor

	public abstract boolean insert (T key); // true if successful else false
	public abstract boolean isIn (T key);   // true if in tree else false
	
	// all the traversal methods! :D |--> :(
	public abstract void preorder();
	public abstract void inorder();
	public abstract void postorder();
	
	// recursive depth method
	public abstract int maxDepth();	
	
}
