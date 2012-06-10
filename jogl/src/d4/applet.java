package d4;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JApplet;
public class applet extends JApplet{
	private static final long serialVersionUID=1;
	//	public static String glcnvsclsnm="d4.canvaso";
	public static String glcnvsclsnm="d4.canvas";
	public static int wi=1024;
	public static int hi=512;
	public void init(){try{
		setSize(wi,hi);
		final GLCanvas canvas=(GLCanvas)Class.forName(glcnvsclsnm).newInstance();
		getContentPane().add(canvas);
	}catch(Throwable e){e.printStackTrace();}}
}