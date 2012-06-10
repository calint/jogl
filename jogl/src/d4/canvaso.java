package d4;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_NICEST;
import static javax.media.opengl.GL.GL_TRIANGLES;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
public class canvaso extends GLCanvas implements GLEventListener{
	private static final long serialVersionUID=1;
	public canvaso(){
		this.addGLEventListener(this);
	}
	public void init(final GLAutoDrawable d){
		final GL2 gl=d.getGL().getGL2();
		gl.glClearColor(0,0,0,0);
		gl.glClearDepth(1f);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glDepthFunc(GL_LEQUAL);
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT,GL_NICEST);
		gl.glShadeModel(GL_SMOOTH);
	}
	public void reshape(final GLAutoDrawable d,final int x,final int y,final int w,final int h){
		final GL2 gl=d.getGL().getGL2();
		float aspect=(float)w/(h==0?1:h);
		gl.glViewport(0,0,w,h);
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		new GLU().gluPerspective(45,aspect,.1,100); // fovy,aspect,znear,zfar
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	public void display(final GLAutoDrawable d){
		final GL2 gl=d.getGL().getGL2();
		gl.glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0,0,-6);
		gl.glBegin(GL_TRIANGLES);
		gl.glVertex2f(0,1);
		gl.glVertex2f(-1,-1);
		gl.glVertex2f(1,-1);
		gl.glEnd();
	}
	public void dispose(final GLAutoDrawable drawable){}
}