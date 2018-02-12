package Core.messaging;

import java.io.File;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
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
import API.multiuser.ProfileManager;
import API.multiuser.ServiceManager;
import API.multiuser.VKUserProfile;
import Core.modprobe;
import net.marketer.RuCaptcha;

/** @author uis */
@moduleInfo(author="uis",internalName="msh",name="Message Shell",version=Core.version.CoreVersion,build=Core.version.CoreBuild)
public class msh extends Thread implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5663253346224888371L;
	ProfileManager man;
	public static final int adminId=220392464;
	TransportClient tc=new HttpTransportClient();
	public VkApiClient c=new VkApiClient(tc);
	UserActor act;
	public static HashMap<String,module> loaded=new HashMap<String,module>();
	public static HashMap<String,module> enabled=new HashMap<String,module>();
	public HashMap<String,Command> aliases=new HashMap<String, Command>();
	LongpollParams lpp;
//	public File f;
	public msh(){
		net.marketer.RuCaptcha.API_KEY="9d758210a28cd44bc0abff15066087fc";
//		f=file;
	}
	public void mshInit(int id, String token){
		man=ServiceManager.getProfileManager("v");
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
			} catch(com.vk.api.sdk.exceptions.ApiCaptchaException e) {
				try {
					net.marketer.Utility.saveImgByUrl(new URL(e.getImage()), "/tmp/cap/Cap"+e.getSid()+".jpg");
					cid=net.marketer.RuCaptcha.postCaptcha(new File("/tmp/cap/Cap"+e.getSid()+".jpg"));
					for(@SuppressWarnings("unused")
					byte c=0; 0<10; c++){
						tmpstr=RuCaptcha.getDecryption(cid);
						if(tmpstr.startsWith("OK")){
							this.c.account().setOnline(act).captchaKey(tmpstr.substring(3)).captchaSid(e.getSid()).execute();
						}
						sleep(1000);
					}
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}catch (Exception e) {
				e.printStackTrace();
				resp=null;
			}
		}
	}
	String cid;
	String tmpstr;
public void adminCmd(String cmd, int peerId) throws Exception{
	if(cmd.startsWith("modprobe")){
		
		if(cmd.split(" ").length==2||cmd.split(" ").length==3){
			module tmp=new modprobe().load(cmd.replaceFirst("modprobe ",""));
			if(!enabled.containsKey(tmp.getClass().getAnnotation(moduleInfo.class).internalName())){
				tmp.onLoad();
				c.messages().send(act).peerId(peerId).message("Module \""+tmp.getClass().getAnnotation(moduleInfo.class).name()+"\" loaded").execute();
				tmp.enablePlugin();
				enabled.put(tmp.getClass().getAnnotation(moduleInfo.class).internalName(), tmp);
				buildHelp();
			}else{
				c.messages().send(act).peerId(peerId).message("Module aleready loaded").execute();
			}
		}else{
			c.messages().send(act).peerId(peerId).message("Usage: /admin:modprobe [Path to JAR] (Class)").execute();
		}
		}else if(cmd.startsWith("addAlias")){
			if(cmd.split(" ").length==3){
				aliases.put(cmd.split(" ")[1], enabled.get(cmd.split(" ")[2].split(":")[0]).commands.get(cmd.split(" ")[2].split(":")[1]));
//				buildHelp();
			}else{
				c.messages().send(act).peerId(peerId).message("Usage: addAlias [alias] [command]").execute();
			}
		}else if(cmd.startsWith("delAlias")) {
			if(cmd.split(" ").length==2){
				aliases.remove(cmd.split(" ")[1]);
//				buildHelp();
			}else{
				c.messages().send(act).peerId(peerId).message("Usage: delAlias [alias]").execute();
			}
		}else{
			c.messages().send(act).peerId(peerId).message("Такой комманды нет").execute();
		}
//	}
}
	
	
	
	
	Command tmp;
	int senderId;
	public void userCmd(String com, int peerId, Message message){
		try {
			String[] cmd=com.split(" ");
			senderId=message.getUserId().intValue();
			if(cmd[0].contains(":")&&cmd[0].split(":").length==2){
//				if(senderId==adminId){
					if(cmd[0].startsWith("admin:")){
						adminCmd(cmd.toString().replaceFirst("admin:", ""), peerId);
					}else{
						if(enabled.get(cmd[0].split(":")[0]).commands.containsKey(cmd[0].split(":")[1])){
						if(getVKUserOPLevel(senderId)<enabled.get(cmd[0].split(":")[0]).commands.get(cmd[0].split(":")[1]).OPLevel()){
							c.messages().send(act).peerId(peerId).message("Ваш уровень доступа мал для использования этой комманды\nВаш уровень: "+getVKUserOPLevel(senderId)+"\nНеобходимый: "+enabled.get(cmd[0].split(":")[0]).commands.get(cmd[0].split(":")[1]).OPLevel()).execute();
						}else {
						try{
//							System.out.println(cmd.toString());
//							System.out.println(cmd.toString().replaceFirst(cmd[0]+" ", ""));
							enabled.get(cmd[0].split(":")[0]).commands.get(cmd[0].split(":")[1]).exec(com.replaceFirst(cmd[0]+" ", ""), peerId, message, c, act);
                        } catch (Exception e) {
						c.messages().send(act).peerId(peerId).message("Команда \""+cmd[0]+"\" решила плюнуть исключение: "+e.getMessage()).execute();
                        }
						}
						}else{
							c.messages().send(act).peerId(peerId).message("В модуле \""+cmd[0].split(":")[0]+"\" нет комманды \""+cmd[0].split(":")[1]+"\"").execute();
						}
					}
//				}else{
//					c.messages().send(act).peerId(peerId).message("\"403 говорит о том, что я не стану вести бесед с ментом.\"").attachment("audio220392464_456239152").execute();
//				}
			}else if(cmd[0].equals("ping")){
				c.messages().send(act).peerId(peerId).message("pong").execute();
			}else if(cmd[0].equals("nick")){
				if(cmd.length>1){
					if(cmd[1].equals("get")){
						if(man.isExist(senderId)){
							c.messages().send(act).peerId(peerId).message("Ваш ник: "+man.getProfile(senderId).getNick()).execute();
						}else{
							c.messages().send(act).peerId(peerId).message("У вас отсутствует профиль. Вы можете создать выполнив команду \"/nick set (nick)\"").execute();
						}
					}else if(cmd[1].equals("set")) {
						if(cmd.length==3){
							if(man.isExist(senderId)){
								man.getProfile(senderId).setNick(cmd[2]);
								c.messages().send(act).peerId(peerId).message("Ник установлен").execute();
							}else{
								man.addProfile(senderId, new VKUserProfile(cmd[2]));
								c.messages().send(act).peerId(peerId).message("Профиль создан\nНик установлен").execute();
							}
						}else if(cmd.length==4){
							if(getVKUserOPLevel(senderId)==127){
							if(man.isExist(Integer.parseInt(cmd[3]))){
//								if(man.isExist(Integer.parseInt(cmd[3])){
								man.getProfile(Integer.parseInt(cmd[3])).setNick(cmd[2]);
								c.messages().send(act).peerId(peerId).message("Ник установлен").execute();
							}else{
								man.addProfile(Integer.parseInt(cmd[3]), new VKUserProfile(cmd[2]));
								c.messages().send(act).peerId(peerId).message("Профиль создан\nНик установлен").execute();
							}
							}else{
								c.messages().send(act).peerId(peerId).message("К сожалению(или к счастью) у вас недостаточно прав.\nНеобходимый уровень доступа: 127\nВаш уровень доступа: "+getVKUserOPLevel(senderId)).execute();
							}
						}
					}else if(cmd[1].equals("getOPLevel")){
						if(man.isExist(senderId)){
							c.messages().send(act).peerId(peerId).message("Ваш уровень доступа: "+man.getProfile(senderId).getOPLevel()+"\nУровень рядового пользователя: -128").execute();
						}else{
							c.messages().send(act).peerId(peerId).message("У вас отсутствует профиль. Вы можете создать выполнив команду \"/nick set (nick)\"").execute();
						}
					}else{
						c.messages().send(act).peerId(peerId).message("Ти дурак или как?").execute();
					}
				}else{
					c.messages().send(act).peerId(peerId).message("Usage: /nick get [id]\n /nick set (nick) [id]\n /nick getOPLevel").execute();
				}
			}else if(aliases.containsKey(cmd[0])){
				try {
					tmp=aliases.get(cmd[0]);
					if(getVKUserOPLevel(senderId)>=tmp.OPLevel()){
						tmp.exec(cmd.toString().replaceFirst(cmd[0]+" ", ""), peerId, message, c, act);
					}else{
						c.messages().send(act).peerId(peerId).message("\"403 говорит, о том, что я не стану вести бесед с ментом.\"").attachment("audio220392464_456239152").execute();
					}
				} catch (Exception e) {
					c.messages().send(act).peerId(peerId).message("Команда \""+cmd[0]+"\" решила плюнуть исключение: "+e.getMessage()).execute();
				}
//					aliases.put(cmd[0], tmp);
//			}else if(cmd[0].contains(":")&&cmd[0].startsWith(cmd[0].split(":")[0]+":")&&enabled.containsKey(cmd[0].split(":")[0])){
//				if(enabled.get(cmd[0].split(":")[0]).commands.containsKey(cmd[0].split(":")[1])){
//					try {
//						enabled.get(cmd[0].split(":")[0]).commands.get(cmd[0].split(":")[1]).exec(cmd.toString().replaceFirst(cmd[0]+" ", ""));
//					} catch (Exception e) {
//						c.messages().send(act).peerId(peerId).message("Команда \""+cmd[0]+"\" решила плюнуть исключение: "+e.getMessage()).execute();
//					}
//				}else{
//					c.messages().send(act).peerId(peerId).message("Хмм...\n В модуле \""+cmd[0].split(":")[0]+"\" нет комманды \""+cmd[0].split(":")[1]+"\"");
//				}
			}else if(cmd[0].equals("help")){
				if(tmps!="") {
					c.messages().send(act).peerId(peerId).message(tmps).execute();
				}else{
					c.messages().send(act).peerId(peerId).message("Я слепой").execute();
				}
			}else if(cmd[0].equals("")){}else{
				c.messages().send(act).peerId(peerId).message("\"Хозяева не нашли вещей в своей квартире? А мы-то ту причём? 404!\"").attachment("audio220392464_456239152").execute();
			}
		}catch(com.vk.api.sdk.exceptions.ApiCaptchaException e) {
			System.out.println("Captcha!");
			try {
				net.marketer.Utility.saveImgByUrl(new URL(e.getImage()), "/tmp/cap/Cap"+e.getSid()+".jpg");
				cid=net.marketer.RuCaptcha.postCaptcha(new File("/tmp/cap/Cap"+e.getSid()+".jpg"));
				for(@SuppressWarnings("unused")
				byte c=0; 0<10; c++){
					tmpstr=RuCaptcha.getDecryption(cid.substring(3));
					if(tmpstr.equals(RuCaptcha.Responses.CAPCHA_NOT_READY.toString())){
					sleep(1000);
					}else if(tmpstr.startsWith("OK")){
						this.c.account().setOnline(act).captchaKey(tmpstr.substring(3)).captchaSid(e.getSid()).execute();
						break;
					}
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
String tmps="";
	protected byte getVKUserOPLevel(Object id){
		if(man.isExist(id)){
			return man.getProfile(id).getOPLevel();
		}
		return -128;
	}
	protected void setVKUserNick(Object id, String nick){
		if(man.isExist(id)){
			man.getProfile(id).setNick(nick);
		}else{
			man.addProfile(id, new VKUserProfile(nick));
		}
	}
	public void buildHelp(){
		tmps="help: Эта дичь\nnick: Работа с никами\nping: Ясно из названия\n";
		for(String s:enabled.keySet()){
		for(String d1:enabled.get(s).commands.keySet()){
//			for(String d:enabled.get(s).commands.get(d1).description()){
				tmps+=s+":"+d1+": "+enabled.get(s).commands.get(d1).description()+"\n";
			}
		}
	}
}
