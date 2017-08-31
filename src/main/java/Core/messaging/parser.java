package Core.messaging.callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Core.messaging.msh;

public class parser {
	public parser() {
		try {
			System.out.print(parse(0,new BufferedReader(new InputStreamReader(System.in)).readLine(),0));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		new parser();
	}
	public String parse(int id, String what, int ID) throws IOException, InterruptedException{
		String sum="";
//		String ins;
		if(what.startsWith(">")){
			return new msh().cmd(what.replace(">", ""));
		} else if(what.startsWith("#")){
			Process i=Runtime.getRuntime().exec(what.replaceFirst("#", ""));
			BufferedReader r=new BufferedReader(new InputStreamReader(i.getInputStream()));
		    while (true)
		    {
		        String str=r.readLine();
		        if(str==null){
		        	break;
		        }
		        sum+=str+"\n";
		    }
		    return sum;
		}
		return sum;
	}
}
