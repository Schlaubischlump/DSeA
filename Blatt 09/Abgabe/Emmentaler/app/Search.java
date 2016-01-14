package app;

// java default stuff
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

class Search{

	public static Vector<Tuple> BFS (boolean [][][] cheese, Tuple origin){

		HashMap<Tuple, ArrayList<Tuple>> edges = createHash(cheese.length, cheese);
		HashSet<Tuple> visited = new HashSet<Tuple>();
		LinkedList<Tuple> queue = new LinkedList<Tuple>();	//queue
		HashMap<Tuple, Tuple> pi = new HashMap<Tuple, Tuple>();
		HashMap<Tuple, Integer> d = new HashMap<Tuple, Integer>();  //Distanzfunktion
		visited.add(origin);
		pi.put(origin, null);
		d.put(origin, 0);

		queue.add(origin);	//queue
		while(queue.size() > 0) {
			Tuple u = queue.poll();	//queue
			ArrayList<Tuple> edge = edges.get(u);		
			for ( int i = 0; i < edge.size(); i++) {
				Tuple next = edge.get(i);
				if (!visited.contains(next)) {
					visited.add(next);
					pi.put(next, u);
					queue.add(next);	//queue
					d.put(next, d.get(u)+1);  //Länge des neuen Knoten ist Länge des Entdeckers +1
				}
				if (next.one == 0) {
					Vector<Tuple> temp = new Vector<Tuple>();
					temp.addElement(next);
					System.out.println("BFS: Laenge = "+d.get(next));
					while (pi.get(next) != null) {
						next = pi.get(next);
						temp.addElement(next);
					}
					return temp;
				}
			}
		}
		return new Vector<Tuple>();
	}

	public static Vector<Tuple> DFS (boolean [][][] cheese, Tuple origin){

		HashMap<Tuple, ArrayList<Tuple>> edges = createHash(cheese.length, cheese);
		HashSet<Tuple> visited = new HashSet<Tuple>();
		LinkedList<Tuple> stack = new LinkedList<Tuple>();	//stack
		HashMap<Tuple, Tuple> pi = new HashMap<Tuple, Tuple>();
		HashMap<Tuple, Integer> d = new HashMap<Tuple, Integer>();  //Distanzfunktion
		visited.add(origin);
		pi.put(origin, null);
		d.put(origin, 0);

		stack.push(origin);	//stack
		while(stack.size() > 0) {
			Tuple u = stack.pop();	//stack
			ArrayList<Tuple> edge = edges.get(u);		
			for ( int i = 0; i < edge.size(); i++) {
				Tuple next = edge.get(i);
				if (!visited.contains(next)) {
					visited.add(next);
					pi.put(next, u);
					stack.push(next);	//stack
					d.put(next, d.get(u)+1);  //Länge des neuen Knoten ist Länge des Entdeckers +1
				}
				if (next.one == 0) {
					Vector<Tuple> temp = new Vector<Tuple>();
					temp.addElement(next);
					System.out.println("DFS: Laenge = "+d.get(next));
					while (pi.get(next) != null) {
						next = pi.get(next);
						temp.addElement(next);
					}
					return temp;
				}
			}
		}
		return new Vector<Tuple>();
	}


	//hilfmethode zur Erzeugung der Mashmap der möglichen Züge in einem Puzzle
	private static HashMap<Tuple, ArrayList<Tuple>> createHash(int n, boolean [][][] cheese) {

		//Erstelle Hashmap
		HashMap<Tuple, ArrayList<Tuple>> edges = new HashMap<Tuple, ArrayList<Tuple>>();
		for (int x = 0; x < n; x++)
			for (int y = 0; y < n; y++)
				for ( int z = 0; z < n; z++) {
					ArrayList<Tuple> temp = new ArrayList<Tuple>();
					edges.put(new Tuple(x,y,z), temp);
					if ( x > 0)
						if (!cheese[x-1][y][z])
							temp.add(new Tuple((x-1), y, z));
					if ( x < n-1)
						if (!cheese[x+1][y][z])
							temp.add(new Tuple((x+1), y, z));
					if ( y > 0)
						if (!cheese[x][y-1][z])
							temp.add(new Tuple( x, (y-1), z));
					if ( y < n-1)
						if (!cheese[x][y+1][z])
							temp.add(new Tuple( x, (y+1), z));
					if ( z > 0)
						if (!cheese[x][y][z-1])
							temp.add(new Tuple( x ,y, (z-1)));
					if ( z < n-1)
						if (!cheese[x][y][z+1])
							temp.add(new Tuple( x, y, (z+1)));
				}
		return edges;
	}

}
