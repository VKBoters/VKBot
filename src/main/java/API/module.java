package API;

import Core.bash;
import Core.messaging.msh;

public interface module {
	public void onLoad();
	public void enablePlugin();
	public void exec(String[] arg, bash bash);
	public void exec(String[] arg, msh msh);
	public void disablePlugin();
}
