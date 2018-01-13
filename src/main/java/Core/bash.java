package Core;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import API.module;
import API.moduleInfo;
import Core.messaging.msh;
@moduleInfo(author="uis",dependencies="",internalName="bash",name="Burn Again Shell",version=Core.version.CoreVersion,build=Core.version.CoreBuild)
public class bash extends Thread{
	BufferedReader br=null;
//	Runtime
	public bash(BufferedReader reader) {
		br=reader;
	}
	public bash() {
	}
	String cmd;
	boolean isUser=false;
//	Class<?> m;
//	HashMap<String,Command> aliases=new HashMap<String, Command>();
//	HashMap<String,module> enabled=new HashMap<String,module>();
//	HashMap<String,module> loaded=new HashMap<String,module>();
	String user="root";
	String host="VKBot";
	module tmp;
	msh MSH=new msh();
	@SuppressWarnings({ "deprecation" })
	@Override
	public void run(){
		while(true){
			try{
				System.out.print(user+"@"+host+"#");
				cmd=br.readLine();
				if(cmd.equals("")){}else if(cmd.equals("exit")){
					break;
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
					String tmpStr=cmd.replaceFirst("modprobe ","");
					if(cmd.replaceFirst("modprobe ","").startsWith("-")){
						if(tmpStr.startsWith("-r")){
							if(msh.loaded.containsKey(tmpStr.split(" ")[1])){
								msh.loaded.remove(tmpStr.split(" ")[1]);
							}else if(msh.enabled.containsKey(tmpStr.split(" ")[1])){
								tmp=msh.enabled.remove(tmpStr.split(" ")[1]);
								tmp.disablePlugin();
							}else{
								System.out.println("Module \""+tmpStr.split(" ")[1]+"\" doesn't loaded");
							}
						}else if(tmpStr.startsWith("-d")){
							if(msh.enabled.containsKey(tmpStr.split(" ")[1])){
								tmp=msh.enabled.remove(tmpStr.split(" ")[1]);
								tmp.disablePlugin();
								msh.loaded.put(tmpStr.split(" ")[1], tmp);
							}else{
								System.out.println("Module \""+tmpStr.split(" ")[1]+"\" doesn't enabled");
							}
						}else if(tmpStr.startsWith("-e")){
							if(msh.loaded.containsKey(tmpStr.split(" ")[1])){
								tmp=msh.loaded.remove(tmpStr.split(" ")[1]);
								tmp.enablePlugin();
								msh.enabled.put(tmpStr.split(" ")[1], tmp);
							}else{
								System.out.println("Module \""+tmpStr.split(" ")[1]+"\" enabled or not loaded");
							}
						}
					}else if(cmd.split(" ").length==2||cmd.split(" ").length==3){
						tmp=new modprobe().load(cmd.replaceFirst("modprobe ",""));
//						tmp=(module) m.newInstance();
						if(!msh.enabled.containsKey(tmp.getClass().getAnnotation(moduleInfo.class).internalName())){
							tmp.onLoad();
							System.out.println("Module \""+tmp.getClass().getAnnotation(moduleInfo.class).name()+"\" loaded");
							tmp.enablePlugin();
							msh.enabled.put(tmp.getClass().getAnnotation(moduleInfo.class).internalName(), tmp);
						}else{
							System.err.println("Mudule aleready loaded");
						}
					}else{
						System.out.println("Usage: modprobe [class]\nOr modprobe [URL] [class]");
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
				}else if(cmd.contains(":")&&cmd.startsWith(cmd.split(":")[0]+":")&&msh.enabled.containsKey(cmd.split(":")[0])){
					if(msh.enabled.get(cmd.split(":")[0]).commands.containsKey(cmd.split(":")[1])){
						msh.enabled.get(cmd.split(":")[0]).commands.get(cmd.split(":")[1]).exec(cmd.replaceFirst(cmd.split(" ")[0]+" ", ""));
					}else{
						System.out.println("Module \""+cmd.split(":")[0]+"\" doesn't contains command \""+cmd.split(":")[1]+"\"");
					}
				}else if(cmd.startsWith("addAlias")){
					if(cmd.split(" ").length==3){
						MSH.aliases.put(cmd.split(" ")[1], msh.enabled.get(cmd.split(" ")[2].split(":")[0]).commands.get(cmd.split(" ")[2].split(":")[1]));
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
					MSH.aliases.get(cmd.split(" ")[0]).exec(cmd.replaceFirst(cmd.split(" ")[0]+" ", ""));
//				}else if(cmd.startsWith("write")){
//					MSH.w.close();
//					MSH.w=new PrintWriter(new File("access.log"));
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
		if(s[0].equals(what)){
			return true;
		}else{
			return false;
		}
	}
}
