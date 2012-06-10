package d4;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
public class canvas extends GLCanvas implements GLEventListener,KeyListener{
	private static final long serialVersionUID=1;
	public static int fps=1;
	private shader shdr=new shader();
	private FPSAnimator fpsa=new FPSAnimator(this,fps);
	private long t0;
	private int key=0;
	private Throwable err;
	public canvas()throws Throwable{
		addGLEventListener(this);
		addKeyListener(this);
		setVisible(true);
		fpsa.start();
	}
	public void init(final GLAutoDrawable gld){
		final GL2 g=gld.getGL().getGL2();
		try{loadprog(g);shdr.init(g);}catch(final Throwable e){err=e;return;}
	}
	private static String readstr(final InputStream is)throws IOException{
		if(is==null)return null;
		final ByteBuffer bb=ByteBuffer.allocate(4*1024);
		final int c=is.read(bb.array());
		if(c<=0)
			return "";
		return new String(bb.array(),0,c,"utf8");//? incomplread
	}
	private void checkshader(final GL2 gl,final int shader,final String pth)throws Exception{
		final IntBuffer bbi=IntBuffer.allocate(1);
		final ByteBuffer bb=ByteBuffer.allocate(4*4096);
		gl.glGetShaderInfoLog(shader,bb.limit(),bbi,bb);
		final int len=bbi.get(0);
		if(len==0)
			return;
		final String s=new String(bb.array(),0,len);
		final String shadernm;
		if(shader==1)shadernm="vertex";
		else if(shader==2)shadernm="fragment";
		else shadernm=Integer.toString(shader);	
       	throw new Exception("loadshader["+shadernm+","+pth+"] failed:\n"+s);
	}
	private void checkprog(final GL2 gl,final int prog) {
		final IntBuffer bbi=IntBuffer.allocate(1);
		gl.glGetObjectParameterivARB(prog,GL2.GL_OBJECT_INFO_LOG_LENGTH_ARB,bbi);
		final int len=bbi.get(0);
		if(len==0)
			return;
		final ByteBuffer bb=ByteBuffer.allocate(len);
		gl.glGetInfoLogARB(prog,len,bbi,bb);
		final int lena=bbi.get(0);
		final String s=new String(bb.array(),0,lena);
		throw new Error("loadprog["+prog+"] failed:\n"+s);
    }
	private void loadprog(final GL2 g)throws Throwable{
		final int v=g.glCreateShader(GL2.GL_VERTEX_SHADER);
		final String vpth=shdr.shdrpthvtx();
		final String vsrc=readstr(canvas.class.getResourceAsStream(vpth));
//		if(vsrc.trim().length()==0)throw new Error("vertexshadersourceisempty");
		g.glShaderSource(v,1,new String[]{vsrc},null);
		g.glCompileShader(v);
		checkshader(g,v,vpth);
		
		final int f=g.glCreateShader(GL2.GL_FRAGMENT_SHADER);
		final String fpth=shdr.shdrpthfrag();
		final String fsrc=readstr(canvas.class.getResourceAsStream(fpth));
//		if(fsrc.trim().length()==0)throw new Error("fragmentshadersourceisempty");
		g.glShaderSource(f,1,new String[]{fsrc},null);
		g.glCompileShader(f);
		checkshader(g,f,fpth);
		
		final int prog=g.glCreateProgram();
		g.glAttachShader(prog,v);
		g.glAttachShader(prog,f);
		g.glLinkProgram(prog);
		checkprog(g,prog);

		shdr.link(g,prog);
		
		g.glValidateProgram(prog);
		g.glUseProgram(prog);
	}
	public void display(final GLAutoDrawable gld) {
		if(err!=null){
			fpsa.stop();
			err.printStackTrace();
			return;
		}
		final long t1=System.currentTimeMillis();
		final GL2 g=gld.getGL().getGL2();
		shdr.rend(g);
		g.glFlush();
		final long t2=System.currentTimeMillis();
		final long dt21=t2-t1;
		final long dt10=t1-t0;
		System.out.format("frame=#%06d   framerend=%03dms    framewait=%03dms    cpu=%02d%%\r",fpsa.getTotalFPSFrames(),(int)dt21,(int)dt10,(int)(100*dt21/dt10));
		t0=t1;
	}
	public void reshape(final GLAutoDrawable gld,final int x,final int y,final int w,final int height){
		if(err!=null)
			return;
		System.out.println("reshape");
		final GL2 g=gld.getGL().getGL2();
		shdr.redim(g,x,y,w,height);
	}
	public void dispose(final GLAutoDrawable gld){System.out.println("dispose");}
	public final void keyPressed(final KeyEvent e){
		final int kc=e.getKeyCode();
		if(kc==key)
			return;
		System.out.println(e);
		key=kc;
		onkey(kc);
	}
	public final void keyTyped(final KeyEvent e){}
	public final void keyReleased(final KeyEvent e){
		System.out.println(e);
		key=0;
	}
	protected void onkey(final int c){
		if(c==KeyEvent.VK_ESCAPE)System.exit(0);
	}
}