package auto.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang.StringUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@CommonsLog
public class JsonUtils {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * @return data from encrypted JSON
     */
    public static <T> T decrypt(String encryptedJson, Class<T> dataClass)
            throws Exception {
        encryptedJson = StringUtils.trimToNull(encryptedJson);
        if (encryptedJson == null) {
            return null;
        }
        String json = EncryptUtils.decrypt(encryptedJson);
        return GSON.fromJson(json, dataClass);
    }

    /**
     * @return encrypted JSON of given data
     */
    public static <T> String encrypt(T data) throws Exception {
        if (data == null) {
            return null;
        }
        return EncryptUtils.encrypt(GSON.toJson(data));
    }
    
    /**
     * @return JSON of given data
     */
    public static String toJson(Object data) {
        return GSON.toJson(data);
    }
    
    /**
     * @return data from JSON
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }
    
    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }
    
    /**
     * @return JSON of key-value data
     */
    public static String toJson(Object... values) {
        Map<Object, Object> content = new HashMap<Object, Object>();
        for (int i = 0; i < values.length; i += 2) {
            if (values[i + 1] != null) {
                content.put(values[i], values[i + 1]);
            }
        }
        return toJson(content);
    }
    
    public static String toJson(Object o, ExclusionStrategy strategy) {
        try {
            GsonBuilder builder = new GsonBuilder();
            if (strategy != null) {
                builder.addSerializationExclusionStrategy(strategy);
            }
            return builder.create().toJson(o);
        } catch (Exception e) {
            log.error("To JSON failed: object=" + o);
            return "";
        }
    }
}
