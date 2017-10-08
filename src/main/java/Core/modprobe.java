package Core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

import API.module;

public class modprobe extends ClassLoader{
	public modprobe() throws ClassNotFoundException{
		super(modprobe.class.getClassLoader());
	}
	public module load(String path) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException{
		if (path.split(" ").length==1){
			module m=loadClass(path).asSubclass(API.module.class).newInstance();
		return m;
		}else{
			JarClassLoader jcl=new JarClassLoader(modprobe.class.getClassLoader());
			if(path.startsWith("http://")||path.startsWith("https://")){
				jcl.add(new URL(path.split(" ")[0]));
			}else{
				jcl.add(path.split(" ")[0]);
			}
//			module m=(module) jcl.loadClass(path.split(" ")[1]).asSubclass(API.module.class).newInstance();
			module m=(module) JclObjectFactory.getInstance().create(jcl, path.split(" ")[1]);
			if(m!=null){
				return m;
			}else{
				throw new NullPointerException();
			}
		}
	}
}
