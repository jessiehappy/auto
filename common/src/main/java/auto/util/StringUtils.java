package auto.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DateUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@CommonsLog
public class StringUtils extends org.apache.commons.lang.StringUtils {
    
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping()
            .create();
    /**
     * @return like '1|2|3'
     */
    public static String toString(Object... o) {
        return org.apache.commons.lang.StringUtils.join(o, '|');
    }

    public static String escpaeHtml(String text) {
        if (text == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        text = StringEscapeUtils.escapeHtml(text);
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if (c == '\r' && i < length - 1 && text.charAt(i + 1) == '\n') { // \r\n
                builder.append("<br>");
                i++;
            } else if (c == '\r' || c == '\n') {
                builder.append("<br>");
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
    
    /**
     * Abstract and then escape to JS.
     */
    public static String escapeSummary(String text, int maxLength) {
        if (text == null) {
            return null;
        }
        text = abbreviate(text, maxLength);
        return StringEscapeUtils.escapeJavaScript(text);
    }

    public static String escapeJS(String text) {
        return StringEscapeUtils.escapeJavaScript(text);
    }
    
    public static String truncate(String text, int length) {
        if (text == null || text.length() <= length) return text;
        return text.substring(0, length - 1) + '…';
    }
    
    public static int findLongestNumberBeforeIndex(String s, int index) {
        int r = 1;
        int number = 0;
        for (int i = index - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                number += r * (ch - '0');
                r *= 10;
            } else {
                break ;
            }
        }
        return number;
    }
    
    public static int findLongestNumberAfterIndex(String s, int index) {
        int number = 0;
        for (int i = index + 1; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                number = number * 10 + ch - '0';
            } else {
                break ;
            }
        }
        return number;
    }
    
    private static final char[] CHINESE_PUNCTUATION = {'，','。','；','：','“','”',
        '！','？','（','）','、','《','》','·'};
    
    public static boolean isChinese(char ch) {
        if (ch >= '\u2E80' && ch <= '\uFE4F') return true;
        for (char chinese : CHINESE_PUNCTUATION) {
            if (chinese == ch) return true;
        }
        return false;
    }
    
    private static final char[] PUNCTUATION = {',','.',' ','?',':',';','!','-','(',')'};
    
    public static boolean isPunctuation(char ch) {
        if (ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') return true;
        for (char punctuation : PUNCTUATION) {
            if (punctuation == ch) return true;
        }
        return false;
    }
    
    public static List<String> getChineseTextList(String content) {
        List<String> list = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            if (isChinese(ch)) {
                sb.append(ch);
            } else if (isPunctuation(ch)){
                if (sb.length() > 0) sb.append(ch);
            } else {
                if (sb.length() > 0) {
                    list.add(sb.toString());
                    sb = new StringBuilder();
                }
            }
        }
        if (sb.length() > 0) {
            list.add(sb.toString());
        }
        return list;
    }

    public static boolean contains(String content, String tag) {
        if (content == null) return false;
        return content.contains(tag);
    }
    
    public static boolean isNotEmpty(String content) {
        if (content == null) return false;
        if (content.length() == 0) return false;
        return true;
    }
    
    public static boolean isEmpty(String content) {
        return !isNotEmpty(content);
    }
    
    public static String replace(String text, String from, String to) {
        if (text == null || from == null || to == null || from.length() <= 0) return text;
        StringBuilder sb = new StringBuilder();
        int n = text.length();
        int m = from.length();
        for (int i = 0; i < n;) {
            boolean match = true;
            for (int j = 0; j < m; j++) {
                if (i + j > n) {
                    match = false;
                    break;
                }
                if (from.charAt(j) != text.charAt(i + j)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                sb.append(to);
                i += m;
            } else {
                sb.append(text.charAt(i));
                i++;
            }
        }
        return sb.toString();
    }
    
    public static Long getLong(String s) {
        try {
            if (isNotEmpty(s)) {
                return Long.valueOf(s);
            }
        } catch(Exception e) {}
        return null;
    }
    
    public static Integer getInt(String s) {
        try {
            if (isNotEmpty(s)) {
                return Integer.valueOf(s);
            }
        } catch(Exception e) {}
        return null;
    }
    
    public static Double getDouble(String s) {
        try {
            if (isNotEmpty(s)) {
                return Double.valueOf(s);
            }
        } catch(Exception e) {}
        return null;
    }
    
    public static String filterUtf8Mb4(String text) {
        if (text == null || text.isEmpty()) return text;
        try {
            byte[] bytes = text.getBytes("utf8");
            int length = 0;
            int i = 0;
            while (i < bytes.length) {
                short b = bytes[i];
                if (b > 0) {
                    i++;
                    length++;
                    continue;
                }
                b += 256;
                if ((b ^ 0xC0) >> 4 == 0) {
                    i += 2;
                    length += 2;
                } else if ((b ^ 0xE0) >> 4 == 0) {
                    i += 3;
                    length += 3;
                } else {
                    i += 4;
                }
            }
            
            ByteBuffer buffer = ByteBuffer.allocate(length);
            i = 0;
            while (i < bytes.length) {
                short b = bytes[i];
                if (b > 0) {
                    buffer.put(bytes[i++]);
                    continue;
                }
                b += 256;
                if ((b ^ 0xC0) >> 4 == 0) {
                    buffer.put(bytes, i, 2);
                    i += 2;
                } else if ((b ^ 0xE0) >> 4 == 0) {
                    buffer.put(bytes, i, 3);
                    i += 3;
                } else {
                    i += 4;
                }
            }
            buffer.flip();
            return new String(buffer.array(), "utf8");
        } catch(Exception e) {}
        return text;
    }
    
    public static String truncateEmail(String content) {
        if (!StringUtils.isEmpty(content)) {
            int index = content.indexOf('@');
            if (index >= 0) {
                return content.substring(0, index);
            }
        }
        return content;
    }
    
    public static int compare(String s1, String s2) {
        int n = Math.min(s1.length(), s2.length());
        for (int i = 0; i < n; i++) {
            if (s1.charAt(i) < s2.charAt(i)) return -1;
            if (s1.charAt(i) > s2.charAt(i)) return 1;
        }
        if (s1.length() == s2.length()) {
            return 0;
        }
        return s1.length() < s2.length() ? -1 : 1;
    }
    
    public static String[] split(String str) {
        if (str == null) return new String[0];
        return str.split("\\|");
    }
    
    public static List<Long> splitToLong(String str) {
        List<Long> list = new ArrayList<Long>();
        if (isNotEmpty(str)) {
            String[] ss = split(str);
            for (String s : ss) {
                try {
                    list.add(Long.valueOf(s));
                } catch(Exception e) {
                    log.error(e, e);
                }
            }
        }
        return list;
    }
    
    public static List<Integer> splitToInt(String str) {
        List<Integer> list = new ArrayList<Integer>();
        if (isNotEmpty(str)) {
            String[] ss = split(str);
            for (String s : ss) {
                try {
                    list.add(Integer.valueOf(s));
                } catch(Exception e) {
                    log.error(e, e);
                }
            }
        }
        return list;
    }
    
    public static String join(Collection<?> list) {
        return org.apache.commons.lang.StringUtils.join(list, "|");
    }
    
    public static String merge(Collection<?> list) {
        return org.apache.commons.lang.StringUtils.join(list, ",");
    }
    
    public static boolean isBefore(Date date, int days) {
        Date now = new Date();
        Date expiringTime = DateUtils.addDays(now, days);
        return date.before(expiringTime);
    }
    
    private static final DecimalFormat df;
    
    static {
        df = (DecimalFormat)DecimalFormat.getInstance();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setGroupingSize(3);
    }
    
    public static String formatPrice(double price) {
        return df.format(price);
    }
    
    public static Map<Long, Long> getMapFromTimedIds(String ids) {
        Map<Long, Long> map = new HashMap<Long, Long>();
        if (StringUtils.isNotEmpty(ids)) {
            for (String pair : StringUtils.split(ids, ',')) {
                try {
                    String[] ss = StringUtils.split(pair, "@");
                    Long houseId = Long.parseLong(ss[0]);
                    Long time = Long.parseLong(ss[1]);
                    map.put(houseId, time);
                } catch(Exception e) {}
            }
        }
        return map;
    }
    
    public static String getTimedIdsFromMap(Map<Long, Long> pairs) {
        StringBuilder sb = new StringBuilder();
        for (Entry<Long, Long> entry : pairs.entrySet()) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(entry.getKey()).append('@').append(entry.getValue());
        }
        return sb.toString();
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(truncateEmail("1231dick@163.com"));
//        String s = "&#39640;&#22823;&#19978;&#39640;&#22823;&#19978;&#20248;&#20808;&#32771;&#34385;&#38388;&#32447;&#19978;&#31614;&#32422;&#30340;&#31199;&#23458;&#65292;&#35874;&#35874;&#65281;";
//        System.out.println();
//        s = abbreviate(s, 100);
//        System.out.println(s);
//       System.out.println(getStrByDecimal(s)); 
       System.out.println(getHTMLTOLINE("你的手腕的\n来接我\r"));
    }

    public static String getHTMLTOLINE(String text){
    	  if (text == null) {
              return null;
          }
    	  text = text.replaceAll("\\n", "<br>");
          return text;
    }
    public static String getStrByDecimal(String str){
    	String[] strs = str.split(";");
    	StringBuffer sb = new StringBuffer();
    	for(String s:strs){
    		String tempS = s.substring(2);
    		String s16 = "\\u"+Integer.toHexString(Integer.parseInt(tempS));
    		sb.append(decodeUnicode(s16));
    	}
    	return sb.toString();
    }
    public static String decodeUnicode(final String dataStr) {  
        int start = 0;  
        int end = 0;  
        final StringBuffer buffer = new StringBuffer();  
        while (start > -1) {  
            end = dataStr.indexOf("\\u", start + 2);  
            String charStr = "";  
            if (end == -1) {  
                charStr = dataStr.substring(start + 2, dataStr.length());  
            } else {  
                charStr = dataStr.substring(start + 2, end);  
            }  
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。  
            buffer.append(new Character(letter).toString());  
            start = end;  
        }  
        return buffer.toString();  
    }  
}
