package Core;

import java.io.IOException;
import java.io.InvalidClassException;

import API.moduleInfo;

public class modprobe extends ClassLoader{
	public modprobe() throws ClassNotFoundException{
		super(ClassLoader.getSystemClassLoader());
	}
	public Class<?> load(String path) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		Class<?> tmp=this.loadClass(path);
		if (tmp==null){
			throw new NullPointerException();
		}
		if(tmp.getAnnotation(moduleInfo.class).internalName()!=null){
			tmp.newInstance();
			return tmp;
		}else{
			throw new InvalidClassException("This is not a module");
		}
	}
}
