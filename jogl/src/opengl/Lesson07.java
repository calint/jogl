package opengl;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
public class Lesson07{

	public static void main(String args[]) throws Throwable{
		new Lesson07(false);
	}
	public Lesson07(boolean fullscreen) throws Throwable{
		final String windowTitle="NeHe's OpenGL Lesson 7 for LWJGL (Texture Filters, Lighting & Keyboard Control)";
		DisplayMode displayMode;
		boolean light=false;
		boolean lp=false;
		boolean fp=false;
		float xrot=0;
		float yrot=0;
		float xspeed=0;
		float yspeed=0;
		float z=-5.0f;
		float lightAmbient[]={0.5f,0.5f,0.5f,1.0f};
		float lightDiffuse[]={1.0f,1.0f,1.0f,1.0f};
		float lightPosition[]={0.0f,0.0f,2.0f,1.0f};
		int filter=0;
		int texture[]=new int[3];
		Display.setFullscreen(fullscreen);
		DisplayMode d[]=Display.getAvailableDisplayModes();
		displayMode=d[0];
		Display.setDisplayMode(displayMode);
		Display.setTitle(windowTitle);
		Display.create();
		Texture texture1=TextureLoader.getTexture("BMP",new FileInputStream(new File("Crate.bmp")),true,GL11.GL_LINEAR);
		Texture texture2=TextureLoader.getTexture("BMP",new FileInputStream(new File("Crate.bmp")),true,GL11.GL_LINEAR);
		Texture texture3=TextureLoader.getTexture("BMP",new FileInputStream(new File("Crate.bmp")),true,GL11.GL_LINEAR);
		texture=new int[]{texture1.getTextureID(),texture2.getTextureID(),texture3.getTextureID()};
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(0.0f,0.0f,0.0f,0.0f);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(45.0f,(float)displayMode.getWidth()/(float)displayMode.getWidth(),0.1f,100.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT,GL11.GL_NICEST);
		ByteBuffer temp=ByteBuffer.allocateDirect(16);
		temp.order(ByteOrder.nativeOrder());
		GL11.glLight(GL11.GL_LIGHT1,GL11.GL_AMBIENT,(FloatBuffer)temp.asFloatBuffer().put(lightAmbient).flip());
		GL11.glLight(GL11.GL_LIGHT1,GL11.GL_DIFFUSE,(FloatBuffer)temp.asFloatBuffer().put(lightDiffuse).flip());
		GL11.glLight(GL11.GL_LIGHT1,GL11.GL_POSITION,(FloatBuffer)temp.asFloatBuffer().put(lightPosition).flip());
		GL11.glEnable(GL11.GL_LIGHT1);
		while(true){
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
				break;
			if(Display.isCloseRequested())
				break;
			if(Keyboard.isKeyDown(Keyboard.KEY_L)&&!lp){
				lp=true;
				light=!light;
				if(!light){
					GL11.glDisable(GL11.GL_LIGHTING);
				}else{
					GL11.glEnable(GL11.GL_LIGHTING);
				}
			}else if(!Keyboard.isKeyDown(Keyboard.KEY_L)){
				lp=false;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_F)&&!fp){
				fp=true;
				filter+=1;
				if(filter>2){
					filter=0;
				}
			}else if(!Keyboard.isKeyDown(Keyboard.KEY_F)){
				fp=false;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_PRIOR)){
				z-=0.02f;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_NEXT)){
				z+=0.02f;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				xspeed-=0.01f;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				xspeed+=0.01f;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				yspeed+=0.01f;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				yspeed-=0.01f;
			}
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0f,0.0f,z);
			GL11.glRotatef(xrot,1.0f,0.0f,0.0f);
			GL11.glRotatef(yrot,0.0f,1.0f,0.0f);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,texture[filter]);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glNormal3f(0.0f,0.0f,1.0f);
			GL11.glTexCoord2f(0.0f,0.0f);
			GL11.glVertex3f(-1.0f,-1.0f,1.0f);
			GL11.glTexCoord2f(1.0f,0.0f);
			GL11.glVertex3f(1.0f,-1.0f,1.0f);
			GL11.glTexCoord2f(1.0f,1.0f);
			GL11.glVertex3f(1.0f,1.0f,1.0f);
			GL11.glTexCoord2f(0.0f,1.0f);
			GL11.glVertex3f(-1.0f,1.0f,1.0f);
			GL11.glNormal3f(0.0f,0.0f,-1.0f);
			GL11.glTexCoord2f(1.0f,0.0f);
			GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
			GL11.glTexCoord2f(1.0f,1.0f);
			GL11.glVertex3f(-1.0f,1.0f,-1.0f);
			GL11.glTexCoord2f(0.0f,1.0f);
			GL11.glVertex3f(1.0f,1.0f,-1.0f);
			GL11.glTexCoord2f(0.0f,0.0f);
			GL11.glVertex3f(1.0f,-1.0f,-1.0f);
			GL11.glNormal3f(0.0f,1.0f,0.0f);
			GL11.glTexCoord2f(0.0f,1.0f);
			GL11.glVertex3f(-1.0f,1.0f,-1.0f);
			GL11.glTexCoord2f(0.0f,0.0f);
			GL11.glVertex3f(-1.0f,1.0f,1.0f);
			GL11.glTexCoord2f(1.0f,0.0f);
			GL11.glVertex3f(1.0f,1.0f,1.0f);
			GL11.glTexCoord2f(1.0f,1.0f);
			GL11.glVertex3f(1.0f,1.0f,-1.0f);
			GL11.glNormal3f(0.0f,-1.0f,0.0f);
			GL11.glTexCoord2f(1.0f,1.0f);
			GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
			GL11.glTexCoord2f(0.0f,1.0f);
			GL11.glVertex3f(1.0f,-1.0f,-1.0f);
			GL11.glTexCoord2f(0.0f,0.0f);
			GL11.glVertex3f(1.0f,-1.0f,1.0f);
			GL11.glTexCoord2f(1.0f,0.0f);
			GL11.glVertex3f(-1.0f,-1.0f,1.0f);
			GL11.glNormal3f(1.0f,0.0f,0.0f);
			GL11.glTexCoord2f(1.0f,0.0f);
			GL11.glVertex3f(1.0f,-1.0f,-1.0f);
			GL11.glTexCoord2f(1.0f,1.0f);
			GL11.glVertex3f(1.0f,1.0f,-1.0f);
			GL11.glTexCoord2f(0.0f,1.0f);
			GL11.glVertex3f(1.0f,1.0f,1.0f);
			GL11.glTexCoord2f(0.0f,0.0f);
			GL11.glVertex3f(1.0f,-1.0f,1.0f);
			GL11.glNormal3f(-1.0f,0.0f,0.0f);
			GL11.glTexCoord2f(0.0f,0.0f);
			GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
			GL11.glTexCoord2f(1.0f,0.0f);
			GL11.glVertex3f(-1.0f,-1.0f,1.0f);
			GL11.glTexCoord2f(1.0f,1.0f);
			GL11.glVertex3f(-1.0f,1.0f,1.0f);
			GL11.glTexCoord2f(0.0f,1.0f);
			GL11.glVertex3f(-1.0f,1.0f,-1.0f);
			GL11.glEnd();
			xrot+=xspeed;
			yrot+=yspeed;
			Display.update();
		}
		Display.destroy();
	}
}