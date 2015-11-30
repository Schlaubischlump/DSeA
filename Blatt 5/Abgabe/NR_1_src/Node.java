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
		  		return this.left.insert(key);
		  	else {
		  		this.left = new Node<T>(key);
		  		return true;
		  	}
	  } else if (direction > 0) {
		  if (this.right != null)
			  return this.right.insert(key);
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
	  System.out.println(this.key);
	  
	  // linker Teilbaum 
	  if (this.left != null )
		  this.left.postorder();

	  // rechter Teilbaum
	  if (this.right != null )
		  this.right.postorder();
  }

  public void inorder() {
	// linker Teilbaum 
	  if (this.left != null )
		  this.left.postorder();

	  System.out.println(this.key);
	  
	  // rechter Teilbaum
	  if (this.right != null )
		  this.right.postorder();
  }

  public void postorder() 
  {
	  // linker Teilbaum 
	  if (this.left != null )
		  this.left.postorder();

	  // rechter Teilbaum
	  if (this.right != null )
		  this.right.postorder();

	  System.out.println(this.key);
  }


  public int maxDepth(){
	  
	  if (left == null && right == null)
		  return 1;
	  if (left == null)
		  return right.maxDepth()+1;
	  
	  int leftD = left.maxDepth();
	  
	  if (right == null)
		  return leftD+1;
	 
	  int rightD = right.maxDepth();
	  
	  return leftD>rightD ? leftD+1 : rightD+1 ;
  }		
}