package app;

// java default stuff
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;

class Search{

    public static Vector<Tuple> BFS (boolean [][][] cheese, Tuple origin){
    	
    	
    	return new Vector<Tuple>();
    }

    public static Vector<Tuple> DFS (boolean [][][] cheese, Tuple origin){
	return new Vector<Tuple>();
    }
    
    private static Integer encodeTupel ( Tuple tupel, int n) {
    	return tupel.zero*n*n + tupel.one*n + tupel.two;
    }
    
    private static Tuple decodeTupel ( Integer value, int n) {
    	return new Tuple (getX(value,n), getY(value,n), getZ(value,n));
    }
    
    private static int getX (Integer value, int n) {
    	return value / (n * n);
    }
    
    private static int getY (Integer value, int n) {
    	return (value - getX(value, n)*n*n) / n;
    }
    
    private static int getZ ( Integer value, int n) {
    	return (value - getX(value, n) * n * n - -getY(value, n) * n);
    }
    
  //hilfmethode zur Erzeugung der Mashmap der möglichen Züge in einem Puzzle
  	private static HashMap<Integer, ArrayList<Integer>> createHash(int n) {

  		//Erstelle Hashmap
  		HashMap<Integer, ArrayList<Integer>> edges = new HashMap<Integer, ArrayList<Integer>>();
  		for (int x = 0; x < n; x++)
  			for (int y = 0; y < n; y++)
  				for ( int z = 0; z < n; z++) {
  				ArrayList<Integer> temp = new ArrayList<Integer>();
  				edges.put(z+n*y+n*n*x, temp);
  				if ( x > 0)
  					temp.add((x-1)*n*n+y*n+z);
  				if ( x < n-1)
  					temp.add((x+1)*n*n+y*n+z);
  				if ( y > 0)
  					temp.add(x*n*n+(y-1)*n+z);
  				if ( y < n-1)
  					temp.add(x*n*n+(y+1)*n+z);
  				if ( z > 0)
  					temp.add(x*n*n+y*n+(z-1));
  				if ( z < n-1)
  					temp.add(x*n*n+y*n+(z+1));
  				}
  		return edges;
  	}

}
