import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

class Puzzle {
	
	int[][] arr;
	HashMap<Integer, ArrayList<Integer>> edges = new HashMap<Integer, ArrayList<Integer>>();
	int n;
	String start;
	
	public Puzzle (String filepath, int n) {
		this.n = n;
		Scanner input = new Scanner(filepath);
		arr = new int[n][n];
		StringBuilder loesung = new StringBuilder();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (!input.hasNextInt()) {
					input.close();
					throw new NoSuchElementException("missing Element in Line "+(i+i));
				}
				arr[i][j] = input.nextInt();
				loesung.append((j+i*n));
			}
		input.close();
		start = this.convert(arr);
		//Erstelle Hashmap
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				ArrayList<Integer> temp;
				if (j == 0) {
					temp = new ArrayList<Integer>();
					edges.put(j+n*i, temp);
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
					temp = edges.get(j+n*i);
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
					temp = edges.get(j+n*i);
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
	}
	
	public Puzzle (String filepath){
		this(filepath, 3);
	}
	
	public String convert (int[][] arr) {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < arr.length; i++)
			for (int j = 0; j < arr[i].length; j++)
				string.append(arr[i][j]);
		return string.toString();
	}
	
	public boolean loesbar () {
		
		return true;
	}
	
	private static String switch (String string, int first, int last) {
	char[] temp = string.toCharArray();
	if (first < 0 || first >= string.length() || last < 0 || last >= string.length())
		throw new IndexOutOfBoundsException();
	}
	public void breitensuche() {
		HashMap<String, Boolean> visited = new HashMap<>();
		MyQueue<String> queue = new MyQueue<>(n*n*n*n);
		HashMap<String, String> pi = new HashMap<>();
		
		visited.put(start, true);
		pi.put(start, null);
		
		queue.push(start);
		while(queue.size() != 0) {
			String u = queue.pop();
			ArrayList<Integer> edge = edges.get(u.indexOf("0"));
			for ( int i = 0; i < edge.size(); i++) {
				String next = u.
			}
		}

forall ( v in V\{S}) {
  col[v]=white;    // Farbe  weiß = unbekannt, grau = bekannt, schwarz = vollkommen bekannt
  d[v] = infinity; // Distanz
  pi[v] = NULL;    // pi ist Vorgänger
}
col[s] = grey;     // s ist Startknoten
d[s] = 0;
pi[s] = null;

Queue Q;
Q.push(s);
while(!Q.empty()) {
  u = Q.pop();
  forall( (u,v) in E) {
    if (col[v] == white) {
      col[v] == grey;
      d[v] = d[u]+1;
      pi[v] = u;
      Q.push(v);
    }
  }
  col[u] = black;
}
	}
	
}