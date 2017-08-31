package Core.messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.LongpollParams;
import com.vk.api.sdk.objects.polls.Answer;
import com.vk.api.sdk.streaming.clients.VkStreamingApiClient;

import API.module;
import API.moduleInfo;

/** @author uis */
@moduleInfo(author="uis",internalName="msh",name="Message Shell",version=Core.version.CoreVersion,build=Core.version.CoreBuild)
public class msh extends Thread{
	TransportClient tc=new HttpTransportClient();
	VkApiClient c=new VkApiClient(tc);
	VkStreamingApiClient sc=new VkStreamingApiClient(tc);
	UserActor act;
	HashMap<String,module> enabled=new HashMap<String,module>();
	LongpollParams lpp;
	public msh(){
		try {
			lpp=c.messages().getLongPollServer(act).lpVersion(4).execute();
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
		}
	}
	public void parse(int sender, String what) throws IOException, InterruptedException{
		String sum="";
//		String ins;
		if(what.startsWith("/")){
			try {
				c.messages().send(act).userId(sender).message(userCmd(what.replace("/", ""))).execute();
			} catch (ApiException | ClientException e) {
				e.printStackTrace();
			}
		}else if(what.startsWith(">")){
			Process i=Runtime.getRuntime().exec(what.replaceFirst(">", ""));
			BufferedReader r=new BufferedReader(new InputStreamReader(i.getInputStream()));
			while (true)
			{
				String str=r.readLine();
				if(str==null){
					break;
				}
				sum+=str+"\n";
			}
			try {
				c.messages().send(act).userId(sender).message(sum).execute();
			} catch (ApiException | ClientException e) {
				e.printStackTrace();
			}
		}else{
			
		}
	}
	Answer a;
	@Override
	public void run(){
		int ts=lpp.getTs();
		String key=lpp.getKey();
		String server=lpp.getServer();
//		JsonObject jo;
//		LongPollResponse r;
//		Message m;
		try {
			ClientResponse resp;
			while(true){
				resp=tc.get("https://"+server+"?act=a_check&key="+key+"&ts="+ts+"&wait=90&version=2");
//				jo=new JsonObject();
//				jo.
//				ts=jo.getInt("ts");
//				r.updates.
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String userCmd(String what){
		return what;
	}
}
