package app;

// this is how minecraft was done
import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;

import static org.lwjgl.opengl.GL11.*;


// java default stuff
import java.util.*;

public class App {

    // some globals (from here you can ignore the code)
    private static String title = "Matrix Revolutions";
    private static int desiredFPS = 60;
    private static boolean isRunning;
    private static double phi = 0, theta = 0;

    // display lists for fast rendering and status variables
    private static int sphereList;
    private static boolean drawSphere = true;
    private static int cityList;
    private static boolean drawCities = true;
    private static int edgeList;
    private static boolean drawEdges = false;
    private static int mstList;
    private static boolean drawMst = true;

    // city information
    private static Vector<Triple> cities;
    private static int cityCount = 200;

    // mst information
    private static Vector<Tuple> mst;

    // decrease this if your pc is to slow (sphere resolution)
    private static int res = 50; 

    public static void main(String[] args) throws Exception {

        init();
        mainLoop();
        System.exit(0);
    }

    private static void makeSphere() {

        sphereList = glGenLists(1);
        glNewList(sphereList, GL_COMPILE);
        new Sphere().draw(1, res, res);
        glEndList();
    }

    private static void makeCities() {

        // same random for everyone
        Random random = new Random(42);

        // vector of cities
        cities = new Vector<Triple>();
        
        // make the cities look nice
        glEnable(GL_POINT_SMOOTH);

        // display list for cities
        cityList = glGenLists(1);
        glNewList(cityList, GL_COMPILE);
        
        glBegin(GL_POINTS);

        // some cities in the full sphere
        while(cities.size() < cityCount){
            
            Triple temp = new Triple(2 * random.nextDouble() - 1,
                                     2 * random.nextDouble() - 1,
                                     2 * random.nextDouble() - 1);
            
            if (temp.x * temp.x + temp.y * temp.y + temp.z * temp.z <= 0.65 &&
                temp.x * temp.x + temp.y * temp.y + temp.z * temp.z >= 0.25){
                
                // normal cities and surface entries (virtual cities)
                cities.add(temp);

                glNormal3d(temp.x, temp.y, temp.z);
                glVertex3d(temp.x, temp.y, temp.z);
            }
        }

        // virtual city as shortest entry from surface
        for (int i = 0; i < cityCount; i++){
            
            Triple temp = cities.get(i);
            Triple virtual = new Triple(temp.x, temp.y, temp.z);
            double rho = Math.sqrt(temp.x * temp.x +  
                                   temp.y * temp.y + 
                                   temp.z * temp.z);
            virtual.x /= rho;
            virtual.y /= rho;
            virtual.z /= rho;

            cities.add(virtual);
            
            // glNormal3d(virtual.x, virtual.y, virtual.z);
            // glVertex3d(virtual.x, virtual.y, virtual.z);
        }
        glEnd();
        glEndList();
    }

    private static void makeEdges() {

        // make edges look nice
        glEnable(GL_LINE_SMOOTH);
        
        // display list for cities
        edgeList = glGenLists(1);
        glNewList(edgeList, GL_COMPILE);
    
        glBegin(GL_LINES);

        // all edges between real cities
        for (int i = 0; i < cityCount; i++)
            for (int j = i + 1; j < cityCount; j++){
                
                Triple origin = cities.get(i);
                Triple target = cities.get(j);
                glNormal3d(origin.x + target.x,
                           origin.y + target.y,
                           origin.z + target.z);
                glVertex3d(origin.x , origin.y, origin.z);
                glVertex3d(target.x , target.y, target.z);
            }

        // edges to surface 
        for (int i = 0; i < cityCount; i++){

            Triple origin = cities.get(i);
            Triple target = cities.get(i + cityCount);
            glNormal3d(origin.x + target.x,
                       origin.y + target.y,
                       origin.z + target.z);
            glVertex3d(origin.x , origin.y, origin.z);
            glVertex3d(target.x , target.y, target.z);
        }
        
        glEnd();       
        glEndList();
    }

    private static void makeMst() {

        // make edges look nice
        glEnable(GL_LINE_SMOOTH);

        // retrieve minimum spanning tree
        mst = SpanningTree.kruskal(cities, cityCount);

        mstList = glGenLists(1);
        glNewList(mstList, GL_COMPILE);

        glBegin(GL_LINES);
        
        for (int i = 0; i < mst.size(); i++){
            
            Triple origin = cities.get(mst.get(i).x);
            Triple target = cities.get(mst.get(i).y);
            glNormal3d(origin.x + target.x,
                       origin.y + target.y,
                       origin.z + target.z);
            glVertex3d(origin.x , origin.y, origin.z);
            glVertex3d(target.x , target.y, target.z);
        }

        glEnd();
        glEndList();
    }

    private static void init() throws Exception {

        // init window
        isRunning = true;
        Display.setTitle(title);
        Display.setDisplayMode(new DisplayMode(640,480));
        Display.setFullscreen(false);
        Display.setVSyncEnabled(true);
        Display.setResizable(true);
        Display.create();

        // clear color
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // initial shape of window
        reshape();

        // z-buffer
        glEnable(GL_DEPTH_TEST);

        // alpha channel
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // backface culling
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        // normalize normals and lighting
        glEnable(GL_NORMALIZE);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_LIGHT0);
        glEnable(GL_LIGHTING);

        // render the sphere
        makeSphere();

        // render the cities
        makeCities();

        // render all possible edges
        makeEdges();

        // render minimal spanning tree
        makeMst();
    }

    private static void reshape() {
        
        // set projection stack
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        // get aspect ratio
        double ratio = Display.getWidth() * 1.0 / Display.getHeight();
        
        // creating frustum depending on aspect ratio
        if (ratio > 1)
            glFrustum (-ratio, ratio, -1.0, 1.0, 1.5, 20.0);
        else
            glFrustum (-1.0, 1.0, -1.0/ratio, 1.0/ratio, 1.5, 20.0);

        // translate objects in visible area
        glTranslated(0,0,-4);
        
        // set modelview stack
        glMatrixMode(GL_MODELVIEW);
        
        // set viewport
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
    }

    private static void mainLoop() {

        // define mainLoop flow chart
        while (isRunning) {
            
            // update display parameters every loop
            Display.update();
            
            // if close button is hit
            if (Display.isCloseRequested()) 
                isRunning = false;

            // if display is active
            else if (Display.isActive()) {
                event();
                display();
                Display.sync(desiredFPS);
            
            // if display is inactive
            } else {
                event();
                if (Display.isVisible() || Display.isDirty()) 
                    display();
            }
        }
    }

    private static void display() {

        // reset color buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        // draw some stuff 
        glPushMatrix();
        glScaled(2, 2, 2);
        glRotated(theta, 1, 0, 0);
        glRotated(phi, 0, 1, 0);


        // earth core is red
        glColor4d(1.0, 0.2, 0.0, 1.0);
        if (drawSphere){
            glPushMatrix();
            glScaled(0.5, 0.5, 0.5);
            glCallList(sphereList);
            glPopMatrix();
        }

        // cities are matrix green - everything is the matrix
        glColor4d(0.0, 1.0, 0.0, 1.0);
        glPointSize(8);
        if (drawCities)
            glCallList(cityList);

        // mst edges are grey
        glColor4d(1.0, 0.0, 0.0, 1.0);
        glLineWidth(2);
        if (drawMst)
            glCallList(mstList);

        // normal edges are grey
        glColor4d(0.5, 0.5, 0.5, 0.1);
        glLineWidth(1);
        if (drawEdges)
            glCallList(edgeList);

        // sphere is matrix green - everything is the matrix
        glColor4d(0.0, 1.0, 0.0, 0.05);
        if (drawSphere)
            glCallList(sphereList);

        glPopMatrix();
    }

    private static void event() {

        // shut down if esc is hit
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            isRunning = false;

        // keyboard interaction for sphere
        if (Keyboard.isKeyDown(Keyboard.KEY_S)){
            drawSphere = !drawSphere;
            sleep();
        }

        // keyboard interaction for edges
        if (Keyboard.isKeyDown(Keyboard.KEY_E)){
            drawEdges = !drawEdges;
            sleep();
        }

        // keyboard interaction for mst
        if (Keyboard.isKeyDown(Keyboard.KEY_M)){
            drawMst = !drawMst;
            sleep();
        }

        // keyboard interaction for cities
        if (Keyboard.isKeyDown(Keyboard.KEY_C)){
            drawCities = !drawCities;
            sleep();
        }

        // refresh projection stack on reshape
        if(Display.wasResized())
            reshape();

        // simple rectangular projection of the sphere
        phi   = 0.5*(+Mouse.getX() - Display.getWidth()/2);
        theta = 0.5*(-Mouse.getY() + Display.getHeight()/2);
    }

    private static void sleep(){
        
        try{
            Thread.sleep(200);
        } catch (Exception e) {}
    }

}


