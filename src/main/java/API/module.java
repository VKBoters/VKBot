package API;

import java.util.HashMap;

import Core.bash;
import Core.messaging.msh;

public interface module{
	public void onLoad();
	public void enablePlugin();
	public void exec(String[] arg, bash bash);
	public void exec(String[] arg, msh msh);
	public void disablePlugin();
	public HashMap<String,Command> commands=new HashMap<String,Command>();
	public static void registerCommand(String cmd, Command command){
		commands.put(cmd,command);
	}
}
