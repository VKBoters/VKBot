package Core;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import API.module;
import API.moduleInfo;
import Core.messaging.msh;
@moduleInfo(author="uis",dependencies="",internalName="bash",name="Burn Again Shell",version=Core.version.CoreVersion,build=Core.version.CoreBuild)
public class bash extends Thread{
	BufferedReader br=null;
	public bash(BufferedReader reader) {
		br=reader;
	}
	public bash() {
	}
	Logger log=LoggerFactory.getLogger("main");
	String cmd;
	boolean isUser=false;
	Class<?> m;
//	HashMap<String,Command> aliases=new HashMap<String, Command>();
//	HashMap<String,module> enabled=new HashMap<String,module>();
//	HashMap<String,module> loaded=new HashMap<String,module>();
	String user="root";
	String host="VKBot";
	module tmp;
	msh MSH=new msh();
	@SuppressWarnings("static-access")
	@Override
	public void run(){
			//log.
//			String cmd;
		while(true){
			try{
				System.out.print(user+"@"+host+"#");
				cmd=br.readLine();
				if(cmd.equals("")){}else if(command("test")){
					System.out.println("Successful");
				}else if(command("oauth")){
					Core.commands.oauth.exec(cmd.split(" "));
				}else if(cmd.equals("exit")){
					break;
				}else if(command("init")){
					Core.commands.init.exec(cmd.split(" "), this);
				}else if(command("mshInit")){
					if(cmd.split(" ").length==3){
						MSH.mshInit(Integer.parseInt(cmd.split(" ")[1]), cmd.split(" ")[2]);
					}else{
						System.out.println("Usage: mshInit [id] [token]");
					}
				}else if(command("mshStart")){
					MSH.start();
				}else if(command("mshStop")){
					MSH.stop();
				}else if(command("whatisit")){
					System.out.println(bash.class.getAnnotation(moduleInfo.class).name());
				}else if(cmd.startsWith("modprobe")){
					if(cmd.split(" ").length==2){
						m=new modprobe().load(cmd.split(" ")[1]);
						tmp=(module) m.newInstance();
						if(!MSH.enabled.containsKey(tmp.getClass().getAnnotation(moduleInfo.class).internalName())){
//							module tmp=(module) m.newInstance();
							log.debug("Loading module \""+tmp.getClass().getAnnotation(moduleInfo.class).name()+"\"");
							tmp.onLoad();
							log.info("Module \""+tmp.getClass().getAnnotation(moduleInfo.class).name()+"\" loaded");
							tmp.enablePlugin();
							MSH.enabled.put(tmp.getClass().getAnnotation(moduleInfo.class).internalName(), tmp);
						}else{
							log.error("Mudule aleready loaded");
						}
					}else{
						System.out.println("Usage: modprobe [class]");
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
				}else if(cmd.contains(":")&&cmd.startsWith(cmd.split(":")[0]+":")&&MSH.enabled.containsKey(cmd.split(":")[0])){
					if(MSH.enabled.get(cmd.split(":")[0]).commands.containsKey(cmd.split(":")[1])){
						System.out.println(MSH.enabled.get(cmd.split(":")[0]).commands.get(cmd.split(":")[1]).exec(cmd.replaceFirst(cmd.split(" ")[0]+" ", "")));
					}else{
						System.out.println("Module \""+cmd.split(":")[0]+"\" doesn't contains command \""+cmd.split(":")[1]+"\"");
					}
				}else if(cmd.startsWith("addAlias")){
					if(cmd.split(" ").length==3){
						MSH.aliases.put(cmd.split(" ")[1], MSH.enabled.get(cmd.split(" ")[2].split(":")[0]).commands.get(cmd.split(" ")[2].split(":")[1]));
					}else{
						System.out.println("Usage: addAlias [alias] [command]");
					}
				}else if(cmd.startsWith("delAlias")){
					if(cmd.split(" ").length==2){
						MSH.aliases.remove(cmd.split(" ")[1]);
					}else{
						System.out.println("Usage: delAlias [alias]");
					}
				}else if(MSH.aliases.containsKey(cmd.split(" ")[0])){
					System.out.println(MSH.aliases.get(cmd.split(" ")[0]).exec(cmd.replaceFirst(cmd.split(" ")[0]+" ", "")));
				}else{
					System.out.println(cmd.split(" ")[0]+": command not found");
				}
			}catch(Exception e){
//				System.out.println(e.toString());
				e.printStackTrace();
			}
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
