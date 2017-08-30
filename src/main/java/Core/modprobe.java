package Core;

import java.io.IOException;
import java.io.InvalidClassException;

import API.module;
import API.moduleInfo;

public class modprobe extends ClassLoader{
	public modprobe() throws ClassNotFoundException{
		super(ClassLoader.getSystemClassLoader());
	}
	public module load(String path) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		Class<?> tmp=this.loadClass(path);
		if (tmp==null){
			throw new NullPointerException();
		}
		if(tmp.getAnnotation(moduleInfo.class).name()!=null){
			module m=(module) tmp.newInstance();
			return m;
		}else{
			throw new InvalidClassException("This is not a module");
		}
	}
}
