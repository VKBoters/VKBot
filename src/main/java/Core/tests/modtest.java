package Core.tests;
import API.Command;
import API.module;
import API.moduleInfo;

import Core.bash;
import Core.messaging.msh;
import Core.tests.commands.testmsg;

/** @author uis */

@moduleInfo(name = "test", internalName = "test", author="uis")
public class modtest implements module{
	@Override
	public void enablePlugin() {
		System.out.println("testOnEnable");
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
	@Override
	public void onLoad() {
		System.out.println("testOnLoad");
		try {
			modtest.registerCommand("testmsg", (Command)testmsg.class.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	private static void registerCommand(String string, Command newInstance) {
		commands.put(string, newInstance);
	}
}
