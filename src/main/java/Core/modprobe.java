package Core;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import API.module;

public class modprobe extends ClassLoader{
	public modprobe() throws ClassNotFoundException{
		super(modprobe.class.getClassLoader());
	}
	public module load(String path) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		if (path.split(" ").length==1){
			module m=loadClass(path).asSubclass(API.module.class).newInstance();
//		Class<?> tmp=this.loadClass(path);
//		if (tmp==null){
//			throw new NullPointerException();
//		}
//		System.out.println(tmp);
//		Class<? extends module> tmp2=tmp.asSubclass(API.module.class);
//		tmp=null;
//		if (tmp2==null){
//			throw new NullPointerException();
//		}
//		module m=tmp2.newInstance();
//		tmp2=null;
//		if (m==null){
//			throw new NullPointerException();
//		}
//		if(tmp.getAnnotation(moduleInfo.class)!=null){
//			tmp.newInstance();
//		tmp.getProtectionDomain().
		return m;
//		}else{
//			throw new InvalidClassException("This is not a module");
//		}
		}else{
			URL[] u={new URL(path.split(" ")[0])};
//			URLClassLoader l=new URLClassLoader(u);
			@SuppressWarnings("resource")
			module m=new URLClassLoader(u,modprobe.class.getClassLoader()).loadClass(path.split(" ")[1]).asSubclass(module.class).newInstance();
			u=null;
			if(m!=null){
			return m;
			}else{
				throw new NullPointerException();
			}
		}
	}
}
