package Core;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import API.module;

public class modprobe extends ClassLoader{
//	public modprobe() throws ClassNotFoundException{
//		super(modprobe.class.getClassLoader());
//	}
	@SuppressWarnings("null")
	public module load(String path) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		if (path.split(" ").length==1){
		module tmp=this.loadClass(path).asSubclass(module.class).newInstance();
		if (tmp==null){
			throw new NullPointerException();
		}
//		if(tmp.getAnnotation(moduleInfo.class)!=null){
//			tmp.newInstance();
			return tmp;
//		}else{
//			throw new InvalidClassException("This is not a module");
//		}
		}else{
			URL[] u=null;
			u[0]=new URL(path.split(" ")[0]);
//			URLClassLoader l=new URLClassLoader(u);
			@SuppressWarnings("resource")
			module m=new URLClassLoader(u).loadClass(path.split(" ")[1]).asSubclass(module.class).newInstance();
			if(m!=null){
			return m;
			}else{
				throw new NullPointerException();
			}
		}
	}
}
