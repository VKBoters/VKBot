package Core;

import java.io.IOException;

import API.module;

public class modprobe extends ClassLoader{
	public modprobe() throws ClassNotFoundException{
		super(modprobe.class.getClassLoader());
	}
	public module load(String path) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
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
	}
}
