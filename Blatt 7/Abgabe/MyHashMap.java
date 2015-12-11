import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

class Point implements Comparable<Point> {
	public int x,y;
	static Random random = new Random();
	static final long p = 2147483647L; // Primzahl < 2^31
	static long a = nextLong(random,p-1)+1; // zufällig 0 < a < p
	static long b = nextLong(random,p); // zufällig 0 <= b < p
	static long m = 10000;

	public static void newHash(int m) {
		a = nextLong(random,p-1)+1; // zufällig 0 < a < p
		b = nextLong(random,p); // zufällig 0 <= b < p
		Point.m = m; 
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
 * Da der Zugriff auf einen Index in einem Array in O(1) funktioniert, kann eine LinkedList in O(1) 
 * erhalten werden. Das Iteririeren über die gesamte Liste benötigt höchstens O(Anzahl Kollisionen 
 * für dieses Element)
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

		// Wir können uns an dieser Stelle sicher sein, dass das Objekt eine 
		// LinkedList mit entsprechendem Entry ist,
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

		// füge an das Ende der Liste ein Entry Element bestehend aus Wert und Punkt ein, wenn 
		// das Element noch nicht existiert.
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


	public static void main(String[] args) throws FileNotFoundException, 
			UnsupportedEncodingException {

		PrintWriter log = new PrintWriter("log.txt");

		for (int m = 1; m <= 400; m++) {
			Point.newHash(m);
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
			PrintWriter out = new PrintWriter("myHash.txt");
			//1. Laufzeit: O(1)
			long start1 = System.nanoTime();
			System.out.println("Anzahl der Punkte: "+h.size());
			long end1 = System.nanoTime();
			//2. Laufzeit: O(n^2)
			long start2 = System.nanoTime();
			for (int j = 0; j < h.size(); j++) { // O(n)
				for (Entry<Point, Integer> e : h.entrySet()) // O(n)
					if (e.getValue().equals(j)) // O(1)
						out.println(e.getKey().x+"  "+e.getKey().y); // O(1)
			}
			long end2 = System.nanoTime();
			//3. O(n)
			long start3 = System.nanoTime();
			for ( int j = 0; j < s.size(); j++) {
				out.println(h.get(s.get(j)[0])+", "+h.get(s.get(j)[1]));
			}
			long end3 = System.nanoTime();

			out.close();
			in.close();

			// Bonusaufgabe
			DecimalFormat format1 = new DecimalFormat("#000,000;(#)");
			DecimalFormat format2 = new DecimalFormat("#0.0000000;(#)");
			long timeInNs1 = (end1-start1);	// Laufzeit für Teilaufgabe a
			long timeInNs2 = (end2-start2);	// Laufzeit für Teilaufgabe b 
			long timeInNs3 = (end3-start3);	// Laufzeit für Teilaufgabe c
			double a = h.size*1.0/m;		// Belegungsfaktor
			System.out.println("Belegungsfaktor: "+format2.format(a)+" | T(20): "
					+format1.format(timeInNs1+timeInNs2+timeInNs3)+ " | T1(20): "
					+format1.format(timeInNs1)+ " | T2(20): "+format1.format(timeInNs2)
					+ " | T3(20): "+format1.format(timeInNs3));
			log.println("Belegungsfaktor: "+format2.format(a)+" | T(20): "
					+format1.format(timeInNs1+timeInNs2+timeInNs3)+ " | T1(20): "
					+format1.format(timeInNs1)+ " | T2(20): "+format1.format(timeInNs2)
					+ " | T3(20): "+format1.format(timeInNs3));
		}
		log.close();
	}
}
