package d4;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
public class frame extends JFrame{
	private static final long serialVersionUID=1;
	public static void main(final String[]a)throws Throwable{new frame();}
	public static int wi=1024;
	public static int hi=512;
	private canvas cnvs=new canvas();
	public frame()throws Throwable{
		super("jogl");
//		setUndecorated(true);
//		setExtendedState(Frame.MAXIMIZED_BOTH);        
		cnvs.setSize(new Dimension(wi,hi));
		add(cnvs);
		addWindowListener(new WindowAdapter(){public void windowClosing(final WindowEvent e){new Thread(){public void run(){
			System.exit(0);
		}}.start();}});
		pack();
		setVisible(true);
	}
}