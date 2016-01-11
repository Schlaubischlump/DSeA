package app;

abstract class AbstractUnionFind {

    AbstractUnionFind(){}
    abstract int find(int i);
    abstract boolean union(int i, int j);

}
