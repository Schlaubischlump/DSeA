class MyQueue<E> {
	
	private final E[] queue;
	private int head;
	private int tail;
	private int size;
	//private boolean empty;
	
	@SuppressWarnings("unchecked")
	public MyQueue ( int size) {
		queue =(E[])new Object[size];
		this.head = -1;
		this.tail = -1;
		this.size = 0;
		//this.empty = true;
	}
	
	public boolean push(E e) {
		
		if ((tail+1)%queue.length == head)
			return false;
		else if (this.size() == 0) {
			head++;
			tail++;
		}
		else {
			tail = (tail+1)%queue.length;
		}
		queue[tail] = e;
		size++;
		return true;
	}
	
	public E pop() {
		E value = null;
		if (head == tail) {
			value = queue[head];
			tail = -1;
			head = -1;
		} else if (size != 0) {
			value = queue[head];
			head = (head+1)%queue.length;
		}
		size--;
		return value;
		
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