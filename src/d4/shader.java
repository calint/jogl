package d4;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
public final class shader{
	private int rot=0;
	public String shdrpthvtx(){return "vertex.glsl";}
	public String shdrpthfrag(){return "fragment.glsl";}
	public void link(final GL2 g,final int prog)throws Throwable{
	}
	public void init(final GL2 g)throws Throwable{
		g.glClearColor(0,0,0,0);
		g.glEnable(GL.GL_CULL_FACE);
		g.glCullFace(GL.GL_BACK);
	}
	public void redim(final GL2 g,final int x,final int y,final int w,final int h){
		g.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		g.glLoadIdentity();
		new GLU().gluPerspective(50f,(float)w/(h<=0?1:h),1,1000);
		g.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		g.glLoadIdentity();
	}
	public void rend(final GL2 g) {
		g.glClear(GL.GL_COLOR_BUFFER_BIT);
		g.glLoadIdentity();
		g.glTranslatef(0,0,-4);

		g.glPointSize(10);
		g.glBegin(GL2.GL_POINTS);
		for(int i=0;i<360;i+=30)
			g.glVertex2f((float)Math.cos((float)i*Math.PI/180),(float)Math.sin((float)i*Math.PI/180));
		g.glEnd();
		g.glRotatef(rot,0,0,1);

		g.glBegin(GL2.GL_TRIANGLE_STRIP);
		g.glVertex2f(-1,-1);
		g.glVertex2f( 1,-1);
		g.glVertex2f(-1, 1);
//		g.glVertex2f( 1, 1);
		g.glEnd();

		g.glRotatef(rot,0,0,1);
		g.glBegin(GL2.GL_TRIANGLE_STRIP);
		g.glVertex2f(-1,-.1f);
		g.glVertex2f( 1,-.1f);
		g.glVertex2f(-1, .1f);
//		g.glVertex2f( 1, 1);
		g.glEnd();

		rot+=360/60;
	}
}