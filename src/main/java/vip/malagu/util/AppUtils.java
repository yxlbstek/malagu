package vip.malagu.util;

public final class AppUtils {

	private static final String[] agent = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" }; 
	
	public static boolean checkAgentIsMobile(String ua) {
		boolean result = false;
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : agent) {
					if (ua.contains(item)) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}

}
