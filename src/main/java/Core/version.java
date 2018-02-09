package Core;
public class version {
	public static final String CoreVersion="0.2.4";
	public static final byte CoreBuild=(byte) 0x01;
	public static String getCoreVersion(){
		return CoreVersion;
	}
	public static byte getCoreBuild(){
		return CoreBuild;
	}
}
