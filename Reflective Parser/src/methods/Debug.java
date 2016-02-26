package methods;

public class Debug {

	static boolean  isDebug = false;
	public static boolean isDebug() {
		return isDebug;
	}
	public static boolean isVerbose() {
		return isVerbose;
	}
	static boolean  isVerbose = false;
	
	public void setIsDebug(boolean aDebug){ isDebug = aDebug;}
	public void setIsVerbose(boolean aVerbose){ isVerbose = aVerbose;}
	
	

}
