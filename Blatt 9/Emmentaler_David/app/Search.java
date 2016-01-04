package app;

// java default stuff
import java.util.Vector;
import java.util.ArrayList;
import java.util.Stack;
import java.util.LinkedList;
import java.util.HashMap;

class Search{

    public static ArrayList<Tuple> getPossibleNeighboursForOrigin(boolean [][][] cheese, Tuple origin)
    {
        ArrayList<Tuple> edges = new ArrayList<Tuple>();
        int x = origin.zero,y = origin.one, z = origin.two;
        
        for (int i = x-1; i<= x+2; i++)
            if ((i >= 0 && i < 40) && i != x && !cheese[i][y][z])
                edges.add(new Tuple(i, y, z));
        
        for (int i = y-1; i<= y+2; i++)
            if ((i >= 0 && i < 40) && i != y &&!cheese[x][i][z])
                edges.add(new Tuple(x, i, z));
        
        for (int i = z-1; i<= z+2; i++)
            if ((i >= 0 && i < 40) && i != z &&  !cheese[x][y][i])
                edges.add(new Tuple(x, y, i));
        
        return edges;
    }
    
    public static Vector<Tuple> BFS (boolean [][][] cheese, Tuple origin){
        boolean visited[][][] = new boolean[40][40][40];
        HashMap<Tuple, Tuple> pi = new HashMap<Tuple, Tuple>(); // Vorgängerknoten
        Vector<Tuple> v = new Vector<Tuple>();
        LinkedList<Tuple> queue = new LinkedList<Tuple>();
        
        pi.put(origin, null); //Anfangselement hat keinen Vorgänger
        visited[origin.zero][origin.one][origin.two] = true;
        
        
        queue.add(origin);
        while (queue.size() > 0) {
            Tuple u = queue.poll();
            // Prüfe ob einer der Nachbarn verfügbar ist
            for (Tuple t : getPossibleNeighboursForOrigin(cheese, u)) {
                int x = t.zero,y = t.one, z = t.two;
                
                if (visited[x][y][z] == false) {
                    visited[x][y][z] = true;
                    pi.put(t, u);
                    queue.add(new Tuple(x,y,z));
                }
                
                // Boden erreicht
                if (y == 0) {
                    Tuple next = t;
                    v.add(next);
                    while (pi.get(next) != null) {
                        t = pi.get(next);
                        v.add(next);
                    }
                }
            }
        }
        return v;
    }

    
    public static Vector<Tuple> DFS (boolean [][][] cheese, Tuple origin){
        boolean visited[][][] = new boolean[40][40][40];
        HashMap<Tuple, Tuple> pi = new HashMap<Tuple, Tuple>(); // Vorgängerknoten
        Vector<Tuple> v = new Vector<Tuple>();
        Stack<Tuple> s = new Stack<Tuple>();
        
        pi.put(origin, null); //Anfangselement hat keinen Vorgänger
        visited[origin.zero][origin.one][origin.two] = true;
        
        
        s.push(origin);
        while (s.size() > 0) {
            Tuple u = s.pop();
            // Prüfe ob einer der Nachbarn verfügbar ist
            for (Tuple t : getPossibleNeighboursForOrigin(cheese, u)) {
                int x = t.zero,y = t.one, z = t.two;
                
                if (visited[x][y][z] == false) {
                    visited[x][y][z] = true;
                    pi.put(t, u);
                    s.push(new Tuple(x,y,z));
                }
                
                // Boden erreicht
                if (y == 0) {
                    Tuple next = t;
                    v.add(next);
                    while (pi.get(next) != null) {
                        t = pi.get(next);
                        v.add(next);
                    }
                }
            }
        }
        return v;
    }
}
