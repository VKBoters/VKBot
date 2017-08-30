package Core;

import java.io.IOException;
import java.io.InvalidClassException;

import API.module;

public class modprobe extends ClassLoader{
	public modprobe() throws ClassNotFoundException{
		super(ClassLoader.getSystemClassLoader());
	}
	public module load(String path) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		module c=(module) this.loadClass(path).newInstance();
		if(c!=null){
			if(c.getClass().getAnnotation(API.moduleInfo.class)==null){
				throw new InvalidClassException("This is not module");
			}
			return c;
		}else{
			throw new NullPointerException("NPE");
		}
	}
}
