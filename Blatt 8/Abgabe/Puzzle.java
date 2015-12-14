import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

class Puzzle {
	
	int[][] arr;
	HashMap<Integer, ArrayList<Integer>> edges = new HashMap<Integer, ArrayList<Integer>>();
	
	public Puzzle (String filepath, int n) {
		Scanner input = new Scanner(filepath);
		arr = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (!input.hasNextInt()) {
					input.close();
					throw new NoSuchElementException("missing Element in Line "+(i+i));
				}
				arr[i][j] = input.nextInt();
			}
		input.close();
		
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
	
	
}