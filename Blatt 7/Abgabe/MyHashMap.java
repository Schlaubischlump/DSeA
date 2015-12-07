import java.util.LinkedList;
import java.util.Random;

class Point implements Comparable<Point> {
  public int x,y;
  Random random = new Random();

  Point(int a,int b) { 
    x = a;
    y = b;
  }
  
  long nextLong(Random rndm, long n) {
	  // error checking and 2^x checking removed because not necessary.
	  // modified version of nextInt(int range)
	  long bits, val;
	  do {
		  bits = (rndm.nextLong() << 1) >>> 1;
		  val = bits % n;
	  } while (bits-val+(n-1) < 0L);
	  return val;
  }

  @Override
  public int hashCode() {
	// randomisiere mich
    long z = x << 16 + y;
    long p = 2147483647L; // Primzahl < 2^31
    long a = nextLong(random,p-1)+1; // zufällig 0 < a < p
    long b = nextLong(random,p); // zufällig 0 <= b < p
    long m = 65536;
    System.out.println("Koordinaten: "+x+" "+y+" hashcode: "+(((a * z + b) % p)%m)+" a = "+a+" b = "+b);
    
    return (int)(((a * z + b) % p)%m);
  }

  @Override
  public boolean equals(Object obj) {
    Point p = (Point) obj;
    if (x == p.x && y == p.y) return true;
    return false;     
  }

  @Override
  public int compareTo(Point p) {
    if (x < p.x) return -1;
    if (x > p.x) return  1;
    if (y < p.y) return -1;
    if (y > p.y) return  1;
    return 0;
  }
};


// Hilfklasse
class Entry<K, V> {
	private K key;
	private V value;
	
	public Entry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K key()
	{
		return this.key;
	}
	
	public V value()
	{
		return this.value;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
}


/*
 * Da der Zugriff auf einen Index in einem Array in O(1) funktioniert, kann eine LinkedList in O(1) erhalten werden.
 * Das Iteririeren über die gesamte Liste benötigt höchstens O(Anzahl Kollisionen)
 */


public class MyHashMap {
	// nicht schön aber Java erlaubt keine Arrays von generischen Typen
	private Object[] values = new Object[65536]; 
	private int size = 0;

	public void put(Point key, Integer value) {
		int index = key.hashCode();
		
		// Wir können uns an dieser Stelle sicher sein, dass das Objekt eine LinkedList mit entsprechendem Entry ist, 
		// da wir keinen Zugriff von außen auf das Array erlauben 
		@SuppressWarnings("unchecked")
		LinkedList<Entry<Point, Integer>> list = (LinkedList<Entry<Point, Integer>>) this.values[index];
		
		// Wenn die Liste noch nicht existiert
		if (list == null) {
			list = new LinkedList<Entry<Point, Integer>>();
			this.values[index] = list;
		}
				
		// Wenn das Element mit dem gegeben Schlüssel existiert, dann überschreiben den Wert
		boolean containsKey = false;
		for (Entry<Point, Integer> e : list) {
			if (e.key().equals(key)) {
				e.setValue(value);
				containsKey = true;
				break;
			}
		}
		
		// füge an das Ende der Liste ein Entry Element bestehend aus Wert und Punkt ein, wenn das Element noch nicht existiert.
		if (containsKey == false) {
			Entry<Point, Integer> item = new Entry<Point, Integer>(key, value);
			list.add(item);
		}
		
		size ++;
	}
	
	
	public int size() {
		return size;
	}
	
	
	public Integer get(Point p) {
		int index = p.hashCode();
		
		@SuppressWarnings("unchecked")
		LinkedList<Entry<Point, Integer>> list = (LinkedList<Entry<Point, Integer>>) this.values[index];
		
		// Wenn die liste existiert, iteriere über alle Elemente der Liste und vergleiche die Objekte
		if (list != null)
			for (Entry<Point, Integer> e : list)
				if (e.key().equals(p))
					return e.value();
		
		// nichts gefunden
		return null;
	}
	
	
	public Point remove(Point p) {
		int index = p.hashCode();
		
		// Element suchen und entfernen 
		// ähnlich zu get
		@SuppressWarnings("unchecked")
		LinkedList<Entry<Point, Integer>> list = (LinkedList<Entry<Point, Integer>>) this.values[index];
		if (list != null)
			for (Entry<Point, Integer> e : list)
				if (e.key().equals(p)) {
					size --;
					list.remove(e);
					return p;
				}
		return null;
	}
	
	
	public static void main(String[] args) {
		//test
		MyHashMap test = new MyHashMap();
		
		Point p1 = new Point(4,0);
		Point p2 = new Point(2,3);
		Point p3 = new Point(2,1);
		Point p4 = new Point(6,2);
		
		test.put(p1, 1);
		test.put(p2, 2);
		test.put(p3, 3);
		test.put(p4, 4);
		test.put(p1, 5);
		
		System.out.println(test.remove(p2));
		System.out.println(test.get(p1));
		System.out.println(test.get(p2));
		System.out.println(test.get(p3));
		System.out.println(test.get(p4));
	}
}

