import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Core.bash;

public class Main{
	public Main() throws Exception{
//		TransportClient c=HttpTransportClient.getInstance();
//		HttpClientBuilder cl=HttpClientBuilder.create();
//		VkApiClient vk = new VkApiClient(c);
//		CallbackApi ca = new CallbackApi();
//		UserActor actor=new UserActor(252250684,"03b681c8c9338ca2bf550738c169d54c1235fd109476c5b501c9ae3acde441052aa801457bea5e29777a2");
//		MessagesGetLongPollServerQuery res=vk.messages().getLongPollServer(actor).useSsl(true);
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		bash b=new bash(br);
		b.start();
		b.join();
//		while(true){
//			res.execute();
//			c.
//		}
	}
	public static void main(String[] args) {
		try {
			new Main();
//			test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	private static void test(){
//	}
}
