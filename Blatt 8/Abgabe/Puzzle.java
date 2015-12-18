import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

class Puzzle {
	
	int[][] arr; //damit die convert Methode einen Sinn hat
	HashMap<Integer, ArrayList<Integer>> edges = new HashMap<Integer, ArrayList<Integer>>();  //Hashmap mit möglichen Zügen
	int n;  //Kantenlänge
	String start;	//Ausgangssituation
	private final int queueSize;  //Gewählte Queue-Size
	
	/////////////
	//Aufgabe 2 b) Fortsetzung
	/////////////
	//Erstellt ein Puzzle-Objekt für eine Datei in filpath mit der Kantenlänge n
	public Puzzle (String filepath, int n) throws FileNotFoundException {
		this.n = n;
		this.edges = createHash(n);;
		this.queueSize = fak(n*n);
		Scanner input = new Scanner(new FileReader(filepath));
		arr = new int[n][n];
		
		//Zeilenweises einlesen der Datei
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (!input.hasNextInt()) {
					input.close();
					throw new NoSuchElementException("missing Element in Line "+(i*n));
				}
				arr[i][j] = input.nextInt();
			}
		input.close();
		start = this.convert(arr);
	}
	
	/////////////
	//Aufgabe 2 b)
	/////////////
	//Alternativer Aufruf für ein 8 Puzzle
	public Puzzle (String filepath) throws FileNotFoundException{
		this(filepath, 3);
	}
	
	//Alternativer Aufruf für ein bereits vorhandenes Puzzle
	private Puzzle (String puzzle, int n, HashMap<Integer, ArrayList<Integer>> edges) {
		start = puzzle;
		this.n = n;
		this.edges = edges;
		this.queueSize = fak(n*n);
	}
	
	//////////
	//Aufgabe 2 a)
	/////////
	//Konvertiert ein Puzzle in einen String
	public String convert (int[][] arr) {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < arr.length; i++)
			for (int j = 0; j < arr[i].length; j++)
				string.append(arr[i][j]);
		return string.toString();
	}
	
	///////////
	//Aufgabe 2 d)
	///////////
	//Prüft, ob ein gegebens Puzzle lösbar ist, Algorithmus entsrpicht der Breitensuche aus der Vorlesung
	public boolean loesbar () {
		HashSet<String> visited = new HashSet<>();  //hier vorhandene Knoten sind grau
		MyQueue<String> queue = new MyQueue<>(this.queueSize);  //Die Queue
		//HashMap<String, String> pi = new HashMap<>();  
		
		
		visited.add(start);  //Makiere start grau
		//pi.put(start, null);
		
		queue.push(start);  //platziere start in der queue
		while(queue.size() != 0) {
			String u = queue.pop();
			int index = u.indexOf("0");  //Ermittle den freien PLatz
			ArrayList<Integer> edge = edges.get(index);  //Ermittle die möglichen Züge/Kanten
			for ( int i = 0; i < edge.size(); i++) {  //gehe alle Kanten ab
				String next = swap(u,index, edge.get(i));  //Erzeuge neue Nachbarknoten
				if (!visited.contains(next)) {  //wenn KNoten weiß
					visited.add(next);  //Setze Knoten grau
					//pi.put(next, u);  //setze vorherigen Knoten als Vorgänger
					queue.push(next);  //platziere Knoten in der Queue
				}
				if (this.correct(next))  //wenn Lösung erreicht
					return true;
			}
		}  //Alle Knoten besucht
		return false;
	}
	
	//Theoretisch eine Fakultätsfunktion
	private static int fak(int n) {
		/*if (n < 2)
			return n;
		return fak(n-1)*n;*/
		return 362880;  //9! um Rechenzeit zu sparen
	}
	
	/////////////
	//Aufgabe 2 c) 
	/////////////
	//hilfmethode zur Erzeugung der Mashmap der möglichen Züge in einem Puzzle
	private static HashMap<Integer, ArrayList<Integer>> createHash(int n) {

		//Erstelle Hashmap
		HashMap<Integer, ArrayList<Integer>> edges = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				edges.put(j+n*i, temp);
				if (j == 0) {
					if (i == 0) {  //Oben linke Ecke
						temp.add(j+1+n*i);
						temp.add(j+(i+1)*n);
						
					} else if( i == n-1) {  //unten linke Ecke
						temp.add(j+(i-1)*n);
						temp.add(j+1+i*n);
					} else {  //linker Rand
						temp.add(j+(i-1)*n);
						temp.add(j+1+i*n);
						temp.add(j+(i+1)*n);
					}
				} else if (j == n-1) { 
					if (i == 0) {  //Oben rechte Ecke
						temp.add(j-1+n*i);
						temp.add(j+(i+1)*n);
						
					} else if( i == n-1) {  //unten rechte Ecke
						temp.add(j+(i-1)*n);
						temp.add(j-1+i*n);
					} else {
						temp.add(j+(i-1)*n);  //rechter Rand
						temp.add(j-1+i*n);
						temp.add(j+(i+1)*n);
					}
				} else {
					if (i == 0) {  //oberer Rand
						temp.add(j-1+n*i);
						temp.add(j+1+n*i);
						temp.add(j+(i+1)*n);
						
					} else if( i == n-1) {  //unterer Rand
						temp.add(j+(i-1)*n);
						temp.add(j-1+i*n);
						temp.add(j+1+n*i);
					} else {  //Mitte
						temp.add(j+(i-1)*n);
						temp.add(j-1+i*n);
						temp.add(j+1+n*i);
						temp.add(j+(i+1)*n);
					}
				}	
			}
		return edges;
	}
	
	//Ermittelt die Tiefe/Entfernung der Lösung
	public int tiefe () {
		//HashMap<String, Boolean> visited = new HashMap<>();
		HashSet<String> visited = new HashSet<>();
		MyQueue<String> queue = new MyQueue<>(this.queueSize);
		//HashMap<String, String> pi = new HashMap<>();
		HashMap<String, Integer> d = new HashMap<>();  //Distanzfunktion
		//String loesung = null;
		//visited.put(start, true);
		visited.add(start);
		//pi.put(start, null);
		d.put(start, 0);
		
		queue.push(start);
		while(queue.size() > 0) {
			String u = queue.pop();
			int index = u.indexOf("0");
			ArrayList<Integer> edge = edges.get(index);
			for ( int i = 0; i < edge.size(); i++) {
				String next = swap(u,index, edge.get(i));
				if (!visited.contains(next)) {
					visited.add(next);
					//pi.put(next, u);
					queue.push(next);
					d.put(next, d.get(u)+1);  //Länge des neuen Knoten ist Länge des Entdeckers +1
				}
				if (this.correct(next)) {
					//loesung = next;
					return d.get(next);
					//result = Math.min(d.get(next), result);
				}
			}
		}
		//if (loesung == null)
			return -1;  //Gebe negativen Weg zurück, wenn kein Weg gefunden
		//return d.get(loesung);
	}
	
	
	//////////
	//Aufgabe 2 e)
	//////////
	//Ermittelt den Längesten Weg für ein n*n-1 Puzzle
	public static int maxTiefe(int breite) throws FileNotFoundException {
		int max = -1;
		int n = breite*breite;
		
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < n; i++)  //Erzeuge Ansatz
			temp.append(i+"");
		HashMap<Integer, ArrayList<Integer>> edges = createHash(breite);  //Erzeuge einmalig die Hashmap
		return maxTiefe("", temp.toString(), max, breite, n, edges);
	}
	
	//Vorischt extrem lange Laufzeit
	private static int maxTiefe(String pre, String perm, int max,int breite, int n, 
			HashMap<Integer, ArrayList<Integer>> edges) throws FileNotFoundException {
		
		if ( n == 0) {  //Abbruchbedinung
			int temp = new Puzzle(pre, breite, edges).tiefe();
			return Math.max(temp, max);
		}
		
		int result = max;  //setze bisheriges maxiumum als Startwert
		
		for (int i = 0; i < n; i++) {  //Bilde rekursiv alle Permutationen des Startstrings
			result = Math.max(
					maxTiefe(pre + perm.charAt(i),perm.substring(0, i)+ perm.substring(i+1, n), result, 
							breite, n-1, edges), result);
		}
		if (n > 4 )  //Fortschrittsanzeige
			System.out.print("-");
		return result;
	}
	
	// Hilfsfunktion zum tauschen zweier Zeichen in einem String
	private static String swap (String string, int first, int last) {
		char[] temp = string.toCharArray();
		swap(temp, first, last);
		return String.valueOf(temp);
	}
	
	// Hilfsfunktion zum tauschen zweier Zeichen in einem char[]
	private static void swap (char[] arr, int first, int last) {
		char temp = arr[first];
		arr[first] = arr[last];
		arr[last] = temp;
	}
	
	//Überprüft, ob der übergebene String eine gültige Lösung ist 
	private boolean correct (String solution) {
		int l = solution.length();
		if (!solution.endsWith("0"))
			return false;
		for ( int i = 1; i < l-1; i++) {
			if ( Integer.parseInt(solution.charAt(i-1)+"") >= Integer.parseInt(solution.charAt(i)+""))
				return false;
		}
		return true;
	}
	
	//Ein paar Test
	public static void main (String... args) throws FileNotFoundException {
		Puzzle test = new Puzzle("in.txt");
		System.out.println(test.loesbar());  //Gibt für das Beispiel aus dem Blatt korrekt false zurück
		System.out.println(test.tiefe());
		System.out.println(Puzzle.maxTiefe(3));  //Sollte 31 zurückgeben
	}
}