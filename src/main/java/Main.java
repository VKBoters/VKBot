import java.io.BufferedReader;
import java.io.InputStreamReader;

import Core.bash;
public class Main{
	public Main() throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		bash b=new bash(br);
		b.start();
		b.join();
	}
	public static void main(String[] args) {
		try {
			new Main();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
