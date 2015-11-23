// Abstract class, there is no need for customization
abstract class AbstractNode <T extends Comparable<T>> {
	
	// all the public things! Who cares about getters and setters?
  public T key;                       // any type that is comparable	
	public AbstractNode<T> left, right; // pointer onto children
	
	public AbstractNode(T key){
		this.key = key;
	}
	
	public abstract boolean insert (T key); // true if successful else false
	public abstract boolean isIn (T key);   // true if in tree else false
	
	// all the traversal methods! :D |--> :(
	public abstract void preorder();
	public abstract void inorder();
	public abstract void postorder();
	
	// recursive depth method
	public abstract int maxDepth();	
}
