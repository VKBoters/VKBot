package Core.messaging;

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
import com.vk.api.sdk.objects.messages.Message;

import API.Command;
import API.module;
import API.moduleInfo;
import Core.modprobe;

/** @author uis */
@moduleInfo(author="uis",internalName="msh",name="Message Shell",version=Core.version.CoreVersion,build=Core.version.CoreBuild)
public class msh extends Thread{
//	File l;
//	public PrintWriter w;
	public static final int adminId=220392464;
	TransportClient tc=new HttpTransportClient();
	public VkApiClient c=new VkApiClient(tc);
//	VkStreamingApiClient sc=new VkStreamingApiClient(tc);
	UserActor act;
	public HashMap<String,module> loaded=new HashMap<String,module>();
	public HashMap<String,module> enabled=new HashMap<String,module>();
	public HashMap<String,Command> aliases=new HashMap<String, Command>();
	LongpollParams lpp;
	public msh(){
//		l=new File("access.log");
//		if(!l.exists()){
//			try {
//				l.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if(!l.canWrite()){
//			l.setWritable(true);
//		}
	}
	public void mshInit(int id, String token){
		try {
			act=new UserActor(id,token);
			c.account().setOnline(act).execute();
			getLPS();
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
		}
	}
	void getLPS() throws ApiException, ClientException{
		lpp=c.messages().getLongPollServer(act).lpVersion(4).execute();
	}
//	public void parse(int sender,String what) throws IOException, InterruptedException{
//		String sum="";
//		String ins;
//		if(what.startsWith("/")){
//			userCmd(what,sender);
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
//		}else{
//			
//		}
//	}
	public void run(){
		int ts=lpp.getTs();
		String key=lpp.getKey();
		String server=lpp.getServer();
		Gson gson=c.getGson();
		JsonObject r;
		ClientResponse resp=null;
		Object[][] o;
//		Object[] tmp;
		while(true){
			try {
				try{
				resp=tc.get("https://"+server+"?act=a_check&key="+key+"&ts="+ts+"&wait=90&version=2&mode=2");
				}catch(NullPointerException e){
					getLPS();
					ts=lpp.getTs();
					key=lpp.getKey();
					resp=null;
				}
//				System.out.println(resp.getContent());
				if(resp!=null){
					r=(JsonObject) new JsonParser().parse(new JsonReader(new StringReader(resp.getContent())));
					if(r.has("failed")){
						System.err.println(gson.fromJson(r.get("failed"), Object.class));
		//				l.error("Longpoll failed"+gson.fromJson(r.get("failed"), Object.class));
					}
					if(r.has("ts")){
						ts=gson.fromJson(r.get("ts"), int.class);
						o=gson.fromJson(r.get("updates"), Object[][].class);
						for(int i=0; i<o.length; i++){
		//					tmp=o[i];
							if((double)o[i][0]==4){
		//						System.out.println(tmp[0]+" "+((Double)tmp[3]).intValue()+" "+tmp[5]);
								if(((String)o[i][5]).startsWith("/")&&((String)o[i][5]).split(" ")[0]!="/"){
		//							w.println(c.users().get(act).userIds(((Integer)((Double)o[i][3]).intValue()).toString()).execute().get(0).getNickname()+" requested \""+(String)o[i][5]+"\"");
									userCmd(((String)o[i][5]).replaceFirst("/", ""),((Double)o[i][3]).intValue(),c.messages().getById(act, ((Double)o[i][1]).intValue()).execute().getItems().get(0));
								}else if(((String)o[i][5]).startsWith("\\")){
									if(c.messages().getById(act, ((Double)o[i][1]).intValue()).execute().getItems().get(0).getFromId().intValue()==220392464){
										adminCmd(((String)o[i][5]).replaceFirst("\\", ""), ((Double)o[i][3]).intValue());
									}else{
		//								w.println(c.users().get(act).userIds(((Integer)((Double)o[i][3]).intValue()).toString()).execute().get(0).getNickname()+" was tried to exec admin command \""+(String)o[i][5]+"\"");
										c.messages().send(act).peerId(((Double)o[i][3]).intValue()).message("\"403 говорит о том, что я не стану вести бесед с ментом.\"").attachment("audio220392464_456239152").execute();
									}
								}
							}
						}
					}else{
						getLPS();
						ts=lpp.getTs();
						key=lpp.getKey();
					}
					resp=null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				resp=null;
			}
		}
	}
	public void adminCmd(String cmd, int peerId) throws Exception{
		if(cmd.startsWith("modprobe")){
			if(cmd.split(" ").length==2||cmd.split(" ").length==3){
				module tmp=new modprobe().load(cmd.replaceFirst("modprobe ",""));
				if(!enabled.containsKey(tmp.getClass().getAnnotation(moduleInfo.class).internalName())){
					tmp.onLoad();
					c.messages().send(act).peerId(peerId).message("Module \""+tmp.getClass().getAnnotation(moduleInfo.class).name()+"\" loaded").execute();
					tmp.enablePlugin();
					enabled.put(tmp.getClass().getAnnotation(moduleInfo.class).internalName(), tmp);
				}else{
					c.messages().send(act).peerId(peerId).message("Mudule aleready loaded").execute();
				}
			}else if(cmd.startsWith("addAlias")){
				if(cmd.split(" ").length==3){
					aliases.put(cmd.split(" ")[1], enabled.get(cmd.split(" ")[2].split(":")[0]).commands.get(cmd.split(" ")[2].split(":")[1]));
				}else{
					c.messages().send(act).peerId(peerId).message("Usage: addAlias [alias] [command]").execute();
				}
			}else if(cmd.startsWith("write")){
//				w.close();
//				w=new PrintWriter(new File("access.log"));
			}else if(cmd.split(" ").length==2){
				aliases.remove(cmd.split(" ")[1]);
			}else{
				c.messages().send(act).peerId(peerId).message("Usage: delAlias [alias]").execute();
			}
		}
	}
	public void userCmd(String cmd, int peerId, Message message){
		try {
			if(cmd.split(" ")[0].contains(":")){
				if(message.getFromId().intValue()==220392464){
					if(cmd.startsWith("admin:")){
						adminCmd(cmd.replaceFirst(cmd.split(" ")[0]+" ", ""), peerId);
					}else{
						if(enabled.get(cmd.split(":")[0]).commands.containsKey(cmd.split(":")[1])){
							System.out.println(enabled.get(cmd.split(":")[0]).commands.get(cmd.split(":")[1]).exec(cmd.replaceFirst(cmd.split(" ")[0]+" ", "")));
						}else{
							System.out.println("Module \""+cmd.split(":")[0]+"\" doesn't contains command \""+cmd.split(":")[1]+"\"");
						}
					}
				}else{
					c.messages().send(act).peerId(peerId).message("\"403 говорит о том, что я не стану вести бесед с ментом.\"").attachment("audio220392464_456239152").execute();
				}
			}else if(cmd.equals("ping")){
				c.messages().send(act).peerId(peerId).message("pong").execute();
			}else if(aliases.containsKey(cmd.split(" ")[0])){
				try {
					aliases.get(cmd.split(" ")[0]).exec(cmd.replaceFirst(cmd.split(" ")[0]+" ", ""), peerId, message, c, act);
				} catch (Exception e) {
					c.messages().send(act).peerId(peerId).message("Команда \""+cmd.split(" ")[0]+"\" решила плюнуть исключение: "+e.getMessage()).execute();
				}
//			}else if(cmd.contains(":")&&cmd.startsWith(cmd.split(":")[0]+":")&&enabled.containsKey(cmd.split(":")[0])){
//				if(enabled.get(cmd.split(":")[0]).commands.containsKey(cmd.split(":")[1])){
//					try {
//						enabled.get(cmd.split(":")[0]).commands.get(cmd.split(":")[1]).exec(cmd.replaceFirst(cmd.split(" ")[0]+" ", ""));
//					} catch (Exception e) {
//						c.messages().send(act).peerId(peerId).message("Команда \""+cmd.split(" ")[0]+"\" решила плюнуть исключение: "+e.getMessage()).execute();
//					}
//				}else{
//					c.messages().send(act).peerId(peerId).message("Хмм...\n В модуле \""+cmd.split(":")[0]+"\" нет комманды \""+cmd.split(":")[1]+"\"");
//				}
			}else if(cmd.split(" ")[0].equals("")){}else{
				c.messages().send(act).peerId(peerId).message("\"Хозяева не нашли вещей в своей квартире? А мы-то ту причём? 404!\"").attachment("audio220392464_456239152").execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
