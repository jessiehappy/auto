package auto.datamodel;

public enum Platform {
	
	ios, android, pc ;
    
    public static final String[] NAMES = {"苹果", "安卓", "WEB"};
    
    public static Platform parse(String s) {
        for (Platform platform : Platform.values()) {
            if (platform.toString().equals(s)) return platform;
        }
        return Platform.pc;
    }
    
    public static String getName(Platform platform) {
        return NAMES[platform.ordinal()];
    }
}
