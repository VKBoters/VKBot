import API.module;
import API.moduleInfo;
import Core.bash;
import Core.messaging.msh;
@moduleInfo(name = "test")
public class modtest implements module {

	@Override
	public void enablePlugin() {
		System.out.println("test");
	}

	@Override
	public void exec(String[] arg, bash bash) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exec(String[] arg, msh msh) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disablePlugin() {
		// TODO Auto-generated method stub

	}

}
