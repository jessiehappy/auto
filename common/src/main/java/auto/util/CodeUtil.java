package auto.util;

import java.util.UUID;

public class CodeUtil {

	public static String getCode() {
		return UUID.randomUUID().toString();
	}
}
