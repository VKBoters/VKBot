package Core.tests.commands;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.messages.Message;

import API.Command;

public class testmsg extends Command{
	@Override
	public String exec(String args) throws Exception{
		return "Just test";
	}
	@Override
	public void exec(String args, int peerId, Message message, VkApiClient vk, UserActor actor) throws Exception{
		vk.messages().send(actor).peerId(peerId).message("Test - is the best part of my hard work!").execute();
	}
}
