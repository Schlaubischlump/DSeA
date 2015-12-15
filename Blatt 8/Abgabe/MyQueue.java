class MyQueue<E> {
	
	private E[] queue;
	private int head;
	private int tail;
	int size;
	//private boolean empty;
	
	@SuppressWarnings("unchecked")
	public MyQueue ( int size) {
		queue =(E[])new Object[size];
		this.head = 0;
		this.tail = 0;
		this.size = 0;
		//this.empty = true;
	}
	
	public boolean push(E e) {
		
		if (this.size()== queue.length)
			return false;
		else if (this.size() != 0)
			tail = (tail+1)%queue.length;
		if (size == 0) {
			//empty = false;
			head = 0;
			tail = 0;
		}
		queue[tail] = e;
		size++;
		return true;
	}
	
	public E pop() {
		if (this.size() == 0)
		//if (empty)
			return null;
		//if (head == tail)
			//empty = true;
		E temp = queue[head];
		head = (head+1)%queue.length;
		size--;
		return temp;
		
	}
	
	public int size() {
		//if (empty)
			//return 0;
		//if (tail > head)
			//return tail-head+1;
		//if (head > tail)
			//return queue.length-head+tail+1;
		//return 1;
		//return (tail+head)%(queue.length+1);
		return size;
	}
	
	public void print() {
		for (int i = 0; i <this.size(); i++)
			System.out.print(queue[(i+head)%queue.length]+"  ");
		System.out.println();
	}
	
	public static void main (String... args) {
		MyQueue<Integer> test = new MyQueue<Integer>(4);

		test.push(4);
		test.push(5);
		test.push(0);
		test.push(3);
		test.print();

		test.pop();
		test.pop();
		test.pop();
		test.pop();
		test.print();
		
		test.push(2);
		test.push(10);

		test.print();

	}
}