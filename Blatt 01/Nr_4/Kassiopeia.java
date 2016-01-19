import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Klasse dient ledeglich zum ausführen
 * @author David Klopp, Christian Stricker, Markus Vieth
 */
public class Kassiopeia {

	/**
	 *
	 * @param args nothing for default, something if user wants to enter his own path to a map file
	 * @throws IOException
	 */
	public static void main(String... args) throws IOException {
		
		KassiopeiaMap map;
		Scanner input;
		String path;
		if(args.length == 0) {
			for (int i = 0; i < 8; i++) {
				path = "http://www.bundeswettbewerb-informatik.de/uploads/media/kassiopeia" + i + ".txt";
				map = new KassiopeiaMap(path);

				try {
					System.out.println(map.findPath());
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println();
			}
		}
			
		else {
			input = new Scanner(System.in);
			System.out.println("Bitte geben Sie den Dateipfad für die Karte ein:");
			path = input.nextLine();
			map = new KassiopeiaMap(path);

			try {
				System.out.println(map.findPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}

/**
 * Klasse mit den Funktionen
 */
class KassiopeiaMap {

	//0 N, 1 O, 2 S, 3 W
	private int posX;
	private int posY;

	private int lenX;
	private int lenY;

	char[][] map;


	public KassiopeiaMap (String path) throws IOException {
		Scanner input;

		//Connects to webspace
		if (path.startsWith("http")) {
			URL urlPath = new URL(path);
			URLConnection connection = urlPath.openConnection();

			InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());

			input = new Scanner(inputReader);
		//Load file
		} else {
			FileReader file = new FileReader(path);
			input = new Scanner(file);
		}

		lenY = input.nextInt();
		lenX = input.nextInt();
		input.nextLine();

		map = new char[lenY][lenX];

		//Copies Map to array map
		for (int y = 0; y < lenY; y++) {
			String line = input.nextLine();
			System.out.println(line);
			for ( int x = 0; x < lenX; x++) {
				map[y][x] = line.charAt(x);
				if (line.charAt(x) == 'K') {
					posX = x;
					posY = y;
				}
			}
		}
		try {
			String control = checkMap(map);
			if(!"ok".equals(control)) {
				if (control.startsWith("Invalid amount of Ks:"))
					throw new InvalidAmountOfKsException(createMessage(Integer.parseInt(control.substring(21))));
				else
					throw new UnexpectedCharacterException(control.charAt(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 *
	 * @return if it is possible zo find a solution
	 */
	public boolean findPath () {
		return this.findPath(this.posY, this.posX, this.map, new StringBuilder());
	}

	/**
	 *
	 * @param posY the row with Kassiopeia
	 * @param posX the column with Kassiopeia
	 * @param map the map to test
	 * @param way the way till now
	 * @return if it is possible zo find a solution
	 */
	private boolean findPath (int posY, int posX, char[][] map, StringBuilder way) {

		//Try every direction
		for (int i = 0; i <= 3; i++){
			boolean noN = posY <= 0;
			boolean noO = posX >= map[posY].length;
			boolean noS = posY >= map.length;
			boolean noW = posX <= 0;

			//Kassiopeia is not allowed to cross the border
			if(noN && i == 0 || noO && i == 1 || noS && i == 2 || noW && i == 3)
				continue;

			//mark the actual position as visited
			map[posY][posX] = '#';

			//0 is north, 1 ist east, 2 is south, 3 is west
			switch (i){
				case 0:
					//check if move is valid
					if (map[posY-1][posX] == ' ') {
						//note the direction
						way.append("N");
						//try the direction
						if(findPath(posY-1,posX,map,way))
							//valid path found
							return true;
						else
							//clear the note
							way.deleteCharAt(way.length()-1);
					}
					break;
				case 1:
					if (map[posY][posX+1] == ' ') {
						way.append("O");
						if(findPath(posY,posX+1,map,way))
							return true;
						else
							way.deleteCharAt(way.length()-1);
					}
					break;
				case 2:
					if (map[posY+1][posX] == ' ') {
						way.append("S");
						if(findPath(posY+1,posX,map,way))
							return true;
						else
							way.deleteCharAt(way.length()-1);
					}
					break;
				case 3:
					if (map[posY][posX-1] == ' ') {
						way.append("W");
						if(findPath(posY,posX-1,map,way))
							return true;
						else
							way.deleteCharAt(way.length()-1);
					}
					break;
			}
		}

		//check if map was successfully cleared
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == ' ') {
					//remove last move
					map[posY][posX] = ' ';
					return false;
				}
			}
		}
		//print path
		System.out.println(way);
		return true;
	}

	/**
	 *
	 * @param map map that should be checked
	 * @return
	 */
	private static String checkMap(char[][] map) {

		int numberOfKChars = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				switch(map[i][j]) {
					case 'K':
						numberOfKChars++; //fallthrough
					case ' '://fallthrough
					case '#':
						break;
					default: //Invalid character
						return map[i][j]+"";
				}
			}
		}

		if (numberOfKChars != 1)
			//Invalid number of Ks
			return "Invalid amount of Ks:"+numberOfKChars;
		return "ok";
	}


	private static class UnexpectedCharacterException extends Exception {

		/**
		 *
		 * @param c the unexpected character
		 */
		public UnexpectedCharacterException(char c) {
			super("Unerwarteter Charakter '"+c+"'!");
		}

	}


	private static class InvalidAmountOfKsException extends Exception {

		/**
		 *
		 * @param s message about the invalid number of Ks in the map
		 */
		public InvalidAmountOfKsException (String s) {
			super (s);
		}
	}

	/**
	 *
	 * @param i number of Ks in the map
	 * @return
	 */
	private static String createMessage(int i) {
		if (i >1)
			return"Zu viele Ks auf der Karte. Es wurden "+i+" Ks gefunden!";
		else
			return"Es muss sich mindestens ein K auf der Karte befinden";
	}
}