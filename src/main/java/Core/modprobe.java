package Core;

import java.io.IOException;
import java.io.InvalidClassException;

import API.module;

public class modprobe extends ClassLoader{
	public modprobe() throws ClassNotFoundException{
		super(ClassLoader.getSystemClassLoader());
	}
	public module load(String path) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
//		if(!new File(path+".class").exists()){
			module c=(module) this.loadClass(path).newInstance();
//			m.onEnabling();
			if(c!=null){
				if(c.getClass().getAnnotation(API.moduleInfo.class)==null){
					throw new InvalidClassException("This is not module");
				}
				return c;
			}else{
				throw new NullPointerException("NPE");
			}
//		}else{
//			throw new IOException("No such file or directory");
//		}
	}
//	@Override
//	public Class<?> findClass(String path){
//		byte b[] = null;
//		try {
//			b=fetchClassFromFS(path+".class");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
///*		if(path.split("/").length==1){
//*			return defineClass(path.split("/")[path.split("/").length-1],b, 0, b.length);
//*		}else{
//*			return defineClass(path.split("/")[1],b, 0, b.length);
//*		}*/
//		return defineClass(path,b, 0, b.length);
//	}
//	
// @SuppressWarnings("resource")
//private byte[] fetchClassFromFS(String path) throws FileNotFoundException, IOException {
//    InputStream s = new FileInputStream(new File(path));
//    long length = new File(path).length();
//    if (length > Integer.MAX_VALUE) {
//      throw new IOException("File too large");
//    }
//    byte[] bytes = new byte[(int)length];
//    int os = 0;
//    int numRead = 0;
//    while (os < bytes.length
//        && (numRead=s.read(bytes, os, bytes.length-os)) >= 0) {
//      os += numRead;
//    }
//    s.close();
//    if (os < bytes.length) {
//      throw new IOException("Could not completely read file "+path);
//    }
//    return bytes;
//  }
}
