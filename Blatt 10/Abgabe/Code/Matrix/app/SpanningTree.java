package app;

// default java stuff
import java.util.Vector;
import java.util.Collections;

class SpanningTree {

    static Vector<Tuple> kruskal (Vector<Triple> cities, int cityCount){
        
        // final mst and vector of all edges
        Vector<Tuple> mst = new Vector<Tuple>();
        Vector<WeightedEdge> edges = new Vector<WeightedEdge>();

        // here should be something intelligent

        // sort all edges 
        Collections.sort(edges);

        // here should also be something intelligent

        return mst;
    }

    static double euclid(Triple a, Triple b){
        
        return (a.x - b.x) * (a.x - b.x) +  
               (a.y - b.y) * (a.y - b.y) +
               (a.z - b.z) * (a.z - b.z);
    }

}
