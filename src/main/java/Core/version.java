package Core;
public class version {
	public static final String CoreVersion="0.2.3";
	public static final byte CoreBuild=(byte) 0x00;
	public static String getCoreVersion(){
		return CoreVersion;
	}
	public static byte getCoreBuild(){
		return CoreBuild;
	}
}
