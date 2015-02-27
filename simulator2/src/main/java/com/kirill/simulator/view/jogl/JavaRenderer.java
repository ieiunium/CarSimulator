package com.kirill.simulator.view.jogl;

/**
 * Created by kirill-good on 25.2.15.
 */
import com.kirill.simulator.core.interfaces.Agent;
import com.kirill.simulator.core.simulation.Track;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.util.List;

public class JavaRenderer implements GLEventListener {
    private float rotateT = 0.0f;
    private static final GLU glu = new GLU();
    protected volatile int dX=0,dY=0,dZ=-800;

    public JavaRenderer(Track track, List<Agent> agents) {
        this.track = track;
        this.agents = agents;
    }

    private volatile List<Agent> agents;
    private Track track;

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        gl.glBegin(GL2.GL_QUADS);

        track.glPaint(gl,dX,dY,dZ);
        for(Agent i:agents){
            i.glPaint(gl,dX,dY,dZ);
        }

        gl.glEnd();

        rotateT += 0.2f;
    }

    public static void drawSquare(GL2 gl,int x,int y,int dX,int dY,int dZ){

        gl.glColor3f(0.0f, 0.0f, 0.0f);

        // front :
        float a = 1.0f;
        gl.glVertex3f(x + dX,    y + dY,      +dZ);
        gl.glVertex3f(x+a + dX,  y + dY,      +dZ);
        gl.glVertex3f(x+a + dX,  y+a + dY,    +dZ);
        gl.glVertex3f(x + dX,    y+a + dY,    +dZ);
    }

    public void init(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT,GL.GL_NICEST);
    }

    public void reshape(GLAutoDrawable gLDrawable, int x,
                        int y, int width, int height) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        if(height <= 0) {
            height = 1;
        }
        final float h = (float)width / (float)height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 2000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void dispose(GLAutoDrawable arg0) {

    }

}