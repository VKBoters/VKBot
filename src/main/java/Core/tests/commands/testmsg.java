package Core.tests.commands;

import API.Command;

public class testmsg implements Command{

	@Override
	public String exec(String args) {
		return "Just test";
	}

}
