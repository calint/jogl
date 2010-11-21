import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;
public class Lesson2{
	public Lesson2(){
//		String sound_file="FancyPants.wav";
		String sound_file="Footsteps.wav";
		IntBuffer buffer=BufferUtils.createIntBuffer(1);
		IntBuffer source=BufferUtils.createIntBuffer(1);
		FloatBuffer sourcePos=BufferUtils.createFloatBuffer(3).put(new float[]{-10.0f,0.0f,0.0f});
		FloatBuffer sourceVel=BufferUtils.createFloatBuffer(3).put(new float[]{0.1f,0.0f,0.0f});
		FloatBuffer listenerPos=BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});
		FloatBuffer listenerVel=BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});
		FloatBuffer listenerOri=BufferUtils.createFloatBuffer(6).put(new float[]{0.0f,0.0f,-1.0f,0.0f,1.0f,0.0f});
		sourcePos.flip();
		sourceVel.flip();
		listenerPos.flip();
		listenerVel.flip();
		listenerOri.flip();
		try{
			AL.create(null,15,22050,true);
		}catch(LWJGLException e){
			throw new Error(e);
		}
		AL10.alGetError();
		AL10.alGenBuffers(buffer);
		if(AL10.alGetError()!=AL10.AL_NO_ERROR)
			throw new Error();
		WaveData waveFile=WaveData.create(sound_file);
		AL10.alBufferData(buffer.get(0),waveFile.format,waveFile.data,waveFile.samplerate);
		waveFile.dispose();
		AL10.alGenSources(source);
		if(AL10.alGetError()!=AL10.AL_NO_ERROR)
			throw new Error();
		AL10.alSourcei(source.get(0),AL10.AL_BUFFER,buffer.get(0));
		AL10.alSourcef(source.get(0),AL10.AL_PITCH,1.0f);
		AL10.alSourcef(source.get(0),AL10.AL_GAIN,1.0f);
		AL10.alSource(source.get(0),AL10.AL_POSITION,sourcePos);
		AL10.alSource(source.get(0),AL10.AL_VELOCITY,sourceVel);
		AL10.alSourcei(source.get(0),AL10.AL_LOOPING,AL10.AL_TRUE);
		if(AL10.alGetError()!=AL10.AL_NO_ERROR)
			throw new Error();
		AL10.alListener(AL10.AL_POSITION,listenerPos);
		AL10.alListener(AL10.AL_VELOCITY,listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION,listenerOri);
		AL10.alSourcePlay(source.get(0));
		long time=Sys.getTime();
		long elapse=0;

		while(!kbhit()){
			elapse+=Sys.getTime()-time;
			time+=elapse;
			if(elapse>10){
				elapse=0;
				sourcePos.put(0,sourcePos.get(0)+sourceVel.get(0));
				sourcePos.put(1,sourcePos.get(1)+sourceVel.get(1));
				sourcePos.put(2,sourcePos.get(2)+sourceVel.get(2));
				System.out.println(sourcePos.get(0));
				AL10.alSource(source.get(0),AL10.AL_POSITION,sourcePos);
			}
		}
		AL10.alSourceStop(source.get(0));
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
	}

	private boolean kbhit(){
		try{
			return(System.in.available()!=0);
		}catch(IOException ioe){}
		return false;
	}

	public static void main(String[] args){
		new Lesson2();
	}
}
