import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

class Puzzle {
	
	int[][] arr;
	HashMap<Integer, ArrayList<Integer>> edges = new HashMap<Integer, ArrayList<Integer>>();
	int n;
	String start;
	
	public Puzzle (String filepath, int n, HashMap<Integer, ArrayList<Integer>> edges) throws FileNotFoundException {
		this.n = n;
		this.edges = edges;
		Scanner input = new Scanner(new FileReader(filepath));
		arr = new int[n][n];
		StringBuilder loesung = new StringBuilder();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (!input.hasNextInt()) {
					input.close();
					throw new NoSuchElementException("missing Element in Line "+(i*n));
				}
				arr[i][j] = input.nextInt();
				loesung.append((j+i*n));
			}
		input.close();
		start = this.convert(arr);
	}
	
	public Puzzle (String filepath) throws FileNotFoundException{
		this(filepath, 3, createHash(3));
	}
	
	private Puzzle (char[] puzzle, int n) {
		this(puzzle, n, createHash(n));
	}
	
	private Puzzle (char[] puzzle, int n, HashMap<Integer, ArrayList<Integer>> edges) {
		start = String.copyValueOf(puzzle);
		this.n = n;
		this.edges = edges;
	}
	
	private void setStart(String start) {
		this.start = start;
	}
	public String convert (int[][] arr) {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < arr.length; i++)
			for (int j = 0; j < arr[i].length; j++)
				string.append(arr[i][j]);
		return string.toString();
	}
	
	public boolean loesbar () {
		HashSet<String> visited = new HashSet<>();
		MyQueue<String> queue = new MyQueue<>(n*n*n*n);
		HashMap<String, String> pi = new HashMap<>();
		
		//visited.put(start, true);
		visited.add(start);
		pi.put(start, null);
		
		queue.push(start);
		while(queue.size() != 0) {
			String u = queue.pop();
			int index = u.indexOf("0");
			ArrayList<Integer> edge = edges.get(index);
			for ( int i = 0; i < edge.size(); i++) {
				String next = swap(u,index, edge.get(i));
				//if (visited.get(next) == null || visited.get(next) == false) {
				if (!visited.contains(next)) {
					//visited.put(next, true);
					visited.add(next);
					pi.put(next, u);
					queue.push(next);
				}
				if (this.correct(next))
					return true;
			}
		}
		return false;
	}
	
	private static HashMap<Integer, ArrayList<Integer>> createHash(int n) {

		//Erstelle Hashmap
		HashMap<Integer, ArrayList<Integer>> edges = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				edges.put(j+n*i, temp);
				if (j == 0) {
					if (i == 0) {
						temp.add(j+1+n*i);
						temp.add(j+(i+1)*n);
						
					} else if( i == n-1) {
						temp.add(j+(i-1)*n);
						temp.add(j+1+i*n);
					} else {
						temp.add(j+(i-1)*n);
						temp.add(j+(i+1)*n);
						temp.add(j+1+i*n);
					}
				} else if (j == n-1) {
					if (i == 0) {
						temp.add(j-1+n*i);
						temp.add(j+(i+1)*n);
						
					} else if( i == n-1) {
						temp.add(j+(i-1)*n);
						temp.add(j-1+i*n);
					} else {
						temp.add(j+(i-1)*n);
						temp.add(j+(i+1)*n);
						temp.add(j-1+i*n);
					}
				} else {
					if (i == 0) {
						temp.add(j+1+n*i);
						temp.add(j-1+n*i);
						temp.add(j+(i+1)*n);
						
					} else if( i == n-1) {
						temp.add(j+(i-1)*n);
						temp.add(j-1+i*n);
						temp.add(j+1+n*i);
					} else {
						temp.add(j+(i-1)*n);
						temp.add(j+(i+1)*n);
						temp.add(j-1+i*n);
						temp.add(j+1+n*i);
					}
				}	
			}
		return edges;
	}
	public int tiefe () {
		//HashMap<String, Boolean> visited = new HashMap<>();
		HashSet<String> visited = new HashSet<>();
		MyQueue<String> queue = new MyQueue<>(n*n*n*n);
		HashMap<String, String> pi = new HashMap<>();
		HashMap<String, Integer> d = new HashMap<>();
		String loesung = null;
		//visited.put(start, true);
		visited.add(start);
		pi.put(start, null);
		d.put(start, 0);
		
		queue.push(start);
		while(queue.size() > 0) {
			String u = queue.pop();
			int index = u.indexOf("0");
			ArrayList<Integer> edge = edges.get(index);
			for ( int i = 0; i < edge.size(); i++) {
				String next = swap(u,index, edge.get(i));
				//if (visited.get(next) == null || visited.get(next) == false) {
				if (!visited.contains(next)) {
					//visited.put(next, true);
					visited.add(next);
					pi.put(next, u);
					queue.push(next);
					d.put(next, d.get(u)+1);
				}
				if (this.correct(next)) {
					loesung = next;
					//return d.get(next);
					//result = Math.min(d.get(next), result);
				}
			}
		}
		if (loesung == null)
			return -1;
		return d.get(loesung);
	}
	
	public static int maxTiefe(int breite) throws FileNotFoundException {
		int max = -1;
		int n = breite*breite;
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < n; i++)
			temp.append(i+"");
		HashMap<Integer, ArrayList<Integer>> edges = createHash(breite);
		return maxTiefe(temp.toString().toCharArray(), max, breite, n, edges);
	}
	
	private static int maxTiefe(char[] perm, int max,int breite, int n, HashMap<Integer, ArrayList<Integer>> edges) throws FileNotFoundException {
		if ( n == 1) {
			int temp = new Puzzle(perm, breite, edges).tiefe();
			return Math.max(temp, max);
		}
		int result = max;
		for (int i = 0; i < n; i++) {
			swap(perm, i, n-1);
			result = Math.max(maxTiefe(perm, result, breite, n-1, edges), result);
			swap(perm, i, n-1);
		}
		return result;
	}

	
	private static String swap (String string, int first, int last) {
		char[] temp = string.toCharArray();
		swap(temp, first, last);
		return String.valueOf(temp);
	}
	
	private static void swap (char[] arr, int first, int last) {
		char temp = arr[first];
		arr[first] = arr[last];
		arr[last] = temp;
	}
	
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
	
	public static void main (String... args) throws FileNotFoundException {
		Puzzle test = new Puzzle("in.txt");
		System.out.println(test.loesbar());
		System.out.println(test.tiefe());
		System.out.println(Puzzle.maxTiefe(3));
	}
}