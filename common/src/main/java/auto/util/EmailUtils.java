package auto.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang.Validate;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

@CommonsLog
public class EmailUtils {
    
    public static final String[] OPERATION = {
    };
    
    public static final String[] MONITOR = {
    };
    
    public static final String[] SYSTEM_EMAILS = {
    };
    
    public static enum EmailAccount {

        /**
         * Internal.
         */
        ADMIN,
        /**
         * Verify email.
         */
        NO_REPLY,

    }

    private static VelocityEngine velocityEngine;

    private static final Set<String> EDU_POSTFIXES = new HashSet<String>(
            Arrays.asList(".edu", ".edu.cn", ".edu.au", ".edu.hk", ".edu.sg",
                    ".ac.cn", ".ac.jp", ".ac.uk"));
    
    static {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER,
                "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class",
                ClasspathResourceLoader.class.getName());
        Properties properties = new Properties();
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        velocityEngine.init(properties);
    }

    public static void sendSystemMail(String title, String content) {
        List<String> toes = Arrays.asList(SYSTEM_EMAILS);
        title = title + " from " + auto.Properties.hostName;
        sendMail(toes, title, content, SimpleEmail.class,
                EmailAccount.ADMIN);
    }
    
    public static boolean sendMail(List<String> toes, String title, String content) {
        title = title + " from " + auto.Properties.hostName;
        return sendMail(toes, title, content, SimpleEmail.class,
                EmailAccount.ADMIN);
    }

    @SuppressWarnings("unused")
	public static boolean sendMail(List<String> toes, String title, String content,
            Class<? extends Email> emailClass, EmailAccount emailAccount) {
//        if (room107.Properties.isEmailOn) {
        	if(true){
            Validate.notNull(emailClass);
            try {
                Email email = emailClass.newInstance();
                setMailAccount(email, emailAccount);
                for (String to : toes) {
                    email.addTo(to, to);
                }
                email.setSubject(title);
                if (emailClass == HtmlEmail.class) {
                    HtmlEmail htmlEmail = (HtmlEmail) email;
                    htmlEmail.setHtmlMsg(content);
                    htmlEmail.setTextMsg(content);
                } else {
                    email.setMsg(content);
                }
                log.info("Send mail: to=" + toes + ", title=" + title
                        + ", content=" + content + ", emailClass=" + emailClass
                        + ", emailAccount=" + emailAccount);
                email.send();
            } catch (Exception e) {
                log.error("Send mail failed: account=" + emailAccount + ", to="
                        + toes + ", title=" + title + ", content=" + content, e);
                return false;
            }
        } else {
            log.info("Send mail: to=" + toes + ", title=" + title
                    + ", content=" + content + ", emailClass=" + emailClass
                    + ", emailAccount=" + emailAccount);
        }
        return true;
    }

    public static boolean sendMail(String to, String title, String content,
            Class<? extends Email> emailClass, EmailAccount emailAccount) {
        List<String> toes = new ArrayList<String>();
        toes.add(to);
        return sendMail(toes, title, content, emailClass, emailAccount);
    }

    public static boolean sendMailByTemplate(String to, String title,
            String templateName, VelocityContext context,
            Class<? extends Email> emailClass, EmailAccount emailAccount) {
        Template template = velocityEngine.getTemplate("template/"
                + templateName);
        Validate.notNull(template);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return sendMail(to, title, writer.toString(), emailClass, emailAccount);
    }

    public static boolean isEduEmail(String email) {
        email = WebUtils.getDomainFromEmail(email);
        if (email == null) {
            return false;
        }
        email = email.toLowerCase();
        int i = email.lastIndexOf('.');
        if (i <= 0 || i == email.length() - 1) {
            return false;
        }
        // level 1
        String postfix = email.substring(i);
        if (EDU_POSTFIXES.contains(postfix)) {
            return true;
        }
        // level 2
        i = email.lastIndexOf('.', i - 1);
        if (i < 0) {
            return false;
        }
        return EDU_POSTFIXES.contains(email.substring(i));
    }
    
    public static boolean sendBatchHtml(String title, String content, List<String> toes) {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("from", "noreplay@107room.com"));
            params.add(new BasicNameValuePair("to", "weiwei@107room.com"));
            for (String to : toes) {
                params.add(new BasicNameValuePair("Bcc", to));
            }
            params.add(new BasicNameValuePair("subject", title));
            params.add(new BasicNameValuePair("html", content));
            HttpUtils.post("https://api.mailgun.net/v3/sandbox3481.mailgun.org/messages", params,
                    HttpUtils.DEFAULT_TIMEOUT, "api:key-62pjf5gu7ju47p1wxeimum4rqxt-mak6");
            return true;
        } catch(Exception e) {
            log.error("send batch html failed, title = " + title + ", content = " + content + ", toes = " + toes, e);
            return false;
        }
    }

    private static Email setMailAccount(Email email, EmailAccount emailAccount)
            throws Exception {
        email.setHostName("smtp.exmail.qq.com");
        email.setCharset("UTF-8");
        if (emailAccount == EmailAccount.ADMIN) {
            email.setAuthentication("admin@107room.com", "8293420sppN");
            email.setFrom("admin@107room.com", "107room-admin");
        } else{
            email.setAuthentication("no-reply@107room.com", "107wwdsb");
            email.setFrom("no-reply@107room.com", "107room-no-reply");
        }
        return email;
    }

    public static void main(String[] args) throws Exception {
        File hf = new File("C:\\Users\\DickYang\\Desktop\\CMBC-EDM.html");
        InputStream hin = new FileInputStream(hf);
        byte[] hc = new byte[102400];
        int len = hin.read(hc);
        hin.close();
        String content = new String(hc, 0, len, "utf-8");
        auto.Properties.isEmailOn = true;
        List<String> toes = new ArrayList<String>();
        toes.add("gengliang@107room.com");
        toes.add("yanghao@107room.com");
        toes.add("jinyu@107room.com");
        toes.add("zhaohaixi@107room.com");
        sendMail(toes, "测试edm", content, HtmlEmail.class, EmailAccount.ADMIN);
        
        System.out.println(content);
    }

}
