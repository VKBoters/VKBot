package Core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	boolean isUser=false;
	Class<?> m;
	HashMap<String,module> hm=new HashMap<String,module>();
	HashMap<String,module> loaded=new HashMap<String,module>();
	String user="root";
	String host="VKBot";
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
				}else if(command("whatisit")){
					System.out.println(bash.class.getAnnotation(moduleInfo.class).name());
				}else if(command("modprobe")){
					m=new modprobe().load(cmd.split(" ")[1]);
					if(!hm.containsKey(m.getClass().getAnnotation(moduleInfo.class).internalName())){
						module tmp=(module) m.newInstance();
						log.debug("Loading module \""+m.getClass().getAnnotation(moduleInfo.class).name()+"\"");
						tmp.onLoad();
						log.info("Module \""+m.getClass().getAnnotation(moduleInfo.class).name()+"\" loaded");
						tmp.enablePlugin();
						hm.put(m.getClass().getAnnotation(moduleInfo.class).internalName(), tmp);
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
				}else if(cmd.contains(":")&&cmd.startsWith(cmd.split(":")[0]+":")&&hm.containsKey(cmd.split(":")[0])){
					if(hm.get(cmd.split(":")[0]).commands.containsKey(cmd.split(":")[1])){
						System.out.println(hm.get(cmd.split(":")[0]).commands.get(cmd.split(":")[1]).exec(cmd.replaceFirst(cmd.split(" ")[0]+" ", "")));
					}else{
						System.out.println("Module \""+cmd.split(":")[0]+"\" doesn't contains command \""+cmd.split(":")[1]+"\"");
					}
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
