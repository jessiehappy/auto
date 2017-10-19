package auto.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author yanghao
 */
public class XmlUtils {

    public static Element getRootElement(HttpServletRequest request)
            throws Exception {
        if (request == null) {
            return null;
        }
        return getRootElement(request.getInputStream());
    }

    public static Element getRootElement(String s) throws Exception {
        if (s == null) {
            return null;
        }
        return getRootElement(new ByteArrayInputStream(s.getBytes()));
    }

    private static Element getRootElement(InputStream in) throws Exception {
        SAXReader reader = new SAXReader();
        try {
            return reader.read(in).getRootElement();
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
    
    public static String toXml(Object... args) {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("xml");
        for (int i = 0; i < args.length - 1; i += 2) {
            if (args[i] != null && args[i + 1] != null) {
                root.addElement(args[i].toString()).addCDATA(args[i + 1].toString());
            }
        }
        return doc.asXML();
    }

    public static String toXml(Map<String, String> params) {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("xml");
        for (Entry<String, String> entry : params.entrySet()) {
            root.addElement(entry.getKey()).addCDATA(entry.getValue());
        }
        return doc.asXML();
    }
    
    public static Map<String, String> parse(String content) throws Exception {
        Element root = XmlUtils.getRootElement(content);
        return parse(root);
    }
    
    public static Map<String, String> parse(Element root) {
        Map<String, String> params = new TreeMap<String, String>();
        for (Object o : root.elements()) {
            Element e = (Element) o;
            String key = e.getName();
            String value = e.getText();
            params.put(key, value);
        }
        return params;
    }
}
