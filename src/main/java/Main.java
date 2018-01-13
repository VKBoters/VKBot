import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.LoggerFactory;

import Core.bash;
public class Main{
	public Main() throws Exception{
//		TransportClient c=HttpTransportClient.getInstance();
//		HttpClientBuilder cl=HttpClientBuilder.create();
//		VkApiClient vk = new VkApiClient(c);
//		MessagesGetLongPollServerQuery res=vk.messages().getLongPollServer(actor).useSsl(true);
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		bash b=new bash(br);
		b.start();
		b.join();
	}
	public static void main(String[] args) {
//		System.setProperty("java.util.logging.FileHandler.pattern", "bot.log");
//		Slf4JLoggerFactory.getInstance("").info("Test");
		LoggerFactory.getLogger("HttpTransportClient").info("Best");
		try {
			new Main();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	private static void test(){
//	}
}
