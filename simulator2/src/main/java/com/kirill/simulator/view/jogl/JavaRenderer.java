package com.kirill.simulator.view.jogl;

/**
 * Created by kirill-good on 25.2.15.
 */
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

public class JavaRenderer implements GLEventListener {
    private float rotateT = 0.0f;
    private static final GLU glu = new GLU();
    public volatile int x=0,y=0;
    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        //gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        //gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        //gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        //gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);

        gl.glBegin(GL2.GL_QUADS);

        drawSquare(gl, x, y);

        gl.glEnd();

        rotateT += 0.2f;
    }

    public static void drawSquare(GL2 gl,int x,int y){

        gl.glColor3f(1.0f, 1.0f, 1.0f);

        // front :
        float a = 0.5f;

        float az = -100f;
        gl.glVertex3f(x,    y,      +az);
        gl.glVertex3f(x+a,  y,      +az);
        gl.glVertex3f(x+a,  y+a,    +az);
        gl.glVertex3f(x,    y+a,    +az);
/*
        // back :
        gl.glVertex3f(+0.5f, -0.5f, -0.5f);
        gl.glVertex3f(+0.5f, +0.5f, -0.5f);
        gl.glVertex3f(-0.5f, +0.5f, -0.5f);
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);

        // left :
        gl.glVertex3f(-0.5f, +0.5f, +0.5f);
        gl.glVertex3f(-0.5f, +0.5f, -0.5f);
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glVertex3f(-0.5f, -0.5f, +0.5f);

        // right :
        gl.glVertex3f(+0.5f, +0.5f, -0.5f);
        gl.glVertex3f(+0.5f, +0.5f, +0.5f);
        gl.glVertex3f(+0.5f, -0.5f, +0.5f);
        gl.glVertex3f(+0.5f, -0.5f, -0.5f);

        // top :
        gl.glVertex3f(+0.5f, +0.5f, +0.5f);
        gl.glVertex3f(-0.5f, +0.5f, +0.5f);
        gl.glVertex3f(-0.5f, +0.5f, -0.5f);
        gl.glVertex3f(+0.5f, +0.5f, -0.5f);

        // bottom :
        gl.glVertex3f(+0.5f, -0.5f, +0.5f);
        gl.glVertex3f(+0.5f, -0.5f, -0.5f);
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glVertex3f(-0.5f, -0.5f, +0.5f);*/
    }

    public void init(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
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
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void dispose(GLAutoDrawable arg0) {

    }

}