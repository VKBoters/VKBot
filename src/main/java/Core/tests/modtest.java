package Core.tests;
import API.moduleImpl;
import API.moduleInfo;

import Core.bash;
import Core.messaging.msh;

/** @author uis */

@moduleInfo(name = "test", internalName = "test", author="uis")
public class modtest extends moduleImpl{
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
	}
}
