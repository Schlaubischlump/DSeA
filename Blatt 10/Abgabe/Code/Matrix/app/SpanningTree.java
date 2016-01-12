package app;

// default java stuff
import java.util.Vector;
import java.util.Collections;

class SpanningTree {

    static Vector<Tuple> kruskal (Vector<Triple> cities, int cityCount){
        
        // final mst and vector of all edges
        Vector<Tuple> mst = new Vector<Tuple>();
        Vector<WeightedEdge> edges = new Vector<WeightedEdge>();
        Vector<WeightedEdge> virtualCities = new Vector<WeightedEdge>();
        UnionFind uf = new UnionFind(cityCount);
        // here should be something intelligent

        for (int i = 0; i < cityCount; i++) {
            for (int j = 0; j < cityCount; j++) {
                if ( i != j) {
                    edges.add(new WeightedEdge(euclid(cities.get(i),cities.get(j)), new Tuple(i,j)));
                }
            }
            virtualCities.add(new WeightedEdge(euclid(cities.get(i),cities.get(i+cityCount)),new Tuple(i,i+cityCount)));
        }

        // sort all edges 
        Collections.sort(edges);

        // here should also be something intelligent
        for (int i = 0; i < edges.size(); i++) {
            int u = edges.get(i).edge.x;
            int v = edges.get(i).edge.y;
            if (uf.find(u) == uf.find (v))
                continue;
            mst.add(edges.get(i).edge);
            uf.union(u,v);
            //Vielleicht noch eine HashMap<Tripel, int> erzeugen, welche die Nächste virtuelle Stadt für eine Partition speichert

        }
        return mst;
    }

    static double euclid(Triple a, Triple b){
        
        return (a.x - b.x) * (a.x - b.x) +  
               (a.y - b.y) * (a.y - b.y) +
               (a.z - b.z) * (a.z - b.z);
    }

}
