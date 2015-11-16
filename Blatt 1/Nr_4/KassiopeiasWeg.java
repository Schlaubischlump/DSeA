public class KassiopeiasWeg {

	static boolean[][] irrweg;
	static int max = 0;
	static String[] path;
	static int startX = 0;
	static int startY = 0;
	
	// parse input 
	// see http://www.bundeswettbewerb-informatik.de/aktuell/1-runde/material-341/ for format description
	// we don't do any error handling in this method
	public static void generateIrrwegFromInputString(String input) {
		String[] zeilen = input.split("\n");
		boolean[][] tmpIrrweg = new boolean[zeilen.length-1][];
		
		for (int i=1; i<zeilen.length; i++) {
			String zeile = zeilen[i];
			boolean[] tmpZeile = new boolean[zeile.length()];
			
			for (int j = 0; j<tmpZeile.length; j++) {
				char wert = zeile.charAt(j);
				if (wert == '#') {
					tmpZeile[j] = false;
				} else if (wert == ' ') {
					tmpZeile[j] = true;
				} else if (wert == 'K') {
					tmpZeile[j] = true;
					startX = i-1;
					startY = j;
				}
			}
			tmpIrrweg[i-1] = tmpZeile;
		}
		irrweg = tmpIrrweg;
	}
	
	
	public static void main(String[] args) {
		generateIrrwegFromInputString("6 9\n#########\n#  #    #\n#  # #  #\n#  K #  #\n#    #  #\n#########");
		
		for(int i = 0; i < irrweg.length;i++)
			for(int j = 0; j < irrweg[0].length; j++)
				if(irrweg[i][j] == true)
					max++;
		
		path = new String[max];

		irrweg[startX][startY]=false;
		loese(startX,startY,0);
	}
	
	static boolean go = true;
	private static void loese(int x, int y,int t){

		if(t==max-1){
			go = false;
			for(int i = 0; i < t; i++)
			System.out.println(path[i]);
		}
		if(irrweg[x][y+1] && go){
			irrweg[x][y+1] = false;
			path[t]="O";
			loese(x,y+1,t+1);
			irrweg[x][y+1] = true;
		}
		if(irrweg[x-1][y] && go){
			irrweg[x-1][y] = false;
			path[t]="N";
			loese(x-1,y,t+1);
			irrweg[x-1][y] = true;
		}
		if(irrweg[x][y-1] && go){
			irrweg[x][y-1] = false;
			path[t]="W";
			loese(x,y-1,t+1);
			irrweg[x][y-1] = true;
		}
		if(irrweg[x+1][y] && go){
			irrweg[x+1][y] = false;
			path[t]="S";
			loese(x+1,y,t+1);
			irrweg[x+1][y] = true;
		}	
	}
}
