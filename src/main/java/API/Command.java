package API;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.messages.Message;

public class Command {
	public String exec(String args) throws Exception {
		return "";
	}
	public void exec(String args, int peerId, Message message, VkApiClient vk, UserActor actor) throws Exception {
	}
}
