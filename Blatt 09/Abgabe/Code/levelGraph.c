forall ( v in V \{ S }) {
    col[v] = white ;
    d[v] = infinity ;
}

List kanten = [];
col[s] = grey ;
d[s] = 0;
Queue Q;
Q.push ( s );
while (!Q.empty ()) {
    u = Q.pop();
    forall ((u , v ) in E) {
        if (d[u] < d[v])
            kanten.add((u,v));
        
        if (col[v] == white) {
            col[v] == grey ;
            d[v] = d[u]+ 1 ;
            Q.push(v);
        }
    }
    col[u] = black ;
}
