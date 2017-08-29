package Core.messaging.callback;

import java.io.IOException;

import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.objects.messages.Message;

public class call extends CallbackApi{
	@Override
	public void messageNew(Integer id, Message message){
		try {
			new parser().parse(id, message.getBody(),message.getChatId());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
