class Node<T extends Comparable<T>> extends AbstractNode<T>{ 

  public Node(T key){
    super(key);
  }

  public boolean insert (T key){

    int direction = key.compareTo(this.key);		
    // use direction to choose if left or right, zero means equality

    // should be more sophisticated than xkcd random dice roll https://xkcd.com/221/
    return false;
  }

  public boolean isIn (T key){

    int direction = key.compareTo(this.key);
    // use direction to choose if left or right, zero means equality

    // should be more sophisticated than xkcd random dice roll https://xkcd.com/221/
    return false;
  }

  public void preorder(){}

  public void inorder(){}

  public void postorder(){}


  public int maxDepth(){

    // Should be a bit more sophisticated
    return 0;
  }		
}