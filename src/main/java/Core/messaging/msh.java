package Core.messaging;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

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
	public void run(){
		int ts=lpp.getTs();
		String key=lpp.getKey();
		String server=lpp.getServer();
		Gson gson=c.getGson();
		JsonObject r;
		ClientResponse resp;
		Object[][] o;
		Object[] tmp;
		while(true){
			try {
				resp=tc.get("https://"+server+"?act=a_check&key="+key+"&ts="+ts+"&wait=90&version=2");
				System.out.println(resp.getContent());
				r=(JsonObject) new JsonParser().parse(new JsonReader(new StringReader(resp.getContent())));
				if(r.has("failed")){
					System.err.println(gson.fromJson(r.get("failed"), Object.class));
	//				l.error("Longpoll failed"+gson.fromJson(r.get("failed"), Object.class));
				}
				ts=gson.fromJson(r.get("ts"), int.class);
				o=gson.fromJson(r.get("updates"), Object[][].class);
				for(int i=0; i<o.length; i++){
					tmp=o[i];
					if((double)tmp[0]==4){
	//					System.out.println(tmp[0]+" "+((Double)tmp[3]).intValue()+" "+tmp[5]);
						userCmd((String)tmp[5],((Double)tmp[3]).intValue()/*,c.messages().getById(act, ((Double)tmp[1]).intValue()).execute().getItems().indexOf(0)*/);
					}
				}
			} catch (IOException/* | ApiException | ClientException */e) {
				e.printStackTrace();
			}
		}
	}
	public void userCmd(String cmd, int peerId/*, int wroteBy*/){
		try {
			if(cmd.equals("ping")){
					c.messages().send(act).peerId(peerId).message("pong").execute();
//			}else if(){
				
			}else{
				
			}
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
		}
	}
}
