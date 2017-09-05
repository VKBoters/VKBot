package Core.messaging;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.LongpollParams;

import API.Command;
import API.module;
import API.moduleInfo;
import Core.messaging.longPolling.LongPollResponse;
/** @author uis */
@moduleInfo(author="uis",internalName="msh",name="Message Shell",version=Core.version.CoreVersion,build=Core.version.CoreBuild)
public class msh extends Thread{
	TransportClient tc=new HttpTransportClient();
	public VkApiClient c=new VkApiClient(tc);
//	VkStreamingApiClient sc=new VkStreamingApiClient(tc);
	UserActor act;
	public HashMap<String,module> loaded=new HashMap<String,module>();
	public HashMap<String,module> enabled=new HashMap<String,module>();
	public HashMap<String,Command> aliases=new HashMap<String, Command>();
	LongpollParams lpp;
	public void mshInit(int id, String token){
		try {
			act=new UserActor(id,token);
			c.account().setOnline(act).execute();
			lpp=c.messages().getLongPollServer(act).lpVersion(4).execute();
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
		}
	}
	public void parse(int sender,String what) throws IOException, InterruptedException{
//		String sum="";
//		String ins;
		if(what.startsWith("/")){
			userCmd(what,sender);
//		}else if(what.startsWith(">")){
//			Process i=Runtime.getRuntime().exec(what.replaceFirst(">", ""));
//			BufferedReader r=new BufferedReader(new InputStreamReader(i.getInputStream()));
//			while (true)
//			{
//				String str=r.readLine();
//				if(str==null){
//					break;
//				}
//				sum+=str+"\n";
//			}
//			try {
//				c.messages().send(act).userId(sender).message(sum).execute();
//			} catch (ApiException | ClientException e) {
//				e.printStackTrace();
//			}
		}else{
			
		}
	}
//	Answer a;
	Logger l=LoggerFactory.getLogger(msh.class);
	public void run(){
		int ts=lpp.getTs();
		String key=lpp.getKey();
		String server=lpp.getServer();
		Gson gson=c.getGson();
		JsonObject r;
		LongPollResponse lpr;
//		Message m;
		ClientResponse resp;
		while(true){
			try {
			resp=tc.get("https://"+server+"?act=a_check&key="+key+"&ts="+ts+"&wait=90&version=2");
			System.out.println(resp.getContent());
			r=(JsonObject) new JsonParser().parse(new JsonReader(new StringReader(resp.getContent())));
			if(r.has("failed")){
//				LongPollFailed f;
//				f=gson.fromJson(r.get("failed"), LongPollFailed.class);
				l.error("Longpoll failed"+gson.fromJson(r.get("failed"), Object.class));
			}
			ts=gson.fromJson(r.get("ts"), int.class);
//			lpr=gson.fromJson(r.get("updates"), LongPollResponse.class);
//				r.
//				lpr=gson.fromJson(resp.getContent(),LongPollResponse.class);
//				if(lpr.updates.length!=0&&lpr.updates!=null){
//					if(lpr.updates[0]==(Object)4){
////						parse((int)lpr.updates[1]);
//					}
//				}
//				jo.
//				ts=jo.getInt("ts");
//				r.updates.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void userCmd(String what, int id){
		try {
			if(what.equals("ping")){
					c.messages().send(act).chatId(id).message("pong").execute();
//			}else if(){
				
			}else{
				
			}
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
		}
	}
}
