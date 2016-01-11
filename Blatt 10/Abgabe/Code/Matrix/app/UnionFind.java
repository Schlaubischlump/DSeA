package app;

class UnionFind extends AbstractUnionFind {

    int[] ref;
    int[] size;
    int[] next;

    UnionFind(int n){
        ref = new int[n];
        size = new int[n];
        next = new int[n];

        for (int i = 0; i < n; i++) {
            ref[i] = i;
            size[i] = 1;
            next[i] = -1;
        }   
}

    public int find(int repr){

        // here should be something intelligent
        return ref[repr];
    }

    public boolean union(int i, int j){

        // here should also be something intelligent

        int x = ref[i];
        int y = ref[j];
        
        if (size[x] > size[y]) {
            x = ref[j];
            y = ref[i];
        }

        int h = next[y];
        next[y] = x;
        int z = y;

        while (next[z] >= 0) {
            z = next[z];
            ref[z] = y;
        }

        next[z] = h;
        size[y] = size[y] + size[x];

        return true;
    }
}
