package a.d4;

import b.a;
import b.bin;
import b.cacheable;
import b.xwriter;

public class ¤ extends a implements cacheable,bin{
	static final long serialVersionUID=1;
	public String filetype(){return "jnlp";}
	public String contenttype(){return "application/x-java-jnlp-file";}
	public boolean cacheforeachuser(){return false;}
	public String lastmod(){return b.b.tolastmodstr(0);}
	public long lastmodupdms(){return 2000;}
	public void to(final xwriter x)throws Throwable{
		b.b.path("d4.jnlp").to(x);
	}
}
