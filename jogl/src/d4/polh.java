package d4;

import java.nio.FloatBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import b.a;

public class polh extends a{
	private static final long serialVersionUID=1L;
	private static int gbv=0;
//	private float[]v={-.5f,-.5f,  .5f,-.5f,  -.5f, .5f};
//	private float[]v={0,0};
	private FloatBuffer bv=(FloatBuffer)FloatBuffer.allocate(2).put(0).put(0).flip();
	private int[]gb=new int[1];
	public void init(final GL2 g)throws Throwable{
		if(bv==null)
			return;
		g.glGenBuffers(gb.length,gb,0);
		g.glBindBuffer(GL2.GL_ARRAY_BUFFER,gb[gbv]);
		g.glBufferData(GL2.GL_ARRAY_BUFFER,bv.limit()*2,bv,GL2.GL_STATIC_DRAW);
		if(g.glGetError()!=GL.GL_NO_ERROR)throw new Exception(this+": couldnotloadvertexbuf");
		//? checkok
		rend(g);
	}
	float pointsize=1;
	public void rend(final GL2 g){
		if(bv==null)
			return;
		g.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		g.glEnableClientState(GL2.GL_COLOR_ARRAY);
		final int c=bv.limit()>>1;
		g.glPointSize(pointsize);
		g.glBindBuffer(GL.GL_ARRAY_BUFFER,gb[gbv]);
		g.glVertexAttribPointer(gb[gbv],2,GL.GL_FLOAT,false,0,0);
		g.glDrawArrays(GL.GL_TRIANGLES,0,c);
//		g.glVertexPointer(2,GL.GL_FLOAT,0,bv);
//		g.glDrawArrays(GL.GL_TRIANGLE_STRIP,0,v.length);
	}
	public static void main(final String[]a){
		//· rendinframe
	}
}
