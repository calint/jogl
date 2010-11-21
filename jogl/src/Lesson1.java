import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

/**
 * $Id$
 * <p>
 * Lesson 1: Single Static Source
 * </p>
 * @author Brian Matzon <brian@matzon.dk>
 * @version $Revision$
 */
public class Lesson1{

	/** Buffers hold sound data. */
	IntBuffer buffer=BufferUtils.createIntBuffer(1);

	/** Sources are points emitting sound. */
	IntBuffer source=BufferUtils.createIntBuffer(1);

	/** Position of the source sound. */
	FloatBuffer sourcePos=BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});

	/*
	 * These are 3D cartesian vector coordinates. A structure or class would be
	 * a more flexible of handling these, but for the sake of simplicity we will
	 * just leave it as is.
	 */

	/** Velocity of the source sound. */
	FloatBuffer sourceVel=BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});

	/** Position of the listener. */
	FloatBuffer listenerPos=BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});

	/** Velocity of the listener. */
	FloatBuffer listenerVel=BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});

	/** Orientation of the listener. (first 3 elements are "at", second 3 are "up")
	    Also note that these should be units of '1'. */
	FloatBuffer listenerOri=BufferUtils.createFloatBuffer(6).put(new float[]{0.0f,0.0f,-1.0f,0.0f,1.0f,0.0f});

	public Lesson1(){
		// CRUCIAL!
		// any buffer that has data added, must be flipped to establish its position and limits
		sourcePos.flip();
		sourceVel.flip();
		listenerPos.flip();
		listenerVel.flip();
		listenerOri.flip();
	}

	/**
	 * boolean LoadALData()
	 *
	 *  This function will load our sample data from the disk using the Alut
	 *  utility and send the data into OpenAL as a buffer. A source is then
	 *  also created to play that buffer.
	 */
	int loadALData(){
		// Load wav data into a buffer.
		AL10.alGenBuffers(buffer);

		if(AL10.alGetError()!=AL10.AL_NO_ERROR)
			return AL10.AL_FALSE;

		WaveData waveFile=WaveData.create("FancyPants.wav");
		AL10.alBufferData(buffer.get(0),waveFile.format,waveFile.data,waveFile.samplerate);
		waveFile.dispose();

		// Bind the buffer with the source.
		AL10.alGenSources(source);

		if(AL10.alGetError()!=AL10.AL_NO_ERROR)
			return AL10.AL_FALSE;

		AL10.alSourcei(source.get(0),AL10.AL_BUFFER,buffer.get(0));
		AL10.alSourcef(source.get(0),AL10.AL_PITCH,1.0f);
		AL10.alSourcef(source.get(0),AL10.AL_GAIN,1.0f);
		AL10.alSource(source.get(0),AL10.AL_POSITION,sourcePos);
		AL10.alSource(source.get(0),AL10.AL_VELOCITY,sourceVel);

		// Do another error check and return.
		if(AL10.alGetError()==AL10.AL_NO_ERROR)
			return AL10.AL_TRUE;

		return AL10.AL_FALSE;
	}

	/**
	 * void setListenerValues()
	 *
	 *  We already defined certain values for the Listener, but we need
	 *  to tell OpenAL to use that data. This function does just that.
	 */
	void setListenerValues(){
		AL10.alListener(AL10.AL_POSITION,listenerPos);
		AL10.alListener(AL10.AL_VELOCITY,listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION,listenerOri);
	}

	/**
	 * void killALData()
	 *
	 *  We have allocated memory for our buffers and sources which needs
	 *  to be returned to the system. This function frees that memory.
	 */
	void killALData(){
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
	}

	public void execute(){
		// Initialize OpenAL and clear the error bit.
		try{
			AL.create(null,15,22050,true);
		}catch(LWJGLException le){
			le.printStackTrace();
			return;
		}
		AL10.alGetError();

		// Load the wav data.
		if(loadALData()==AL10.AL_FALSE){
			System.out.println("Error loading data.");
			return;
		}

		setListenerValues();

		System.out.print("MindCode's OpenAL Lesson 1: Single Static Source\n\n");
		System.out.print("Controls:\n");
		System.out.print("p) Play\n");
		System.out.print("s) Stop\n");
		System.out.print("h) Hold (pause)\n");
		System.out.print("q) Quit\n\n");

		// Loop.
		char c=' ';
		while(c!='q'){
			try{
				c=(char)System.in.read();
			}catch(IOException ioe){
				c='q';
			}

			switch(c){
			// Pressing 'p' will begin playing the sample.
			case 'p':
				AL10.alSourcePlay(source.get(0));
				break;

			// Pressing 's' will stop the sample from playing.
			case 's':
				AL10.alSourceStop(source.get(0));
				break;

			// Pressing 'h' will pause the sample.
			case 'h':
				AL10.alSourcePause(source.get(0));
				break;
			//        case '1': System.out.println("Suspend");ALC.alcSuspendContext(); break;
			//        case '2': ALC.alcProcessContext(); break;
			};

		}

		killALData();
	}
	public static void main(String[] args){
		new Lesson1().execute();
	}
}