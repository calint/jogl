package openal;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;
public class Lesson2{
	public static void main(String[] args) throws Throwable{
		new Lesson2();
	}
	public Lesson2() throws Throwable{
		String[]sounds=new String[]{"Roland-GR-1-Pick-Bass-2-C4.wav","Footsteps.wav"};
		String soundfile=sounds[1];
		FloatBuffer sourcePos=BufferUtils.createFloatBuffer(3).put(new float[]{-100.0f,10.0f,0.0f});
		FloatBuffer sourceVel=BufferUtils.createFloatBuffer(3).put(new float[]{0.5f,0.0f,0.0f});
		FloatBuffer listenerPos=BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});
		FloatBuffer listenerVel=BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});
		FloatBuffer listenerOri=BufferUtils.createFloatBuffer(6).put(new float[]{0.0f,0.0f,-1.0f,0.0f,1.0f,0.0f});
		IntBuffer buffer=BufferUtils.createIntBuffer(1);
		IntBuffer source=BufferUtils.createIntBuffer(1);
		sourcePos.flip();
		sourceVel.flip();
		listenerPos.flip();
		listenerVel.flip();
		listenerOri.flip();
		AL.create(null,15,44010,false);
		AL10.alGetError();
		if(AL10.alGetError()!=AL10.AL_NO_ERROR)
			throw new Error();
		AL10.alGenBuffers(buffer);
		if(AL10.alGetError()!=AL10.AL_NO_ERROR)
			throw new Error();
		WaveData waveFile=WaveData.create(soundfile);
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
		long t0=Sys.getTime();
		while(sourcePos.get(0)<100.0f){
			sourcePos.put(0,sourcePos.get(0)+sourceVel.get(0));
			sourcePos.put(1,sourcePos.get(1)+sourceVel.get(1));
			sourcePos.put(2,sourcePos.get(2)+sourceVel.get(2));
			System.out.println((Sys.getTime()-t0)+"  "+sourcePos.get(0));
			AL10.alSource(source.get(0),AL10.AL_POSITION,sourcePos);
			try{
				Thread.sleep(50);
			}catch(InterruptedException ignored){}
		}
		AL10.alSourceStop(source.get(0));
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
	}
}
