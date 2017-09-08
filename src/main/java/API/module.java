package API;

import java.util.HashMap;

import Core.bash;
import Core.messaging.msh;

public class module{
	public void onLoad() {
	}
	public void enablePlugin() {
	}
	public void exec(String[] arg, bash bash) {
	}
	public void exec(String[] arg, msh msh) {
	}
	public void disablePlugin() {
	}
	public HashMap<String,Command> commands=new HashMap<String,Command>();
	protected void registerCommand(String string, Command newInstance) {
		commands.put(string, newInstance);
	}
}
