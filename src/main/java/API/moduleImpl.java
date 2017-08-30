package API;

import java.util.HashMap;

public abstract class moduleImpl implements module{
	HashMap<String,Class<? extends Command>> commands;
	public void registerCommand(String cmd, Class<? extends Command> command){
		commands.put(cmd,command);
	}
}
