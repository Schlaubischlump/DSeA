import java.util.Scanner;


public class Puzzle {

	public String kodieren(int[][] arr){
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < arr.length;i++)
			for(int j = 0; j < arr[0].length;j++)
				result.append(arr[i][j]);
		return result.toString();
	}
	
	Puzzle(String filepath){
		Scanner scanner = new Scanner(filepath);
		//Erste Zeile einlesen und die größe des Arrayfeldes bestimmen.
		String eingabe = scanner.nextLine();
		String[] line = eingabe.split(" ");
		//Quadratisches Array erstellen
		int[][] arr = new int[line.length][line.length];
		
		for(int i = 0; i < line.length;i++)
			arr[0][i] = Integer.parseInt(line[i]);
		
		for(int i = 1; i < line.length;i++)
			for(int j = 0; j < line.length;j++)
				arr[i][j] = scanner.nextInt();
		
		scanner.close();			
	}
}
