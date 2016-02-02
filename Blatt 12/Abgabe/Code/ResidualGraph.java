import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;


public class ResidualGraph {
    //Iterierbare Liste der Knoten
    Vector<Vertex> vertexs;
    //Zuordnung Name -> Knoten
    HashMap<String, Vertex> map;

    /**
     * Constructor
     */
    public ResidualGraph() {
        this.vertexs = new Vector<>();
        this.map = new HashMap<>();
    }

    //////////////////////////////////////////////////////////

    public static void main(String... args) {
        System.out.println("Tailaufgabe b)");
        ResidualGraph test = new ResidualGraph();
        test.addVertex("s");
        for (int i = 0; i < 6; i++)
            test.addVertex(i + "");
        test.addVertex("t");
        test.addEdge("0", "1", 5, 5);
        test.addEdge("0", "3", 7, 7);
        test.addEdge("1", "t", 11, 5);
        test.addEdge("2", "3", 13, 6);
        test.addEdge("2", "5", 7, 3);
        test.addEdge("3", "t", 13, 13);
        test.addEdge("4", "5", 5, 0);
        test.addEdge("5", "t", 11, 3);
        test.addEdge("s", "0", 13, 12);
        test.addEdge("s", "2", 13, 9);
        test.addEdge("s", "4", 13, 0);
        System.out.println("Ausgangssituation");
        test.print();

        test.findMaxFlow("s", "t");
        System.out.println("Ergebnis");

        test.print();

        System.out.println("Tailaufgabe c)");
        System.out.println("Minimaler Schnitt");
        Vector<Edge> cut = test.findMinCut("s", "t");
        //int sum = 0;
        for (Edge e : cut) {
            System.out.println(e.toString());
            //sum += e.capacity;
        }

        System.out.println("Tailaufgabe d)");
        test = new ResidualGraph();
        test.addVertex("s");
        for (int i = 0; i < 6; i++)
            test.addVertex(i + "");
        test.addVertex("t");
        test.addEdge("0", "1", 1);
        test.addEdge("0", "3", 1);
        test.addEdge("1", "t", 1);
        test.addEdge("2", "3", 1);
        test.addEdge("2", "5", 1);
        test.addEdge("3", "t", 1);
        test.addEdge("4", "5", 1);
        test.addEdge("5", "t", 1);
        test.addEdge("s", "0", 1);
        test.addEdge("s", "2", 1);
        test.addEdge("s", "4", 1);
        System.out.println("Ausgangssituation");

        test.print();

        test.findMaxFlow("s", "t");
        System.out.println("Ergebnis");

        test.print();

        System.out.println("Minimaler Schnitt");
        cut = test.findMinCut("s", "t");
        //sum = 0;
        for (Edge e : cut) {
            System.out.println(e.toString());
            //sum += e.capacity;
        }
    }

    //////////////////////////////////////////////////////////

    /**
     *
     * @param name name of the vertex
     * @return true if successful, false if name is already in use
     */
    public boolean addVertex(String name) {
        Vertex u = new Vertex(name);

        //Prüfe, ob Name bereits vergeben
        if (this.map.containsKey(name))
            return false;

        this.map.put(name, u);
        this.vertexs.add(u);
        return true;
    }

    /**
     * Erzeugt Kante ohne vorhandenen Fluss
     * @param start name of the start vertex
     * @param end name of the end vertex
     * @param capacity max. capacity of the edge
     */
    public void addEdge(String start, String end, int capacity) {
        map.get(start).addEdge(map.get(end), capacity);
    }

    /**
     * Erzeugt Kante mit Fluss
     * @param start name of the start vertex
     * @param end name of the end vertex
     * @param capacity max. capacity of the edge
     * @param used used capacity of the edge
     */
    public void addEdge(String start, String end, int capacity, int used) {
        map.get(start).addEdge(map.get(end), capacity, used);
    }

    /**
     * Tiefensuche, welche einen Pfad von start nach end sucht
     * @param start name of source
     * @param end name of the sink
     * @return LinkedList with a path from source to sink or null if no path exists
     */
    private LinkedList<Vertex> findPath(String start, String end) {
        if (start.equals(end))
            return new LinkedList<>();

        //Tiefensuche
        HashSet<Vertex> visited = new HashSet<>();
        LinkedList<Vertex> stack = new LinkedList<>();
        HashMap<Vertex, Vertex> pi = new HashMap<>();

        Vertex origin = this.map.get(start);
        Vertex sink = this.map.get(end);

        visited.add(origin);
        pi.put(origin, null);

        stack.push(origin);

        while (stack.size() > 0) {
            Vertex u = stack.pop();
            Vector<Edge> edges = u.edges;
            edges.addAll(u.restEdges);
            for (int i = 0; i < edges.size(); i++) {
                Edge toNext = edges.get(i);
                if (toNext.capacity <= 0)
                    continue;
                Vertex next = toNext.getEnd();
                if (!visited.contains(next)) {
                    visited.add(next);
                    pi.put(next, u);
                    stack.push(next);
                }

                //Stoppe, wenn sink erreicht
                if (next == sink) {
                    LinkedList<Vertex> temp = new LinkedList<>();
                    temp.push(next);
                    while (pi.get(next) != null) {
                        next = pi.get(next);
                        temp.push(next);
                    }
                    return temp;
                }
            }
        }
        return null;
    }

    /**
     * Suche des minimalen Schnitts über eine Breitensuche
     * @param start name of the source node
     * @param end name of the sink node
     * @return A Vector of the edges, which are connecting partitions S and T.
     */
    public Vector<Edge> findMinCut(String start, String end) {
        if (start.equals(end))
            return new Vector<>();

        //Erzeuge maximalen Fluss
        this.findMaxFlow(start, end);

        //Breitensuche
        HashSet<Vertex> visited = new HashSet<>(); //entspricht S
        LinkedList<Vertex> queue = new LinkedList<>();

        Vertex origin = this.map.get(start);

        visited.add(origin);

        queue.add(origin);

        while (queue.size() > 0) {
            Vertex u = queue.poll();
            Vector<Edge> edges = u.edges;
            edges.addAll(u.restEdges);
            for (int i = 0; i < edges.size(); i++) {
                Edge toNext = edges.get(i);
                if (toNext.capacity <= 0)
                    continue;
                Vertex next = toNext.getEnd();
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.add(next);
                }
            }
        }

        //Teile Knoten in jene von S im residual Graphen erreichbare und nicht erreichbare ein
        Vector<Vertex> setT = new Vector<>();
        for (Vertex v : this.vertexs) {
            if (!visited.contains(v))
                setT.add(v);
        }

        //Sammel alle Kanten, welche die beiden Partitionen verbinden
        Vector<Edge> cut = new Vector<>();
        for (Vertex v : visited) {
            for (Vertex u : setT) {
                Edge temp = v.getEdge(u);
                if (temp != null)
                    cut.add(temp);
            }
        }

        return cut;


    }

    /**
     * Find max flow
     * @param start name of the source
     * @param end name of the sink
     */
    public void findMaxFlow(String start, String end) {
        if (start.equals(end)) {
            System.out.println("Start == End");
            return;
        }
        //Finde einen flussverbessernden Pfad
        LinkedList<Vertex> p = this.findPath(start, end);

        // Solange ein flussverbessernder Pfad existiert
        while (p != null) {
            int c = Integer.MAX_VALUE;
            LinkedList<Edge> pTemp = new LinkedList<>();
            Vertex last = p.removeFirst();

            //Finde die kleinste Kapazität
            while (!p.isEmpty()) {
                Vertex next = p.removeFirst();
                Edge edge = last.getEdge(next);
                c = Math.min(edge.capacity, c);
                pTemp.add(edge);
                last = next;
            }

            // aktualisiere den Durchfluss
            while (!pTemp.isEmpty()) {
                Edge edge = pTemp.removeFirst();
                edge.capacity -= c;
                edge.partner.capacity += c;
            }
            p = this.findPath(start, end);
        }
    }

    /**
     * Erzeugt String zur grafischen Ausgabe
     * @return String with adjacency matrix
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("from\\to\t");
        for (int i = 0; i < this.vertexs.size(); i++) {
            out.append("  ");
            out.append(this.vertexs.get(i).toString());
            out.append("  ");
            out.append('\t');
        }
        out.append('\n');
        for (int i = 0; i < this.vertexs.size(); i++) {
            out.append("  ");
            out.append(this.vertexs.get(i).toString());
            out.append("  ");
            out.append('\t');
            Vertex u = this.vertexs.get(i);
            for (int j = 0; j < this.vertexs.size(); j++) {
                Vertex v = this.vertexs.get(j);
                Edge edge = u.getEdge(v);
                if (i == j) {
                    out.append("  -  ");
                    out.append('\t');
                } else if (edge != null && !edge.isRest) {
                    out.append(" ");
                    out.append(edge.CAP - edge.capacity);
                    out.append('/');
                    out.append(edge.CAP);
                    out.append('\t');
                } else {
                    out.append("  ");
                    out.append('-');
                    out.append("  ");
                    out.append('\t');
                }
            }
            out.append('\n');
        }
        return out.toString();
    }

    /**
     *  Prints this.toString()
     */
    public void print() {
        System.out.println(this.toString());
    }

    ///////////////////////////////////////////////////////////

    private class Vertex {
        Vector<Edge> edges;
        Vector<Edge> restEdges;
        String name;

        /**
         *
         * @param name name of the vertex
         */
        public Vertex(String name) {
            this.edges = new Vector<>();
            this.restEdges = new Vector<>();
            this.name = name;
        }

        /**
         *
         * @param end name of the vertex at the end
         * @param capacity max. capacity of the edge
         * @param used used capacity
         */
        public void addEdge(Vertex end, int capacity, int used) {
            Edge edge = new Edge(this, end, capacity - used, false, capacity);
            Edge restEdge = new Edge(end, this, used, true, capacity);
            this.edges.add(edge);
            end.restEdges.add(restEdge);
            edge.setPartner(restEdge);
            restEdge.setPartner(edge);
        }

        /**
         *
         * @param end name of the vertex at the end
         * @param capacity max. capacity of the vertex
         */
        public void addEdge(Vertex end, int capacity) {
            this.addEdge(end, capacity, 0);
        }

        /**
         *
         * @param end name of the vertex at the end
         * @return the edge with end at the end or null
         */
        public Edge getEdge(Vertex end) {
            for (Edge e : edges) {
                if (e.end == end)
                    return e;
            }
            for (Edge e : restEdges) {
                if (e.end == end)
                    return e;
            }

            return null;
        }

        /**
         *
         * @return hash code
         */
        @Override
        public int hashCode() {
            int hash = 17;
            int mult = 59;
            hash = hash * mult + name.hashCode();
            return hash;

        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Vertex) {
                return this.hashCode() == o.hashCode();
            }
            return false;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    ///////////////////////////////////////////////////////////

    private class Edge {
        final int CAP;
        int capacity;
        Vertex end;
        Edge partner;
        boolean isRest;
        Vertex start;

        /**
         *
         * @param start name of the vertex at the start
         * @param end name of the vertex at the end
         * @param capacity not used capacity of the edge
         * @param isRest true if edge is only in the residual graph
         * @param cap max. capacity of the edge
         */
        public Edge(Vertex start, Vertex end, int capacity, boolean isRest, int cap) {

            this.capacity = capacity;
            this.CAP = cap;
            this.end = end;
            this.isRest = isRest;
            this.start = start;
        }

        /**
         *
         * @return name of the vertex at the end
         */
        public Vertex getEnd() {
            return this.end;
        }

        /**
         *
         * @param partner the "twin edge" in the residual graph
         */
        public void setPartner(Edge partner) {
            this.partner = partner;
        }

        public String toString() {
            if (isRest)
                return end + "-" + start + '\t' + capacity + "/" + CAP;
            return start + "-" + end + '\t' + capacity + "/" + CAP;
        }

        @Override
        public int hashCode() {
            int hash = 42;
            int mult = 43;

            hash = hash * mult + CAP;
            hash = hash * mult + end.hashCode();
            if (isRest) {
                hash = hash * mult + 1;
            } else {
                hash = hash * mult + 0;
            }
            hash = hash * mult + start.hashCode();
            return hash;
        }
    }
}
