public class Suche1 {

	static int[][] schrank = {{2,2,1,2,1,0,2,0,1,2,2,1,0,0,0,0},
		{2,1,0,2,0,2,1,0,0,1,0,2,0,2,0,2}};
	static int l = 3;	//Anzahl der Muster
	static int k = 3;	//Anzahl der Farben
	static int n = 8;	//Anzahl der Sockenpaare
	
	public static void main(String[] args){
		//Erstelle 2xk Array, erste  Zeile kommen die Anzahl der einzelnen Farben rein, zweite Zeile der Index vom Pointer
		int[][] socke = new int[2][k];
		
		//Die Anzahl der Farben werden ermittelt
		for(int i = 0; i < 2*n; i++)
			socke[0][schrank[0][i]]++;
		
		//Die Anfangsindezes der Farben für das Hauptarray werden berechnet und abgespeichert 
		for(int i = 1; i < k; i++)
			socke[1][i] = socke[0][i-1]+socke[1][i-1]; 

		int i = 0;
		int farbe = 0;	//Die Farbe 0 wird ausgewählt
		int index = socke[1][farbe+1];	//Der Anfangsindex für die nächste Farbe/Endindex für die erste Farbe wird ausgewählt
		int var = schrank[0][i];	//derzeitige Farbe aus dem Schrank, die verglichen und einsortiert wird
		
		//Die Farben werden von 0-k in der richtigen Reihenfolge sortiert.
		while(i < n){

			//Wenn die Socken für die eine Farbe schon eingeordnet sind, geht es weiter zur nächsten Farbe 
			if(i >= index){
				farbe++;
				index = index + socke[0][farbe];
			}
			
			//Wenn die Farben schon an der richtigen stelle stehen, wird der Laufindex erhöht
			while(i < 2*n && var == farbe){
				i++;
				var = schrank[0][i];
			}
			
			//Solange der Laufindex nicht bei der nächsten Farbe ankommt, wird getauscht. Die Farben, die nicht zur (wenn farbe = 0 ausgewählt ist) 0-ten Farbe gehören, werden in den richtige Farbenteil getauscht 
			if(i < index && i<n){	
				swap(i,socke[1][var],schrank);	//Tauscht farbe an der Position i mit einer Farbe im Zielbereich
				socke[1][var]++;				//Pointer wird erhöht
				var = schrank[0][i];			//die Farbe, auf die der Pointer zeigt, wird aktualisiert
			}
		}
		
		//Anzeige des Arrays
		System.out.println("Nur nach Farben sortiert: ");
		for(int b = 0; b < 4*n; b++){
			if(b<2*n)System.out.print(schrank[0][b]+" ");
			if(b == 2*n) System.out.println("");
			if(b>=2*n)System.out.print(schrank[1][b % (2*n)]+" ");
		}
		System.out.println("");
		
		//Die Anfangsindezes der Farben für das Hauptarray werden berechnet und abgespeichert 
		for( i = 1; i < k; i++)
			socke[1][i] = socke[0][i-1]+socke[1][i-1]; 
		
		//in dem Array wird in der ersten Spalte die Anzahl der jeweiligen Muster in einer Farbe gespeichert, in der zweiten Spalte die Anfangspointer im Gesamtarray
		int[][] musteranzahl;
		int j = 0;	//äußerer Schleifenzähler
		
		while(j < k){	//Wie viele Farben es gibt, so oft wird die Schleife durchlaufen
			
			int muster = 0;
			musteranzahl = new int[2][l];

			//Bestimme die Anzahl der jeweiligen Muster
			for(i = socke[1][j]; i < socke[1][j] + socke[0][j]; i++){
				int o = schrank[1][i];
				musteranzahl[0][o]++;
			}
			
			//Ende des Teilarrays des (ersten) Musters in der (ersten) Farbe
			index = musteranzahl[0][0] + socke[1][j]; 
			
			i = socke[1][j];	//Es wird einmal durch den Bereich des Arrays einer Farbe durchiteriert
			musteranzahl[1][0] = i;
			
			//Bestimme die Laufindizes der Einzelnen Muster in dem Teilarray von Schrank
			for(int m = 0; m < l-1; m ++ ){
				musteranzahl[1][m+1] = musteranzahl[0][m] + musteranzahl[1][m];	
			}
			

			while(i < socke[1][j] + socke[0][j]){		
				if(i >= index){
					muster++;
					index = index + musteranzahl[0][muster];
				}
	
				//Wenn das Muster schon an der richtigen stelle stehen, wird der Laufindex erhöht
				while(i < socke[1][j] + socke[0][j] && schrank[1][i] == muster)
					i++;
				
				//Solange der Laufindex nicht bei dem nächsten Muster ankommt, wird getauscht. Das Muster, das nicht zur (wenn muster = 0 ausgewählt ist) 0-ten Muster gehören, werden in das richtige Musterteil getauscht 
				if(i < index){		
					int musterindex = schrank[1][i];
					swap(i,musteranzahl[1][musterindex],schrank);
					musteranzahl[1][musterindex]++;
				}
			}
			j++;
			if(j==2)
				System.out.println("h");
		}
		
		//Anzeige des Arrays
		System.out.println("");
		System.out.println("nach Farben und Muster sortiert: ");
		for(int b = 0; b < 4*n; b++){
			if(b<2*n)System.out.print(schrank[0][b]+" ");
			if(b == 2*n) System.out.println("");
			if(b>=2*n)System.out.print(schrank[1][b % (2*n)]+" ");	
		}	
	}
	
	//Vertauschung der Elemente vom Indize i mit j
	private static void swap(int i, int j, int[][] schrank2) {
		int a = schrank2[0][i];	//Werte zwischenspeichern
		int b = schrank2[1][i];
		schrank2[0][i] = schrank2[0][j];	
		schrank2[1][i] = schrank2[1][j];
		schrank2[0][j] = a;
		schrank2[1][j] = b;
	}
}