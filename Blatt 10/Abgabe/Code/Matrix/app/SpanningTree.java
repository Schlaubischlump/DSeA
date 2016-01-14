package app;

// default java stuff
import java.util.Vector;
import java.util.Collections;

class SpanningTree {

    static Vector<Tuple> kruskal (Vector<Triple> cities, int cityCount){
        
        // final mst and vector of all edges
        Vector<Tuple> mst = new Vector<Tuple>();
        Vector<WeightedEdge> edges = new Vector<WeightedEdge>();
        UnionFind uf = new UnionFind(cityCount*2);
        // here should be something intelligent
        //Erstelle Kantenmengen für die Cities untereinander und jeweils zwischen Citiy unt virtual city
        for (int i = 0; i < cityCount; i++) {
            for (int j = 0; j < cityCount; j++) {
                if ( i != j) {
                    edges.add(new WeightedEdge(euclid(cities.get(i), cities.get(j)), new Tuple(i, j)));
                }
            }
            edges.add(new WeightedEdge(euclid(cities.get(i), cities.get(i + cityCount)), new Tuple(i, i + cityCount)));
        }
        // sort all edges 
        Collections.sort(edges);
        //stecke alle virtuellen Cities in eine Partition
        for (int i = 0; i < cityCount-1; i++) {
        		uf.union(i + cityCount, i + 1 + cityCount);
        }
        // here should also be something intelligent
        for (int i = 0; i < edges.size(); i++) {
            int u = edges.get(i).edge.x;
            int v = edges.get(i).edge.y;    
            
            //Wenn schon Verbunden (wenn auch nicht direkt) ist die Kante uninteressant
            if (uf.find(u) == uf.find (v))
                continue;
            
            mst.add(edges.get(i).edge);
            uf.union(u,v);
        }     
        return mst;
    }
    static double euclid(Triple a, Triple b){
        
        return (a.x - b.x) * (a.x - b.x) +  
               (a.y - b.y) * (a.y - b.y) +
               (a.z - b.z) * (a.z - b.z);
    }
}