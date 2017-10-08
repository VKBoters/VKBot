package Core.tests;
import API.Command;
import API.module;
import API.moduleInfo;
import Core.version;
import Core.tests.commands.testmsg;

/** @author uis */

@moduleInfo(name = "test", internalName = "test", author="uis", version="0.1.1", build=version.CoreBuild)
public class modtest extends module{
	byte b=2;
	@Override
	public void enablePlugin() {
		System.out.println("testOnEnable");
	}
	@Override
	public void disablePlugin() {
		// TODO Auto-generated method stub
	}
	@Override
	public void onLoad() {
		System.out.println("testOnLoad");
		try {
			registerCommand("testmsg", (Command)testmsg.class.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
