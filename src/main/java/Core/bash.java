package Core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;

import API.module;
import API.moduleInfo;
@moduleInfo(author="uis",dependencies="",internalName="bash",name="Burn Again Shell",version=Core.version.CoreVersion,build=Core.version.CoreBuild)
public class bash extends Thread{
	modprobe p;
	BufferedReader br=null;
	public bash(BufferedReader reader) {
		br=reader;
	}
	public bash() {
	}
	Logger log=LoggerFactory.getLogger("main");
	String cmd;
	UserActor u;
	GroupActor g;
	public void setUserActor(UserActor u){
		isUser=true;
		this.u=u;
	}
	public void setGroupActor(GroupActor g){
		isUser=false;
		this.g=g;
	}
	boolean isUser=false;
	module m;
	HashMap<String,module> hm=new HashMap<String,module>();
	HashMap<String,module> loaded=new HashMap<String,module>();
	String user="root";
	String host="VKBot";
	@Override
	public void run(){
		try{
			//log.
//			String cmd;
			while(true){
				System.out.print("root@VKBot#");
				cmd=br.readLine();
				if(cmd.equals("")){}else if(command("test")){
					System.out.println("Successful");
				}else if(command("oauth")){
					Core.commands.oauth.exec(cmd.split(" "));
				}else if(cmd.equals("exit")){
					break;
				}else if(command("init")){
					Core.commands.init.exec(cmd.split(" "), this);
				}else if(command("whatisit")){
					System.out.println(bash.class.getAnnotation(moduleInfo.class).name());
				}else if(command("modprobe")){
					m=new modprobe().load(cmd.split(" ")[1]);
					if(!hm.containsKey(m.getClass().getAnnotation(moduleInfo.class).internalName())){
//						System.out.println(loaded.containsKey(m.getClass().getAnnotation(moduleInfo.class).internalName()));
						log.debug("Loading module \""+m.getClass().getAnnotation(moduleInfo.class).name()+"\"");
						m.onLoad();
						log.info("Module \""+m.getClass().getAnnotation(moduleInfo.class).name()+"\" loaded");
						m.enablePlugin();
						hm.put(m.getClass().getAnnotation(moduleInfo.class).internalName(), m);
					}else{
						log.error("Mudule aleready loaded");
					}
				}else if(cmd.startsWith("exec ")){
					String sum="";
					BufferedReader r=new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(cmd.replaceFirst("exec ", "")).getInputStream()));
				    while (true)
				    {
				        String str=r.readLine();
				        if(str==null){
				        	break;
				        }
				        sum+=str+"\n";
				    }
				    System.out.println(sum);
				}else{
					System.out.println(cmd.split(" ")[0]+": command not found");
				}
			}
		}catch(Exception e){
//			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	protected boolean command(String what){
		String[] s=cmd.split(" ");
		if(s[0].equals(what)&&cmd.contains(what)||cmd.equals(what)){
			return true;
		}else{
			return false;
		}
	}
}
