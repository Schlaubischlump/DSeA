class MyQueue<E> {
	
	E[] queue;
	int head;
	int tail;
	
	@SuppressWarnings("unchecked")
	public MyQueue ( int size) {
		queue =(E[])new Object[size];
		this.head = 0;
		this.tail = 0;
	}
	
	public boolean push(E e) {
		
		if (tail == (head-1)%queue.length)
			return false;
		else if (head == tail)
			head = 0;
		tail = (tail+1)%queue.length;
		queue[tail] = e;
		return true;
	}
	
	public E pop() {
		if (head == tail)
			return null;
		head = (head-1)%queue.length;
		return queue[head];
		
	}
	
	public int size() {
		if (tail == head)
			return 0;
		return (tail-head+queue.length)%queue.length+1;
	}
	
	public void print() {
		for (int i = head; i <this.size(); i++)
			System.out.print(queue[i]+"  ");
		System.out.println();
	}
	
	public static void main (String... args) {
		MyQueue<Integer> test = new MyQueue<Integer>(4);

		System.out.println(test.size());
		test.push(5);
		System.out.println(test.size());
		test.push(5);
		System.out.println(test.size());
		test.push(0);
		System.out.println(test.size());
		test.push(3);
		System.out.println(test.size());
		test.print();

		System.out.println(test.pop());
		System.out.println(test.pop());
		
		test.push(2);
		test.push(10);
		
		test.print();
	}
}