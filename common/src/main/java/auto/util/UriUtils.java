package auto.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import auto.Properties;

@CommonsLog
public class UriUtils {
    
    public static String urlEncode(String url) {
        try {
            return URLEncoder.encode(url, "utf-8");
        } catch(Exception e) {}
        return url;
    }
    
    public static String urlDecode(String url) {
        try {
            return URLDecoder.decode(url, "utf-8");
        } catch(Exception e) {}
        return url;
    }
    
    public static String getParamsString(String... args) {
        Map<String, String> params = new HashMap<String, String>();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i+= 2) {
                if (StringUtils.isNotEmpty(args[i]) && StringUtils.isNotEmpty(args[i+1])) {
                    params.put(args[i], args[i+1]);
                }
            }
        }
        String str = formatParams(params);
        if (StringUtils.isNotEmpty(str)) return "?" + str;
        return "";
    }
    
    public static List<NameValuePair> getNameValuePairs(Map<String, String> paramsMap) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (paramsMap != null) {
            for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                params.add(pair);
            }
        }
        return params;
    }
    
    public static String formatParams(Map<String, String> params) {
        List<NameValuePair> pairs = getNameValuePairs(params);
        return URLEncodedUtils.format(pairs, "utf-8");
    }
    
    public static Map<String, String> parseParams(String content) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(content, Charset.forName("utf-8"));
        Map<String, String> params = new TreeMap<String, String>();
        if (pairs != null) {
            for (NameValuePair pair : pairs) {
                params.put(pair.getName(), pair.getValue());
            }
        }
        return params;
    }
    
    public static String addParams(String rawUri, Map<String, String> rawParams) throws URISyntaxException {
        URI uri = new URI(rawUri);
        Map<String, String> params = parseParams(uri.getRawQuery());
        if (rawParams != null) {
            params.putAll(rawParams);
        }
        URI newUri = new URI(uri.getScheme(), uri.getHost(), uri.getRawPath(), formatParams(params), uri.getRawFragment());
        return newUri.toString();
    }
    
    private static final Map<String, String> WEB_SCHEMA_MAPPING = new HashMap<String, String>();
    
    private static final Map<String, String> WEB_SCHEMA_PARAMS_MAPPING = new HashMap<String, String>();
    
    static {
        WEB_SCHEMA_MAPPING.put("accountCoupon", "/account/manage");
        WEB_SCHEMA_MAPPING.put("accountBalance", "/account/manage");
        WEB_SCHEMA_MAPPING.put("userCenter", "/account/manage");
        WEB_SCHEMA_MAPPING.put("houseTenantManage#expense", "/contract/tenant/manage");
        WEB_SCHEMA_MAPPING.put("houseTenantManage#info", "/contract/tenant/manage");
        WEB_SCHEMA_MAPPING.put("contractTenantStatus", "/contract/tenant/contract");
        WEB_SCHEMA_MAPPING.put("houseGetInterest", "/house/search");
        WEB_SCHEMA_MAPPING.put("accountInfo", "/account/manage");
        WEB_SCHEMA_MAPPING.put("userVerify", "/user/auth/verify");
        WEB_SCHEMA_MAPPING.put("houseLandlordList", "/house/landlord");
        WEB_SCHEMA_MAPPING.put("feedback", "/user/feedback");
        WEB_SCHEMA_MAPPING.put("contractLandlordList", "/contract/landlord/list");
        WEB_SCHEMA_MAPPING.put("contractLandlordStatus", "/contract/landlord/contract");
        WEB_SCHEMA_MAPPING.put("houseLandlordManage#expense", "/contract/landlord/manage");
        WEB_SCHEMA_MAPPING.put("houseLandlordManage#info", "/contract/landlord/manage");
        WEB_SCHEMA_MAPPING.put("houseManageStatus", "/house/manage");
        WEB_SCHEMA_MAPPING.put("houseDetail", "/house/detail");
        
        WEB_SCHEMA_PARAMS_MAPPING.put("houseGetInterest", "column=targetHouse");
    }

    
    public static String convertWebUri(String appUri, Map<String, String> moreParams) {
        try {
            URI uri = new URI(appUri);
            if ("room107".equals(uri.getScheme())) {
                String schema = uri.getHost();
                if (StringUtils.isNotEmpty(uri.getRawFragment())) {
                    schema += "#" + uri.getRawFragment();
                }
                String path = WEB_SCHEMA_MAPPING.get(schema);
                if (path == null) {
                    log.error("web中出现无法识别的schema: " + appUri);
                    return null;
                }
                Map<String, String> params = parseParams(uri.getRawQuery());
                if (moreParams != null) {
                    params.putAll(moreParams);
                }
                if (WEB_SCHEMA_PARAMS_MAPPING.containsKey(schema)) {
                    params.putAll(parseParams(WEB_SCHEMA_PARAMS_MAPPING.get(schema)));
                }
                uri = new URI("http", getHost(Properties.HOST), path, formatParams(params), uri.getRawFragment());
            }
            return uri.toString();
        } catch(Exception e) {
            log.error("convert web uri failed.", e);
        }
        return null;
    }
    
    public static String getHost(String uri) throws URISyntaxException {
        return new URI(uri).getHost();
    }
    
    public static void main(String[] args) throws URISyntaxException {
        String uri = "room107://message";
        Map<String, String> params = new HashMap<String, String>();
        params.put("targetUrl", "add");
        System.out.println(addParams(uri, params));
    }

}
