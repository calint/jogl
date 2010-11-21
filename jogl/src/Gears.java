import static org.lwjgl.opengl.ARBTransposeMatrix.glLoadTransposeMatrixARB;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLAT;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NORMALIZE;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_QUAD_STRIP;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glFrustum;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMaterial;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;

public class Gears{
	private float view_rotx=20.0f;
	private float view_roty=30.0f;
	private float view_rotz;
	private int gear1;
	private int gear2;
	private int gear3;
	private float angle;
	public static void main(String[] args){
		new Gears().execute();
		System.exit(0);
	}
	private void execute(){
		try{
			init();
		}catch(LWJGLException le){
			le.printStackTrace();
			System.out.println("Failed to initialize Gears.");
			return;
		}
		loop();
		destroy();
	}
	private void destroy(){
		Display.destroy();
	}
	private void loop(){
		long startTime=System.currentTimeMillis()+5000;
		long fps=0;

		while(!Display.isCloseRequested()){
			angle+=2.0f;
			glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
			glPushMatrix();
			glRotatef(view_rotx,1.0f,0.0f,0.0f);
			glRotatef(view_roty,0.0f,1.0f,0.0f);
			glRotatef(view_rotz,0.0f,0.0f,1.0f);
			glPushMatrix();
			glTranslatef(-3.0f,-2.0f,0.0f);
			glRotatef(angle,0.0f,0.0f,1.0f);
			glCallList(gear1);
			glPopMatrix();
			glPushMatrix();
			glTranslatef(3.1f,-2.0f,0.0f);
			glRotatef(-2.0f*angle-9.0f,0.0f,0.0f,1.0f);
			glCallList(gear2);
			glPopMatrix();
			glPushMatrix();
			glTranslatef(-3.1f,4.2f,0.0f);
			glRotatef(-2.0f*angle-25.0f,0.0f,0.0f,1.0f);
			glCallList(gear3);
			glPopMatrix();
			glPopMatrix();
			Display.update();
			if(startTime>System.currentTimeMillis()){
				fps++;
			}else{
				long timeUsed=5000+(startTime-System.currentTimeMillis());
				startTime=System.currentTimeMillis()+5000;
				System.out.println(fps+" frames in "+timeUsed/1000f+" seconds = "+(fps/(timeUsed/1000f)));
				fps=0;
			}
		}
	}
	private void init() throws LWJGLException{
		Display.setLocation((Display.getDisplayMode().getWidth()-300)/2,(Display.getDisplayMode().getHeight()-300)/2);
		Display.setDisplayMode(new DisplayMode(300,300));
		Display.setTitle("Gears");
		Display.create();
		FloatBuffer pos=BufferUtils.createFloatBuffer(4).put(new float[]{5.0f,5.0f,10.0f,0.0f});
		FloatBuffer red=BufferUtils.createFloatBuffer(4).put(new float[]{0.8f,0.1f,0.0f,1.0f});
		FloatBuffer green=BufferUtils.createFloatBuffer(4).put(new float[]{0.0f,0.8f,0.2f,1.0f});
		FloatBuffer blue=BufferUtils.createFloatBuffer(4).put(new float[]{0.2f,0.2f,1.0f,1.0f});
		pos.flip();
		red.flip();
		green.flip();
		blue.flip();
		glLight(GL_LIGHT0,GL_POSITION,pos);
		glEnable(GL_CULL_FACE);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_DEPTH_TEST);
		gear1=glGenLists(1);
		glNewList(gear1,GL_COMPILE);
		glMaterial(GL_FRONT,GL_AMBIENT_AND_DIFFUSE,red);
		gear(1.0f,4.0f,1.0f,20,0.7f);
		glEndList();
		gear2=glGenLists(1);
		glNewList(gear2,GL_COMPILE);
		glMaterial(GL_FRONT,GL_AMBIENT_AND_DIFFUSE,green);
		gear(0.5f,2.0f,2.0f,10,0.7f);
		glEndList();
		gear3=glGenLists(1);
		glNewList(gear3,GL_COMPILE);
		glMaterial(GL_FRONT,GL_AMBIENT_AND_DIFFUSE,blue);
		gear(1.3f,2.0f,0.5f,10,0.7f);
		glEndList();
		glEnable(GL_NORMALIZE);
		glMatrixMode(GL_PROJECTION);
		System.err.println("LWJGL: "+Sys.getVersion()+" / "+LWJGLUtil.getPlatformName());
		System.err.println("GL_VENDOR: "+glGetString(GL_VENDOR));
		System.err.println("GL_RENDERER: "+glGetString(GL_RENDERER));
		System.err.println("GL_VERSION: "+glGetString(GL_VERSION));
		System.err.println();
		System.err.println("glLoadTransposeMatrixfARB() supported: "+GLContext.getCapabilities().GL_ARB_transpose_matrix);
		if(!GLContext.getCapabilities().GL_ARB_transpose_matrix){
			glLoadIdentity();
		}else{
			final FloatBuffer identityTranspose=BufferUtils.createFloatBuffer(16).put(new float[]{1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1});
			identityTranspose.flip();
			glLoadTransposeMatrixARB(identityTranspose);
		}
		float h=(float)300/(float)300;
		glFrustum(-1.0f,1.0f,-h,h,5.0f,60.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glTranslatef(0.0f,0.0f,-40.0f);
	}
	private void gear(float inner_radius,float outer_radius,float width,int teeth,float tooth_depth){
		int i;
		float r0,r1,r2;
		float angle,da;
		float u,v,len;
		r0=inner_radius;
		r1=outer_radius-tooth_depth/2.0f;
		r2=outer_radius+tooth_depth/2.0f;
		da=2.0f*(float)Math.PI/teeth/4.0f;
		glShadeModel(GL_FLAT);
		glNormal3f(0.0f,0.0f,1.0f);
		glBegin(GL_QUAD_STRIP);
		for(i=0;i<=teeth;i++){
			angle=i*2.0f*(float)Math.PI/teeth;
			glVertex3f(r0*(float)Math.cos(angle),r0*(float)Math.sin(angle),width*0.5f);
			glVertex3f(r1*(float)Math.cos(angle),r1*(float)Math.sin(angle),width*0.5f);
			if(i<teeth){
				glVertex3f(r0*(float)Math.cos(angle),r0*(float)Math.sin(angle),width*0.5f);
				glVertex3f(r1*(float)Math.cos(angle+3.0f*da),r1*(float)Math.sin(angle+3.0f*da),width*0.5f);
			}
		}
		glEnd();
		glBegin(GL_QUADS);
		for(i=0;i<teeth;i++){
			angle=i*2.0f*(float)Math.PI/teeth;
			glVertex3f(r1*(float)Math.cos(angle),r1*(float)Math.sin(angle),width*0.5f);
			glVertex3f(r2*(float)Math.cos(angle+da),r2*(float)Math.sin(angle+da),width*0.5f);
			glVertex3f(r2*(float)Math.cos(angle+2.0f*da),r2*(float)Math.sin(angle+2.0f*da),width*0.5f);
			glVertex3f(r1*(float)Math.cos(angle+3.0f*da),r1*(float)Math.sin(angle+3.0f*da),width*0.5f);
		}
		glEnd();
		glBegin(GL_QUAD_STRIP);
		for(i=0;i<=teeth;i++){
			angle=i*2.0f*(float)Math.PI/teeth;
			glVertex3f(r1*(float)Math.cos(angle),r1*(float)Math.sin(angle),-width*0.5f);
			glVertex3f(r0*(float)Math.cos(angle),r0*(float)Math.sin(angle),-width*0.5f);
			glVertex3f(r1*(float)Math.cos(angle+3*da),r1*(float)Math.sin(angle+3*da),-width*0.5f);
			glVertex3f(r0*(float)Math.cos(angle),r0*(float)Math.sin(angle),-width*0.5f);
		}
		glEnd();
		glBegin(GL_QUADS);
		for(i=0;i<teeth;i++){
			angle=i*2.0f*(float)Math.PI/teeth;
			glVertex3f(r1*(float)Math.cos(angle+3*da),r1*(float)Math.sin(angle+3*da),-width*0.5f);
			glVertex3f(r2*(float)Math.cos(angle+2*da),r2*(float)Math.sin(angle+2*da),-width*0.5f);
			glVertex3f(r2*(float)Math.cos(angle+da),r2*(float)Math.sin(angle+da),-width*0.5f);
			glVertex3f(r1*(float)Math.cos(angle),r1*(float)Math.sin(angle),-width*0.5f);
		}
		glEnd();
		glBegin(GL_QUAD_STRIP);
		for(i=0;i<teeth;i++){
			angle=i*2.0f*(float)Math.PI/teeth;
			glVertex3f(r1*(float)Math.cos(angle),r1*(float)Math.sin(angle),width*0.5f);
			glVertex3f(r1*(float)Math.cos(angle),r1*(float)Math.sin(angle),-width*0.5f);
			u=r2*(float)Math.cos(angle+da)-r1*(float)Math.cos(angle);
			v=r2*(float)Math.sin(angle+da)-r1*(float)Math.sin(angle);
			len=(float)Math.sqrt(u*u+v*v);
			u/=len;
			v/=len;
			glNormal3f(v,-u,0.0f);
			glVertex3f(r2*(float)Math.cos(angle+da),r2*(float)Math.sin(angle+da),width*0.5f);
			glVertex3f(r2*(float)Math.cos(angle+da),r2*(float)Math.sin(angle+da),-width*0.5f);
			glNormal3f((float)Math.cos(angle),(float)Math.sin(angle),0.0f);
			glVertex3f(r2*(float)Math.cos(angle+2*da),r2*(float)Math.sin(angle+2*da),width*0.5f);
			glVertex3f(r2*(float)Math.cos(angle+2*da),r2*(float)Math.sin(angle+2*da),-width*0.5f);
			u=r1*(float)Math.cos(angle+3*da)-r2*(float)Math.cos(angle+2*da);
			v=r1*(float)Math.sin(angle+3*da)-r2*(float)Math.sin(angle+2*da);
			glNormal3f(v,-u,0.0f);
			glVertex3f(r1*(float)Math.cos(angle+3*da),r1*(float)Math.sin(angle+3*da),width*0.5f);
			glVertex3f(r1*(float)Math.cos(angle+3*da),r1*(float)Math.sin(angle+3*da),-width*0.5f);
			glNormal3f((float)Math.cos(angle),(float)Math.sin(angle),0.0f);
		}
		glVertex3f(r1*(float)Math.cos(0),r1*(float)Math.sin(0),width*0.5f);
		glVertex3f(r1*(float)Math.cos(0),r1*(float)Math.sin(0),-width*0.5f);
		glEnd();
		glShadeModel(GL_SMOOTH);
		glBegin(GL_QUAD_STRIP);
		for(i=0;i<=teeth;i++){
			angle=i*2.0f*(float)Math.PI/teeth;
			glNormal3f(-(float)Math.cos(angle),-(float)Math.sin(angle),0.0f);
			glVertex3f(r0*(float)Math.cos(angle),r0*(float)Math.sin(angle),-width*0.5f);
			glVertex3f(r0*(float)Math.cos(angle),r0*(float)Math.sin(angle),width*0.5f);
		}
		glEnd();
	}
}
