import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

class Edge {
	int capacity;
	final int CAP;
	Vertex end;
	Edge partner;
	boolean isRest;
    Vertex start;

	public Edge (Vertex start, Vertex end, int capacity, boolean isRest, int cap) {

        this.capacity = capacity;
		this.CAP = cap;
		this.end = end;
		this.isRest = isRest;
        this.start = start;
	}

	/*public int getCapacity() {
		return this.capacity;
	}*/

	public Vertex getEnd() {
		return this.end;
	}

	public void setPartner(Edge partner) {
		this.partner = partner;
	}

    public String toString() {
        if (isRest)
            return end+"-"+start+'\t'+capacity+"/"+CAP;
        return start+"-"+end+'\t'+capacity+"/"+CAP;
    }
}

class Vertex {
	static int idCount = 0;
	Vector<Edge> edges;
	Vector<Edge> restEdges;
	int id;

	public Vertex() {
		this.edges = new Vector<>();
		this.restEdges = new Vector<>();
		this.id = idCount++;
	}

	public void addEdge(Vertex end, int capacity, int used) {
		Edge edge = new Edge(this, end, capacity-used, false, capacity);
		Edge restEdge = new Edge(end, this, used, true, capacity);
		this.edges.add(edge);
		end.restEdges.add(restEdge);
		edge.setPartner(restEdge);
		restEdge.setPartner(edge);
	}

	public void addEdge(Vertex end, int capacity) {
		this.addEdge(end, capacity, 0);
	}

	public Edge getEdge(Vertex end) {
		for ( Edge e : edges) {
			if (e.end == end)
				return e;
		}
		for ( Edge e : restEdges) {
			if (e.end == end)
				return e;
		}

		return null;
	}
	
	@Override
	public int hashCode() {
		//return edges.hashCode()+restEdges.hashCode()+id;
		return id;
	}

	@Override
	public boolean equals(Object o){
        if (o instanceof Vertex) {
            return this.hashCode() == o.hashCode();
        }
		return false;
	}

    public String toString() {
        return id+"";
    }
}

public class Graph {
	Vector<Vertex> vertexs;

	public Graph() {
        Vertex.idCount = 0;
		this.vertexs = new Vector<>();
	}

	/*public Graph(Vector<Vertex> vertexs) {
		this.vertexs = vertexs;
	}*/

	public boolean addVertex() {
		return vertexs.add(new Vertex());
	}

	/*public boolean addVertex(Vertex vertex) {
		return vertexs.add(vertex);
	}*/

	public void addEdge(int start, int end, int capacity) {
		vertexs.get(start).addEdge(vertexs.get(end), capacity);
	}
	
	public void addEdge(int start, int end, int capacity, int used) {
		vertexs.get(start).addEdge(vertexs.get(end), capacity, used);
	}

	private LinkedList<Vertex> findPath(int start, int end) {
		if (start == end)
			return new LinkedList<>();

		HashSet<Vertex> visited = new HashSet<>();
		LinkedList<Vertex> stack = new LinkedList<>();
		HashMap<Vertex,Vertex> pi = new HashMap<>();

		Vertex origin = this.vertexs.get(start);
		Vertex sink = this.vertexs.get(end);

		visited.add(origin);
		pi.put(origin, null);

		stack.push(origin);

		while(stack.size() > 0) {
			//System.out.println("Der Stack ist noch "+stack.size()+" Elemente groß!");
			Vertex u = stack.pop();
			Vector<Edge> edges = u.edges;
			edges.addAll(u.restEdges);
			for (int i = 0; i < edges.size(); i++) {
				Edge toNext = edges.get(i);
				if (toNext.capacity <= 0)
					continue;
				Vertex next = toNext.getEnd();
				if(!visited.contains(next)) {
					visited.add(next);
					pi.put(next, u);
					stack.push(next);
				}
				if(next == sink) {
					LinkedList<Vertex> temp = new LinkedList<>();
					temp.push(next);
					while (pi.get(next) != null) {
						//System.out.println(next.id);
						next = pi.get(next);
						temp.push(next);
						//System.out.println("Erzeuge Pfad");
					}
					return temp;
				}
			}
		}
		return null;
	}

	public Vector<Edge> findMinCut(int start, int end) {
        this.fordFulkerson(start, end);

        if (start == end)
            return new Vector<>();

        HashSet<Vertex> visited = new HashSet<>(); //entspricht S
        LinkedList<Vertex> queue = new LinkedList<>();
        //HashMap<Vertex,Vertex> pi = new HashMap<>();

        Vertex origin = this.vertexs.get(start);

        visited.add(origin);
        //pi.put(origin, null);

        queue.add(origin);

        while(queue.size() > 0) {
            //System.out.println("Der Stack ist noch "+stack.size()+" Elemente groß!");
            Vertex u = queue.poll();
            Vector<Edge> edges = u.edges;
            edges.addAll(u.restEdges);
            for (int i = 0; i < edges.size(); i++) {
                Edge toNext = edges.get(i);
                if (toNext.capacity <= 0)
                    continue;
                Vertex next = toNext.getEnd();
                if(!visited.contains(next)) {
                    visited.add(next);
                    //pi.put(next, u);
                    queue.add(next);
                }
            }
        }
        Vector<Vertex> setT = new Vector<>();
        for( Vertex v : this.vertexs) {
            if(!visited.contains(v))
                setT.add(v);
        }
        Vector<Edge> cut = new Vector<>();
        for( Vertex v: visited) {
            for (Vertex u : setT) {
                Edge temp = v.getEdge(u);
                if (temp!=null)
                    cut.add(temp);
            }
        }

        return cut;


    }

	public void fordFulkerson(int start, int end) {
		if (start == end){
			System.out.println("Start == End");
			return;
		}
		LinkedList<Vertex> p = this.findPath(start, end);
		while(p != null){
			int c = Integer.MAX_VALUE;
			LinkedList<Edge> pTemp = new LinkedList<>();
			Vertex last = p.removeFirst();
			while (!p.isEmpty()) {
				Vertex next = p.removeFirst();
				Edge edge = last.getEdge(next);
				c = Math.min(edge.capacity, c);
				pTemp.add(edge);
				last = next;
			}

			while(!pTemp.isEmpty()) {
				Edge edge = pTemp.removeFirst();
				edge.capacity -= c;
				edge.partner.capacity += c;
			}
			p = this.findPath(start, end);
		}
	}
	
	
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("from\\to\t");
		for (int i = 0; i < this.vertexs.size(); i++) {
            out.append("  ");
			out.append(i);
            out.append("  ");
			out.append('\t');
		}
		out.append('\n');
		for (int i = 0; i < this.vertexs.size(); i++) {
            out.append("  ");
            out.append(i);
            out.append("  ");
			out.append('\t');
			Vertex u = this.vertexs.get(i);
			for (int j = 0; j < this.vertexs.size(); j++) {
				Vertex v = this.vertexs.get(j);
				Edge edge = u.getEdge(v);
				if (i == j ) {
					out.append("  -  ");
					out.append('\t');
				}else	if (edge != null && !edge.isRest) {
                    out.append(" ");
					out.append(edge.CAP-edge.capacity);
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
	
	public void print() {
		System.out.println(this.toString());
	}

	public static void main(String... args) {
		System.out.println("Es sei s = 6 und t = 7" );
		System.out.println("Tailaufgabe b)");
		Graph test = new Graph();
		for (int i = 0; i < 8; i++)
			test.addVertex();
		test.addEdge(0, 1, 5, 5);
		test.addEdge(0, 3, 7, 7);
		test.addEdge(1, 7, 11, 5);
		test.addEdge(2, 3, 13, 6);
		test.addEdge(2, 5, 7, 3);
		test.addEdge(3, 7, 13, 13);
		test.addEdge(4, 5, 5, 0);
		test.addEdge(5, 7, 11, 3);
		test.addEdge(6, 0, 13, 12);
		test.addEdge(6, 2, 13, 9);
        test.addEdge(6, 4, 13, 0);
        System.out.println("Ausgangssituation");
		test.print();

        test.fordFulkerson(6, 7);
        System.out.println("Ergebnis");
		
		test.print();

        System.out.println("Minimaler Schnitt");
        Vector<Edge> cut = test.findMinCut(6,7);
        for (Edge e : cut)
            System.out.println(e.toString());
		

		System.out.println("Tailaufgabe d)");
		test = new Graph();
		for (int i = 0; i < 8; i++)
			test.addVertex();
		test.addEdge(0, 1, 1);
		test.addEdge(0, 3, 1);
		test.addEdge(1, 7, 1);
		test.addEdge(2, 3, 1);
		test.addEdge(2, 5, 1);
		test.addEdge(3, 7, 1);
		test.addEdge(4, 5, 1);
		test.addEdge(5, 7, 1);
		test.addEdge(6, 0, 1);
		test.addEdge(6, 2, 1);
        test.addEdge(6, 4, 1);
        System.out.println("Ausgangssituation");
		
		test.print();

        test.fordFulkerson(6, 7);
        System.out.println("Ergebnis");
		
		test.print();

        System.out.println("Minimaler Schnitt");
        cut = test.findMinCut(6,7);
        for (Edge e : cut)
            System.out.println(e.toString());
	}
}