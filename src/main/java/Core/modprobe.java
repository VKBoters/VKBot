package Core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import API.module;
import API.moduleInfo;

public class modprobe extends ClassLoader{
	public modprobe(ClassLoader loader/*,URL[] path*/) throws ClassNotFoundException{
		super(loader);
	}
	public module load(String path) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
//		Class<?> c=this.loadClass(path);
//		this.de
//		Annotation a=c.getAnnotation(moduleInfo.class);
		System.out.println("Do you like to load class module "+this.loadClass(path).getAnnotation(moduleInfo.class).name()+"\n[Y/n]");
		if(new BufferedReader(new InputStreamReader(System.in)).readLine().equals("n")){
			return null;
		}else{
			module m=(module) this.loadClass(path).newInstance();
			m.onLoad();
//			c=null;
			return m;
		}
	}
	@Override
	public Class<?> findClass(String path){
		byte b[] = null;
		try {
			b=fetchClassFromFS(path+".class");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defineClass(path,b, 0, b.length);
	}
	
 private byte[] fetchClassFromFS(String path) throws FileNotFoundException, IOException {
    InputStream is = new FileInputStream(new File(path));
    long length = new File(path).length();
    if (length > Integer.MAX_VALUE) {
      throw new IOException("File too large");
    }
    byte[] bytes = new byte[(int)length];
    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length
        && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
      offset += numRead;
    }
    if (offset < bytes.length) {
      throw new IOException("Could not completely read file "+path);
    }
    is.close();
    return bytes;
  }
}
