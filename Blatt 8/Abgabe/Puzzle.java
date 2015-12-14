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
		HashMap<String, Boolean> visited = new HashMap<>();
		MyQueue<String> queue = new MyQueue<>(n*n*n*n);
		HashMap<String, String> pi = new HashMap<>();
		
		visited.put(start, true);
		pi.put(start, null);
		
		queue.push(start);
		while(queue.size() != 0) {
			String u = queue.pop();
			int index = u.indexOf("0");
			ArrayList<Integer> edge = edges.get(index);
			for ( int i = 0; i < edge.size(); i++) {
				String next = swap(u,index, edge.get(i));
				if (this.correct(next))
					return true;
				if (visited.get(next) != true) {
					visited.put(next, true);
					pi.put(u, next);
					queue.push(next);
				}
			}
		}
		return false;
	}
	
	private static String swap (String string, int first, int last) {
		char[] temp = string.toCharArray();
		if (first < 0 || first >= string.length() || last < 0 || last >= string.length())
			throw new IndexOutOfBoundsException();
		char firstChar = temp[first];
		temp[first] = temp[last];
		temp[last] = firstChar;
		return temp.toString();
	}
	
	private boolean correct (String solution) {
		int l = solution.length();
		char[] temp = solution.toCharArray();
		for ( int i = 1; i < l; i++) {
			if ( temp[i-1] >= temp[i])
				return false;
		}
		return true;
	}
}