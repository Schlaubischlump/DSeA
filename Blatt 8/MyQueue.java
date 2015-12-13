import java.util.ArrayList;



/*Aufgabe a) Wenn n Elemente in die queue eingefügt werden, ist die Warteschlange voll.
 * Der Head zeig auf das erste Element (Stelle 0), und Tail zeig auf das letzte Element (Stelle n-1)
 * Wird ein Element entfernt, wird die erste Stelle der Warteschlange wieder frei, 
 * Head zeigt auf 1, tail auf n-1.
 * Wird nun ein weiteres Element hinzugefügt, wird dieses an die erste Stelle des Arrays eingefügt 
 * und der Tail wird auf die erste Stelle gezeigt(tail = (tail + 1) % n)
*/
public class MyQueue<E> {

	int head = 0;
	int tail = 0;
	int size = 0;
	int n = 4;
	ArrayList<E> queue = new ArrayList<E>(n);
	//E[] queue;
	
	
	public boolean push(E e){
		if(size < n){
			queue.add(tail, e);
			//queue[tail+1] = e;
			tail = (tail +1) % n;
			size++;
			return true; 
		}
		else
			return false;
	}
	public E pop(){
		if(size >0){
			head = (head + 1) % n;
			size--;
			return queue.get(head);	
		}else
			return null;
	}
	public int size(){
		return size;
	}
	public void print(){
		for(int i = 0; i < size; i++)
			System.out.println(queue.get((i + head) % n ));
	}
	
	public static void main(String[] args){
		
		MyQueue<Integer> b =new MyQueue<Integer>();
		b.push(4);
		// 4
		b.push(5);
		// 4 5
		b.push(0);
		// 4 5 0
		b.push(3);
		// 4 5 0 3
		b.pop();
		//   5 0 3
		b.pop();
		//     0 3
		b.push(2);
		// 2   0 3
		b.push(10);
		// 2 10 0 3
		
		b.print();
		
	}
	
}
