import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class BipartiterGraph implements UndirectedGraph{
	HashMap<Integer,Vertex> map = new HashMap<Integer, Vertex>();	//Speichert alle Knoten
	LinkedList<Vertex> list = new LinkedList<Vertex>();				//Warteschlange für die noch zu untersuchenden Knoten
	Vertex beginVertex;												//Ein Knoten, mit dem der Algo beginnt
	
	class Edge{
		Vertex start;		//Speichert seine beiden Knoten
		Vertex end;
		
		public Edge(Vertex start, Vertex end){
			this.start = start;
			this.end = end;
		}
	}

	//Klasse Vertex, Speichert all seine Kanten in edge, speichert seine Prüffarbe und ein value
	class Vertex{

		Vector<Edge> edge = new Vector<Edge>();
		
		String colour;
		int value;
		
		public Vertex(int value){	//Konstruktor mit eindeutigem Key/Value
			this.value = value;
		}
		
		public void addEdge(Edge edge){	//Fügt dem Knoten eine seiner Kanten hinzu
			this.edge.add(edge);
		}	
	}

	public BipartiterGraph(){
    
    }
	
	@Override
	public void insertVertex(int value) {		
		Vertex vertex = new Vertex(value);		//Erzeugt einen Knoten
		this.map.put(value, vertex);			//Fügt einen Knoten der Hashmap hinzu
		if(this.beginVertex == null){			//Falls noch kein Knoten existiert, wird dieser als Startknoten definiert
			this.beginVertex = vertex;			
		}
	}

	@Override
	public void insertEdge(int value1, int value2) {
		Vertex start = this.map.get(value1);	//Sucht einen Endknoten der Kante
		Vertex end = this.map.get(value2);		//Sucht den anderen Endknoten der Kante
		if(start != null && end != null){		//Wenn beide existieren, füge beiden Knoten die Kante hinzu
			Edge edge = new Edge(start, end);
			start.addEdge(edge);
			end.addEdge(edge);
		}
		else									//Ansonsten gibt Hinweis aus, das die Kante nicht im Algo berücksichtigt wird 
			System.out.println(" Mindestens einer der Knotenenden der Kante \" "+ value1 +"---" + value2 + " \"  existiert nicht! \n Diese Kante wird nicht dem Graphen hinzugefügt!");
	}

	@Override
	public boolean isBipartite() {
		this.beginVertex.colour = "blue";	//Setzt den Anfangsknoten auf eine bestimmte Farbe (blue)
		list.add(this.beginVertex);			//Fügt den ersten Knoten der Warteschlange hinzu
		Vertex next;						//Hilfsvariablen
		Vertex currentVertex = beginVertex;
		if(!this.map.isEmpty()){			//Wenn ein Graph existiert, starte 
			do{
				currentVertex = list.getFirst();	//Der erste Knoten wird aus der Liste genommen
				
				String colour = currentVertex.colour;				
				for(Edge edge : currentVertex.edge){	//Iteriert über alle Kanten des derzeitigen Knotens
					if(!edge.start.equals(currentVertex) || !edge.end.equals(currentVertex)){	//
							
						if(edge.start.equals(currentVertex)){	//Bestimme den Nachbarknoten, schließe den derzeitigenn Knoten als Nachbarknoten aus
							next = edge.end;
						}else
							next = edge.start;
						
						if(next.colour == null)		//Wenn der Knoten bisher noch nciht in der Warteschlange gewesen ist, d.h. die Farbe ist noch null, füge den Knoten dort ein
							list.add(next);
						
						if(next.colour == colour)	//Wenn die Farbe zu dem derzeitigen Knoten gleich ist, haben zwei Nachbarknoten die gleihce Farbe => kien Bipartiter Graph
							return false;
						else if(currentVertex.colour.equals("red"))	//Ansonsten färbe den Knoten in der zum currentVertex verschiedenen Farbe
							next.colour = "blue";
						else
							next.colour = "red";
					}
				}
				list.remove(currentVertex);	//Wenn alle Kanten abgehackt sind, lösche den derzeitigen Knoten aus der Liste(Listenanfang)
			}while(!list.isEmpty());		//Wenn die Warteschlange leer ist und bisher kein Konflikt enstanden ist, gebe true zurück. 
		}		
		return true;
	}
	
	public static void main(String[] args) {
		BipartiterGraph i = new BipartiterGraph();		//Der Konstruktor baut den Graphen auf und überprüft ihn. Funktionen müssen nicht static sein
	}

}
