class Node<T extends Comparable<T>> extends AbstractNode<T>{ 

  public Node(T key){
    super(key);
  }

  public boolean insert (T key)
  {
	  int direction = key.compareTo(this.key);		
	  // use direction to choose if left or right, zero means equality
	  if (direction < 0)  {
		  	if (this.left != null)
		  		this.left.insert(key);
		  	else {
		  		this.left = new Node<T>(key);
		  		return true;
		  	}
	  } else {
		  if (this.right != null)
			  this.right.insert(key);
		  else {
			  this.right = new Node<T>(key);
			  return true;
		  }
	  }
	  // should be more sophisticated than xkcd random dice roll https://xkcd.com/221/
	  return false;
  }

  public boolean isIn (T key){
    int direction = key.compareTo(this.key);
    // use direction to choose if left or right, zero means equality

    if (direction == 0) {
    	return true;
    } else if (direction < 0 && this.left != null)  {
    	return this.left.isIn(key);
	 } else if (this.right != null){
		return this.right.isIn(key);
	 }
    
    // should be more sophisticated than xkcd random dice roll https://xkcd.com/221/
    return false;
  }

  public void preorder() {
	  
  }

  public void inorder() {
	  
  }

  public void postorder(){}


  public int maxDepth(){

    // Should be a bit more sophisticated
    return 0;
  }		
}