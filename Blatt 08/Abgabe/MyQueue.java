class MyQueue<E> {
	
	private final E[] queue;
	private int head;
	private int tail;
	private int size;
	
	//Erzeuge Objekt
	@SuppressWarnings("unchecked")
	public MyQueue ( int size) {
		queue =(E[])new Object[size];
		this.head = -1;
		this.tail = -1;
		this.size = 0;
	}
	
	
	public boolean push(E e) {
		
		if ((tail+1)%queue.length == head)  //Falls Tail direkt hinter Head, ist die Queue voll
			return false;
		else if (this.size() == 0) {  //wenn leer, wurden die Pointer resetet
			head++;
			tail++;
		}
		else {  //fange von vorne an, wenn hinten angekommen
			tail = (tail+1)%queue.length;
		}
		queue[tail] = e;
		size++;
		return true;
	}
	
	
	public E pop() {
		E value = null;
		//Wenn Head gleich Tail, dann befindet sich nur ein Element in der Queue, 
		//nach dem pop kann diese resetet werden
		if (head == tail) {  
			value = queue[head];
			queue[head] = null;  //Lösche verweise, damit Garbage Collector arbeiten kann
			tail = -1;
			head = -1;
		} else if (size != 0) {  //wenn ein Element vorhanden ist, nimm es und lösch es 
			value = queue[head];
			queue[head] = null;
			head = (head+1)%queue.length;
		}
		size--;
		return value; 
		
	}
	
	public int size() {
		return size;
	}
	
	//Gibt Queue von Head bis Tail aus
	public void print() {
		for (int i = 0; i <this.size(); i++)
			System.out.print(queue[(i+head)%queue.length]+"  ");
		System.out.println();
	}
	
	//Aufgabe 1 c)
	public static void main (String... args) {
		MyQueue<Integer> test = new MyQueue<Integer>(4);

		test.push(4);
		test.push(5);
		test.push(0);
		test.push(3);

		test.pop();
		test.pop();
		
		test.push(2);
		test.push(10);

		test.print();

	}
}