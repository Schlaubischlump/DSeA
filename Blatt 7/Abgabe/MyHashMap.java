import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

class Point implements Comparable<Point> {
  public int x,y;

  Point(int a,int b) { 
    x = a;
    y = b;
  }

  @Override
  public int hashCode() {
    long z = x << 16 + y;
    long p = 2147483647L; // Primzahl < 2^31
    long a = 1189436865L; // zuf�llig 0 < a < p
    long b = 1206511853L; // zuf�llig 0 <= b < p
    // hier m = p

    // F�r ein m != p muss da dieser Stelle noch modulo m
    // gerechnet werden
    return (int)((a * z + b) % p);
  }

  @Override
  public boolean equals(Object obj) {
    Point p = (Point) obj;
    if (x == p.x && y == p.y) return true;
    return false;     
  }

  @Override
  public int compareTo(Point p) {
    if (x < p.x) return -1;
    if (x > p.x) return  1;
    if (y < p.y) return -1;
    if (y > p.y) return  1;
    return 0;
  }
};

public class MapTest {
  public static void main(String[] args) {

    List<Point> P = new LinkedList<Point>();
    P.add(new Point(1,2));
    P.add(new Point(0,0));
    P.add(new Point(1,2));

    // Anlegen der HashMap, welche einen 2D Punkt auf eine Ganzzahl abbildet
    HashMap<Point,Integer> H = new HashMap<Point,Integer>();

    // F�gt den Punkt p mit einem Index der HashMap zu, falls dieser noch
    // nicht enthalten ist.
    int i = 0;
    for(Point p: P) 
      if (!H.containsKey(p))
        H.put(p,i++);

    // Gibt die (Key, Value) Paare aus, die oben hinzugef�gt wurden
    for(Map.Entry<Point,Integer> e: H.entrySet())
      System.out.println(e.getKey().x+" "+e.getKey().y+" "+e.getValue());

    // Selbes f�r die TreeMap
    TreeMap<Point,Integer> T = new TreeMap<Point,Integer>();

    // F�gt den Punkt p mit einem Index der TreeMap zu, falls dieser noch
    // nicht enthalten ist.
    i = 0;
    for(Point p: P) 
      if (!T.containsKey(p))
        T.put(p,i++);

    // Gibt die (Key, Value) Paare aus, die oben hinzugef�gt wurden
    for(Map.Entry<Point,Integer> e: T.entrySet())
      System.out.println(e.getKey().x+" "+e.getKey().y+" "+e.getValue());

  }
}
