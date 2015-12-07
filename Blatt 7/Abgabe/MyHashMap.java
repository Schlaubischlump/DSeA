import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

class Point implements Comparable<Point> {
    public int x,y;
    static Random random = new Random();
    static final long p = 2147483647L; // Primzahl < 2^31
    static long a = nextLong(random,p-1)+1; // zufällig 0 < a < p
    static long b = nextLong(random,p); // zufällig 0 <= b < p
    //static final long m = (long)Math.pow(2, 16);
    static final long m = 33;
    public static void newHash(boolean rndm) {
    	if (rndm) {
    		a = nextLong(random,p-1)+1; // zufällig 0 < a < p
        	b = nextLong(random,p); // zufällig 0 <= b < p
    	} else {
    	    a = 1189436865L; // zufällig 0 < a < p
    	    b = 1206511853L; // zufällig 0 <= b < p
    	}
    }
    
    Point(int a,int b) {
        x = a;
        y = b;
    }
    
    private static long nextLong(Random rndm, long n) {
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
        
        if (((a * z + b) % p)%m < 0)
        	return (int)((((a * z + b) % p)%m)+m);
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
    
    public K getKey()
    {
        return this.key;
    }
    
    public V getValue()
    {
        return this.value;
    }
    
    public void setValue(V value) {
        this.value = value;
    }
}


/*
 * Da der Zugriff auf einen Index in einem Array in O(1) funktioniert, kann eine LinkedList in O(1) erhalten werden.
 * Das Iteririeren über die gesamte Liste benötigt höchstens O(Anzahl Kollisionen für dieses Element)
 *
 *
 */


public class MyHashMap<K, V> {
    // nicht effizient, aber so kann die Abfrage in O(1) erfolgen
    private HashSet<Entry<K, V>> entrySet = new HashSet<Entry<K, V>>();
    // nicht schön aber Java erlaubt keine Arrays von generischen Typen
    private Object[] values = new Object[65536];
    private int size = 0;
    public int n = 0; //Anuahl der Kollisionen
    
    public void put(K key, V value) {
        int index = key.hashCode();
        
        // Wir können uns an dieser Stelle sicher sein, dass das Objekt eine LinkedList mit entsprechendem Entry ist,
        // da wir keinen Zugriff von außen auf das Array erlauben
        @SuppressWarnings("unchecked")
        LinkedList<Entry<K, V>> list = (LinkedList<Entry<K, V>>) this.values[index];
        
        // Wenn die Liste noch nicht existiert
        if (list == null) {
            list = new LinkedList<Entry<K, V>>();
            this.values[index] = list;
            n--;
        }
        
        // Wenn das Element mit dem gegeben Schlüssel existiert, dann überschreiben den Wert
        boolean containsKey = false;
        for (Entry<K, V> e : list) {
            if (e.getKey().equals(key)) {
                e.setValue(value);
                containsKey = true;
                break;
            }
        }
        
        // füge an das Ende der Liste ein Entry Element bestehend aus Wert und Punkt ein, wenn das Element noch nicht existiert.
        if (containsKey == false) {
            Entry<K, V> item = new Entry<K, V>(key, value);
            list.add(item);
            entrySet.add(item);
            n++;
        }
        
        size ++;
    }
    
    
    public HashSet<Entry<K, V>> entrySet() {
        // O(1)
        return this.entrySet;
    }
    
    public int size() {
        // O(1)
        return size;
    }
    
    
    public V get(K key) {
        int index = key.hashCode();
        
        @SuppressWarnings("unchecked")
        LinkedList<Entry<K, V>> list = (LinkedList<Entry<K, V>>) this.values[index];
        
        // Wenn die liste existiert, iteriere über alle Elemente der Liste und vergleiche die Objekte
        if (list != null)
            for (Entry<K, V> e : list)
                if (e.getKey().equals(key))
                    return e.getValue();
        
        // nichts gefunden
        return null;
    }
    
    public boolean containsKey(K key) {
        return this.get(key) != null;
        
    }
    
    
    public Point remove(Point p) {
        int index = p.hashCode();
        
        // Element suchen und entfernen
        // ähnlich zu get
        @SuppressWarnings("unchecked")
        LinkedList<Entry<K, V>> list = (LinkedList<Entry<K, V>>) this.values[index];
        if (list != null)
            for (Entry<K, V> e : list)
                if (e.getKey().equals(p)) {
                    size --;
                    list.remove(e);
                    return p;
                }
        return null;
    }
    

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
    	double diff = 0;
    	double result = 0;
    	for (int z = 0; z < 100; z++)
    		for (int r = 0; r < 2; r++) {
    			int sum = 0;
    			for (int v = 0; v < 100; v++) {

    				Point.newHash(r==0);
    				MyHashMap<Point, Integer> h = new MyHashMap<Point, Integer>();

    				ArrayList<Point[]> s = new ArrayList<Point[]>();
    				Scanner in = new Scanner(new InputStreamReader(new FileInputStream("input.txt"),"UTF-8"));

    				int i = 0;
    				while (in.hasNextLine()) {
    					Point a = new Point(in.nextInt(), in.nextInt());
    					Point b = new Point(in.nextInt(), in.nextInt());
    					if (!h.containsKey(a))
    						h.put(a, i++);
    					if (!h.containsKey(b))
    						h.put(b, i++);
    					s.add(new Point[]{a,b});
    				}

    				// Analyse 

    				PrintWriter out = new PrintWriter("hash.txt");

    				//1. Laufzeit: O(1)
    				//System.out.println("Anzahl der Punkte: "+h.size());
    				//2. Laufzeit: O(n^2)
    				for (int j = 0; j < h.size(); j++) { // O(n)
    					for (Entry<Point, Integer> e : h.entrySet()) // O(n)
    						if (e.getValue().equals(j)) // O(1)
    							out.println(e.getKey().x+"  "+e.getKey().y); // O(1)
    				}
    				//3. O(n)
    				for ( int j = 0; j < s.size(); j++) {
    					out.println(h.get(s.get(j)[0])+", "+h.get(s.get(j)[1]));
    				}

    				out.close();
    				in.close();

    				sum += h.n;
    				//System.out.println(h.n);
    			}
    			if (r == 0) {
    				System.out.println("randomisiert: "+sum/100.0);
    				diff = sum/100.0;
    			} else {
    				System.out.println("normal: "+sum/100.0);
    				diff -= sum/100.0;
    				System.out.println("ran-norm = "+diff);
    				result += diff;
    				diff = 0;
    			}
    		}
    	System.out.print("Schintt: "+result);
    }
}

