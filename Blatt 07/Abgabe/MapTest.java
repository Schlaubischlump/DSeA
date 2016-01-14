import java.util.Map;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

class Point implements Comparable<Point> {
	public int x,y;

	Point(int a,int b) { 
		x = a;
		y = b;
	}

	@Override
	public int hashCode() {
		long z = x << 16 + y;
		long p = 2147483647L; // Primzahl < 2^31
		long a = 1189436865L; // zufällig 0 < a < p
		long b = 1206511853L; // zufällig 0 <= b < p
		// hier m = p

		// Für ein m != p muss da dieser Stelle noch modulo m
		// gerechnet werden
		return (int)((a * z + b) % p);
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

public class MapTest {
	public static void main(String[] args) throws IOException {
		
		
//		List<Point> P = new LinkedList<Point>();
//		P.add(new Point(1, 2));
//		P.add(new Point(0, 0));
//		P.add(new Point(1, 2));
//		P.add(new Point(0, 0));
//		P.add(new Point(1, 2));
//		P.add(new Point(0, 0));
//		P.add(new Point(1, 2));
//		P.add(new Point(0, 0));
//		P.add(new Point(1, 2));
//		P.add(new Point(0, 0));
//		P.add(new Point(2, 2));
		ArrayList<Point[]> S = new ArrayList<Point[]>();

		// Anlegen der HashMap, welche einen 2D Punkt auf eine Ganzzahl abbildet
		HashMap<Point, Integer> H = new HashMap<Point, Integer>();
		
		PrintWriter out = new PrintWriter("hash.txt");
		Scanner in = new Scanner(new InputStreamReader(new FileInputStream("input.txt"),"UTF-8"));
		// Fügt den Punkt p mit einem Index der HashMap zu, falls dieser noch
		// nicht enthalten ist.
		/*int i = 0;
		for (Point p : P)
			if (!H.containsKey(p))
				H.put(p, i++);*/
		int i = 0;
		while (in.hasNextLine()) {
			Point a = new Point(in.nextInt(), in.nextInt());
			Point b = new Point(in.nextInt(), in.nextInt());
			if (!H.containsKey(a))
				H.put(a, i++);
			if (!H.containsKey(b))
				H.put(b, i++);
			S.add(new Point[]{a,b});
		}

		// Gibt die (Key, Value) Paare aus, die oben hinzugefügt wurden
		for (Map.Entry<Point, Integer> e : H.entrySet())
			System.out.println(e.getKey().x + " " + e.getKey().y + " " + e.getValue());

		//Aufgabe a)
        //Hash
		//1. Laufzeit: O(1)
		System.out.println("Anzahl der Punkte: "+H.size());
		//2. Laufzeit: O(n^2)
		for (int j = 0; j < H.size(); j++) { // O(n)
			for (Map.Entry<Point, Integer> e : H.entrySet()) // O(n)
				if ( e.getValue().equals(j)) // O(1)
					out.println(e.getKey().x+"  "+e.getKey().y); // O(1)
		}
		//3. O(n)
		for ( int j = 0; j < S.size(); j++) {
			out.println(H.get(S.get(j)[0])+", "+H.get(S.get(j)[1]));
		}




		out.close();
		in.close();
		// Selbes für die TreeMap
		TreeMap<Point, Integer> T = new TreeMap<Point, Integer>();

		out = new PrintWriter("tree.txt");
		in = new Scanner(new FileReader("input.txt"));

		// Fügt den Punkt p mit einem Index der TreeMap zu, falls dieser noch
		// nicht enthalten ist.
		i = 0;
		/*for (Point p : P)
			if (!T.containsKey(p))
				T.put(p, i++);*/
		while (in.hasNextLine()) {
			Point a = new Point(in.nextInt(), in.nextInt());
			Point b = new Point(in.nextInt(), in.nextInt());
			if (!T.containsKey(a))
				T.put(a, i++);
			if (!T.containsKey(b))
				T.put(b, i++);
		}

		// Gibt die (Key, Value) Paare aus, die oben hinzugefügt wurden
		for (Map.Entry<Point, Integer> e : T.entrySet())
			System.out.println(e.getKey().x + " " + e.getKey().y + " " + e.getValue());

		//Aufgabe a)
        //Tree
		//1. O(1)
		System.out.println("Anzahl der Punkte: "+T.size());
		//2. O(n)
		for (int j = 0; j < T.size(); j++) { // O(n)
			for (Map.Entry<Point, Integer> e : T.entrySet()) // O(n)
				if ( e.getValue().equals(j)) // O(1)
					out.println(e.getKey().x+"  "+e.getKey().y); // O(1)
		}
		//3. O(log(n))
		for ( int j = 0; j < S.size(); j++) {
			out.println(T.get(S.get(j)[0])+", "+T.get(S.get(j)[1])); // O(log(n))
		}
		
		
		
		out.close();
		in.close();


	}
}
