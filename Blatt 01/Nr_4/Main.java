import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class Main {
	//0 frei, 1 schwarz, 2 gelaufen
	//0 N, 1 O, 2 S, 3 W
	//Spalte, dann Zeile
	private static boolean findPath (int posY, int posX, int[][] map, StringBuilder way) {
		
		//int[][] tempCopy = map.clone();
		//System.out.println();
		//System.out.println(posX+"    "+posY);
		//for (int y = 0; y < map.length; y++) {
		//	for (int x = 0; x < map[y].length; x++) {
		//		System.out.print(map[y][x]);
		//	}
		//	System.out.println();
		//}
		for (int i = 0; i <= 3; i++){
			boolean noN = posY <= 0;
			boolean noO = posX >= map[posY].length;
			boolean noS = posY >= map.length;
			boolean noW = posX <= 0;
			
			if(noN && i == 0 || noO && i == 1 || noS && i == 2 || noW && i == 3)
				continue;
			
			map[posY][posX] = 2;
			switch (i){
			case 0:
				if (map[posY-1][posX] == 0) {
					way.append("N");
					if(findPath(posY-1,posX,map,way))
						return true;
					else
						way.deleteCharAt(way.length()-1);
				}
				break;
			case 1:
				if (map[posY][posX+1] == 0) {
					way.append("O");
					if(findPath(posY,posX+1,map,way))
						return true;
					else
						way.deleteCharAt(way.length()-1);
				}
				break;
			case 2:
				if (map[posY+1][posX] == 0) {
					way.append("S");
					if(findPath(posY+1,posX,map,way))
						return true;
					else
						way.deleteCharAt(way.length()-1);
				}
				break;
			case 3:
				if (map[posY][posX-1] == 0) {
					way.append("W");
					if(findPath(posY,posX-1,map,way))
						return true;
					else
						way.deleteCharAt(way.length()-1);
				}
				break;
			}
		}
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0) {
					map[posY][posX] = 0;					
					return false;
				}
			}
			//System.out.println();
		}
		System.out.println(way);
		return true;
	}
	
	public static void main(String... args) throws IOException {
		
		for (int i = 0; i < 8; i++) {
			
			Scanner input;
			if(args.length == 0) {
				String path = "http://www.bundeswettbewerb-informatik.de/uploads/media/kassiopeia"+i+".txt";
				URL urlPath = new URL(path);
				URLConnection connection = urlPath.openConnection(); 
			
				InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
			
				input = new Scanner(inputReader);
			}
			
			else {
				input = new Scanner(System.in);
				System.out.println("Bitte geben Sie den Dateipfad für die Karte ein:");
				String path = input.nextLine();
				FileReader file = new FileReader(path);
				input = new Scanner(file);
			}
				
			
			int posX = -1;
			int posY = -1;
			
			int lenY = input.nextInt();
			int lenX = input.nextInt();
			input.nextLine();
			int[][] map = new int[lenY][lenX];
			for (int y = 0; y < lenY; y++) {
				String line = input.nextLine();
				System.out.println(line);
				for (int x = 0; x < lenX; x++) {
					switch (line.charAt(x)) {
					case '#':
						map[y][x] = 1;
						break;
					case 'K':
						posX = x;
						posY = y;
					case ' ':
						map[y][x] = 0;
						break;
					}
				}
			}
			System.out.println(findPath(posY, posX, map, new StringBuilder()));
		}
		
	}

}
