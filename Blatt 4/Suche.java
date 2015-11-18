public class Suche {

	static int[][] schrank = {{2,2,1,2,1,0,2,0,1,2,2,1,0,0,0,0},
		{2,1,0,2,0,0,1,0,0,1,0,2,0,2,0,2}};
	static int l = 3;	//Anzahl der Muster
	static int k = 3;	//Anzahl der Farben
	static int n = 8;	//Anzahl der Sockenpaare
	
	public static void main(String[] args){
		//Erstelle 3xk Array, erste Zeile sind die Farben, zweite Zeile kommen die Anzahl der einzelnen Farben rein, dritte Zeile der Index vom Pointer
		int[][] socke = new int[k][3];
		
		//Die Anzahl der Farben werden ermittelt
		for(int i = 0; i < 2*n; i++)
			socke[1][schrank[0][i]]++;
		
		//Die Anfangsindezes der Farben für das Hauptarray werden berechnet und abgespeichert 
		for(int i = 1; i < k; i++)
			socke[2][i] = socke[1][i-1]+socke[2][i-1]; 

		int i = 0;
		int farbe = 0;	//Die Farbe 0 wird ausgewählt
		int index = socke[2][farbe+1];	//Der Anfangsindex für die nächste Farbe/Endindex für die erste Farbe wird ausgewählt
		
		//Die Farben werden von 0-k in der richtigen Reihenfolge sortiert.
		while(i < n){

			//Wenn die Socken für die eine FArbe schon eingeordnet sind, geht es wieter zur nächsten Farbe 
			if(i >= index){
				farbe++;
				index = index + socke[1][farbe];
			}

			//Wenn die Farben schon an der richtigen stelle stehen, wird der Laufindex erhöht
			while(i < n && schrank[0][i] == farbe)
				i++;
			
			//Solange der Laufindex nicht bei der nächsten Farbe ankommt, wird getauscht. Die Farben, die nicht zur (wenn farbe = 0 ausgewählt ist) 0-ten Farbe gehören, werden in den richtige Farbenteil getauscht 
			if(i < index){	
			
				int farbeindex = schrank[0][i];			//
				swap(i,socke[2][farbeindex],schrank);	//Tauschen
				socke[2][farbeindex]++;					//Pointer wird erhöht
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
			socke[2][i] = socke[1][i-1]+socke[2][i-1]; 
		
		//in dem Array wird in der ersten Spalte die Anzahl der jeweiligen Muster in einer Farbe gespeichert, in der zweiten Spalte die Anfangspointer im Gesamtarray
		int[][] musteranzahl = new int[2][l];
		int j = 0;	//äußerer Schleifenzähler
		
		while(j < k){	//Wie viele Farben es gibt, so oft wird die Schleife durchlaufen
			
			int muster = 0;
			
			for(int k = 0; k < l; k++)
				musteranzahl[0][k] = 0;	//Die Werte für die Anzahl der jeweiligen Muster werden für die nächste Farbe zurückgesetzt

			//Bestimme die Anzahl der jeweiligen Muster
			for(int k = socke[2][j]; k < socke[2][j] + socke[1][j]; k++)
				musteranzahl[0][schrank[1][k]]++;
			
			//Ende des Teilarrays des (ersten) Musters in der (ersten) Farbe
			index = musteranzahl[0][0] + socke[2][j]; 
			
			//Bestimme die Laufinizes der Einzelnen Muster in dem Teilarray von schrank
			for(i = 0; i < l-1; i ++ ){
				musteranzahl[1][i+1] = musteranzahl[0][i] + socke[2][j];
				
			}
			
			i = socke[2][j];	//Es wird einmal durch den Bereich des Arrays einer Farbe durchiteriert
			while(i < socke[2][j] + socke[1][j]){		

				if(i >= index){
					muster++;
					index = index + musteranzahl[0][muster];
				}
	
				//Wenn das Muster schon an der richtigen stelle stehen, wird der Laufindex erhöht
				while(i < socke[2][j] + socke[1][j] && schrank[1][i] == muster)
					i++;
				
				//Solange der Laufindex nicht bei dem nächsten Muster ankommt, wird getauscht. Das Muster, das nicht zur (wenn muster = 0 ausgewählt ist) 0-ten Muster gehören, werden in das richtige Musterteil getauscht 
				if(i < index){	
				
					int musterindex = schrank[1][i];
					swap(i,musteranzahl[1][musterindex],schrank);
					musteranzahl[1][musterindex]++;
				}
			}
			j++;
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
