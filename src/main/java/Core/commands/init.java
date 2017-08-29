package Core.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;

import Core.bash;
import Core.messaging.msh;

public class init/* implements bash.Callback*/{
	public static void exec(String[] args,bash bash) throws Exception {
		UserActor u;
		GroupActor g;
		if(args.length==2){
			if(args[1].equals("user")){
				g=null;
				u=new UserActor(getId(),get());
				bash.setUserActor(u);
			}else if(args[1].equals("group")){
				u=null;
				g=new GroupActor(getId(),get());
				bash.setGroupActor(g);
			}else{
				System.out.println("init: fatal error: "+args[1]+" does not exist!");
			}
		}else{
			System.out.println("init: fatal error: no input actor");
		}
	}
	private static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	public static void exec(String[] args,msh msh) {
		
	}
	private static int getId() throws IOException {
		System.out.print("Введите ID:");
		return br.read();
	}
	private static String get() throws IOException{
		System.out.print("Введите токен:");
		return br.readLine();
	}
//	@Override
//	public void Callback() {
//		bash b=new bash();
//		b.addCallback(this);
//	}
}