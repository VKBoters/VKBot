//import java.awt.Desktop;
//import java.net.URI;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
//import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
//import com.vk.api.sdk.exceptions.ApiCaptchaException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public class test extends Thread{
//	  private Server server;
	private Logger log = LoggerFactory.getLogger(test.class);
	Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		try {new test();} catch (ApiException | ClientException | InterruptedException e) {e.printStackTrace();}
	}
//	TODO https://oauth.vk.com/authorize?client_id=5973066&display=page&redirect_uri=https://api.vk.com/test.html&scope=friends,messages,offline,account,audio,wall,video,likes,photos,notes,notifications&response_type=token
		public test() throws ApiException, ClientException, InterruptedException{
//		int id = 295060128;
//		String msg = "Ты - пидор и все это знают!!! Заебись!!! Ебись!!! И уебись!!! Только не забудь сдохнуть от спида!!!";
//		int id = 252963381;
//		int id = 220392464;
//		int id = 249939593;
//		int id = 406212707;
		TransportClient transportClient = HttpTransportClient.getInstance();
		log.info("Initialization VK API");
		VkApiClient vk = new VkApiClient(transportClient);
		log.info("VK API inited");
//		ServiceClientCredentialsFlowResponse auths = null;
//		try {auths = vk.oauth().serviceClientCredentialsFlow(5973066, "6FWpwvAbzB7iCBWPd1D5").execute();} catch (ApiException | ClientException e1) {log.error(("Error in VK Service Auth"));e1.printStackTrace();}
//		System.out.println(auths.getAccessToken());
//		rsps.
//		ServiceActor actors = new ServiceActor(5973066,"6FWpwvAbzB7iCBWPd1D5",auths.getAccessToken());
		UserActor actoru=new UserActor(220392464,"9c63f6f8995e47ef1dc566d741558aedfc520e8974f69680b49c237ed0ecc275f7b310dd760256856a2a0");
//		UserActor actoru=new UserActor(252250684,"03b681c8c9338ca2bf550738c169d54c1235fd109476c5b501c9ae3acde441052aa801457bea5e29777a2");
//		UserActor actoru=new UserActor(429814380,"4ef99b95ba185f674c092823678605cd9d1fcbd847b392106205988d0714e3b3687a64e6c935fb5da62e1");
//		UserActor actoru=new UserActor(426545295,"5a024361ba909332fcbf7290dceb09262d103f2ce6dda218ff91b5cf19ce8190cafb6272a44717c5d983d");
//		GroupActor actorg=new GroupActor(145807786, "96ad4b74f7f930120bba660d3b49ad9dac380751f378f851d49affa3e7adaa6db721a11bab1a336276606");

//while(true){
//	try {
//				vk.messages().send(actoru).userId(id).message(msg).execute();this.sleep(334);
//			} catch (ApiCaptchaException e) {
//				e.printStackTrace();
//				System.out.println("Enter captcha: ");
//				try {
//					Desktop.getDesktop().browse(new URI(e.getImage()));
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}vk.messages().send(actoru).userId(id).message(msg).captchaSid(e.getSid()).captchaKey(sc.nextLine()).execute();this.sleep(334);}
//}
//			actors.
//		UserAuthResponse authResponse = null;
//		System.out.print("Введите код: ");String key = sc.nextLine();try {
//			authResponse = vk.oauth().userAuthorizationCodeFlow(5973066, "6FWpwvAbzB7iCBWPd1D5", "http://localhost", key).execute();
//			authResponse = vk.oauth().userAuthorizationCodeFlow(638461, clientSecret, redirectUri, code).execute();
//		} catch (ApiException | ClientException e) {
//			e.printStackTrace();
//		}
//		System.out.println(authResponse);
//		UserActor actoru=new UserActor(220392464, "b3353507a43e74d4df93fe3836b182e17001d7ec2ceb3e7fd435df357219f6bc11356effa324252599f4d");
//		actor = new UserActor(220392464, "");
//		vk.groups().getMembers(as);
//		System.out.println(vk.groups().getMembers(as).execute());
//		try {
//			System.out.println(vk.groups().getMembers(as).execute());
			//			Scanner sc = new Scanner(System.in);
//			String key = sc.next();
//			while(true){
//			System.out.println(key);}
//		}catch (ApiException | ClientException e) {e.printStackTrace();}
//		AccountGetBannedQuery ban = vk.account().getBanned(actor);
//		ban.toString();
//		System.out.println(ban);
//		AccountUnbanUserQuery resp=vk.account().unbanUser(actor, 220392464);
//		System.out.println(resp);
	}
}
