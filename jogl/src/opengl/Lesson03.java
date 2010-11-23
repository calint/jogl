package opengl;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
public class Lesson03{

	public static void main(String args[]) throws Throwable{
		new Lesson03();
	}
	public Lesson03() throws Throwable{
		final String windowTitle="Adding Color";
		Display.setFullscreen(false);
		DisplayMode d[]=Display.getAvailableDisplayModes();
		DisplayMode displayMode=d[0];
		Display.setDisplayMode(displayMode);
		Display.setTitle(windowTitle);
		Display.create();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(0.0f,0.0f,0.0f,0.0f);
		GL11.glClearDepth(1.0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(45.0f,(float)displayMode.getWidth()/(float)displayMode.getHeight(),0.1f,100.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT,GL11.GL_NICEST);
		while(true){
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
				break;
			if(Display.isCloseRequested())
				break;
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();
			GL11.glTranslatef(-1.5f,0.0f,-6.0f);
			GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glColor3f(1.0f,0.0f,0.0f);
			GL11.glVertex3f(0.0f,1.0f,0.0f);
			GL11.glColor3f(0.0f,1.0f,0.0f);
			GL11.glVertex3f(-1.0f,-1.0f,0.0f);
			GL11.glColor3f(0.0f,0.0f,1.0f);
			GL11.glVertex3f(1.0f,-1.0f,0.0f);
			GL11.glEnd();
			GL11.glTranslatef(3.0f,0.0f,0.0f);
			GL11.glColor3f(0.5f,0.5f,1.0f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3f(-1.0f,1.0f,0.0f);
			GL11.glVertex3f(1.0f,1.0f,0.0f);
			GL11.glVertex3f(1.0f,-1.0f,0.0f);
			GL11.glVertex3f(-1.0f,-1.0f,0.0f);
			GL11.glEnd();
			Display.update();
		}
		Display.destroy();
	}
}
