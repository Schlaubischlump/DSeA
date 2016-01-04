package app;

// this is how minecraft was done
import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;

// java default stuff
import java.util.Vector;
import java.util.Random;

public class App {

    // origin, target and cheese (resolution, density)
    private static int res = 40;
    private static boolean cheese [][][] = new boolean[res][res][res];
    private static double cheeseDensity = 0.55;
    private static Tuple origin= new Tuple(res-1, res-1, res-1);

    // some globals (from here you can ignore the code)
    private static String title = "Undichter Käse";
    private static int desiredFPS = 60;
    private static boolean isRunning;
    private static double phi = 0, phid = 0, theta = 0, thetad = 0;

    // display lists for fast rendering and status variables
    private static int cheeseList;
    private static int waterListBFS, waterListDFS;
    private static boolean drawCheese = true;
    private static boolean drawWaterBFS = true;
    private static boolean drawWaterDFS = true;
    private static Vector<Tuple> waterBFS, waterDFS;

    public static void main(String[] args) throws Exception {

        init();
        mainLoop();
        System.exit(0);
    }

    private static void makeCheese() {

        // always the same random cheese
        Random random = new Random(42);

        for (int i = 0; i < res; i++)
            for (int j = 0; j < res; j++)
                for (int k = 0; k < res; k++)
                    cheese[i][j][k] = random.nextDouble() < cheeseDensity;

        cheeseList = glGenLists(1);
        glNewList(cheeseList, GL_COMPILE);
         for (int i = 0; i < res; i++)
            for (int j = 0; j < res; j++)
                for (int k = 0; k < res; k++)
                    if (cheese[i][j][k])
                        drawCube(res,i,j,k);
        glEndList();

    }

    private static void makeWater() {

        waterBFS = Search.BFS(cheese, origin);
        
        waterDFS = Search.DFS(cheese, origin);

        waterListBFS = glGenLists(1);
        glNewList(waterListBFS, GL_COMPILE);
        for (int i = 0; i < waterBFS.size(); i++){
            Tuple Coord = waterBFS.get(i);
            drawCube(res, Coord.zero, Coord.one, Coord.two);
        }
        glEndList();

        waterListDFS = glGenLists(1);
        glNewList(waterListDFS, GL_COMPILE);
        for (int i = 0; i < waterDFS.size(); i++){
            Tuple Coord = waterDFS.get(i);
            drawCube(res, Coord.zero, Coord.one, Coord.two);
        }
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
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // initial shape of window
        reshape();

        // z-buffer
        glEnable (GL_DEPTH_TEST);

        // backface culling
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

       // normalize normals and lighting
       glEnable(GL_NORMALIZE);
       glEnable(GL_COLOR_MATERIAL);
       glEnable(GL_LIGHT0);
       glEnable(GL_LIGHTING);

        // render the cheese
        makeCheese();

        // render the water
        makeWater();
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
        glTranslated(0,0,-6);
        
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

        // bfs water is dark blue!
        glColor3d(0, 0, 0.5);
        if (drawWaterBFS)
            glCallList(waterListBFS);

        // dfs water is light blue!
        glColor3d(0.5, 0.5, 1.0);
        if (drawWaterDFS)
            glCallList(waterListDFS);

        // cheese is yellow!
        glColor3d(1, 1, 0);
        if (drawCheese)
            glCallList(cheeseList);

        glPopMatrix();
    }

    private static void event() {

        // shut down if esc is hit
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            isRunning = false;

        // keyboard interaction for cheese
        if (Keyboard.isKeyDown(Keyboard.KEY_C)){
            drawCheese = !drawCheese;
            sleep();
        }
        
        // keyboard interaction for bfs water
        if (Keyboard.isKeyDown(Keyboard.KEY_B)){
            drawWaterBFS = !drawWaterBFS;
            sleep();
        }

       // keyboard interaction for dfs water
       if (Keyboard.isKeyDown(Keyboard.KEY_D)){
            drawWaterDFS = !drawWaterDFS;
            sleep();
        }

        // refresh projection stack on reshape
        if(Display.wasResized())
            reshape();

        // simple rectangular projection of the sphere
        phi   = 0.5*(+Mouse.getX() - Display.getWidth()/2);
        theta = 0.5*(-Mouse.getY() + Display.getHeight()/2);
    }

    private static void drawCube(int res, int x, int y, int z) {
        
        glPushMatrix();

        glTranslated(x * 2.0 / res - 1,
                     y * 2.0 / res - 1,
                     z * 2.0 / res - 1);
        glScaled(1.0 / res, 1.0 / res, 1.0 / res);

        glBegin(GL_QUADS);
        glNormal3d( 0,  0,  1);
        glVertex3d(-1, -1,  1);
        glVertex3d( 1, -1,  1);
        glVertex3d( 1,  1,  1);
        glVertex3d(-1,  1,  1);

        glNormal3d( 0,  0, -1);
        glVertex3d(-1,  1, -1);
        glVertex3d( 1,  1, -1);
        glVertex3d( 1, -1, -1);
        glVertex3d(-1, -1, -1);

        glNormal3d( 0,  1,  0);
        glVertex3d(-1,  1,  1);
        glVertex3d( 1,  1,  1);
        glVertex3d( 1,  1, -1);
        glVertex3d(-1,  1, -1);

        glNormal3d( 0, -1,  0);
        glVertex3d(-1, -1, -1);
        glVertex3d( 1, -1, -1);
        glVertex3d( 1, -1,  1);
        glVertex3d(-1, -1,  1);

        glNormal3d( 1,  0,  0);
        glVertex3d( 1, -1, -1);
        glVertex3d( 1,  1, -1);
        glVertex3d( 1,  1,  1);
        glVertex3d( 1, -1,  1);

        glNormal3d(-1,  0,  0);
        glVertex3d(-1, -1,  1);
        glVertex3d(-1,  1,  1);
        glVertex3d(-1,  1, -1);
        glVertex3d(-1, -1, -1);
        glEnd();

        glPopMatrix();
    }

    private static void sleep(){
        
        try{
            Thread.sleep(200);
        } catch (Exception e) {}
    }

}


