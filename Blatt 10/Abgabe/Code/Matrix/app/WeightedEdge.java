package app;

// just because java sucks
class WeightedEdge implements Comparable<WeightedEdge>{

    double weight;
    Tuple edge;

    WeightedEdge (double weight, Tuple edge){

        this.weight = weight;
        this.edge = edge;
    }

    public int compareTo(WeightedEdge edge){
        return (int) Math.signum(this.weight - edge.weight);
    }
}


