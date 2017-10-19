package auto.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import com.qiniu.api.net.Http;

import auto.Properties;
import auto.datamodel.controller.constants.JsonStatus;

/**
 * image upload util 
 * one time only upload one image
 * app or web server used this util .
 * upload image to image server(can be change)
 * @author wanglongtao
 *
 *upload token url = "/upload/token";
 */
public class ImageUploadUtils {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private final static String HEADER_AGENT="User-Agent";
	private final static String IMAGE_HOST =  Properties.IMAGE_HOST + "/img/";
	private final static String UP_HOST =  Properties.IMAGE_HOST + "/upload/image/";
	private final static String UPLOAD_TOKEN_HOST = Properties.IMAGE_HOST +  "/upload/token";
	public static final int NO_CRC32 = 0;
	
	/**
	 * 图片类型
	 * 
	 * @author wanglongtao
	 *
	 */
	public enum ImageType {
		verify, favicon, goods, brand, classify,
	}

	public static String getImageToken() throws Exception {
		String result = HttpUtils.post(UPLOAD_TOKEN_HOST, null);
		RESULT rs = JsonUtils.fromJson(result, RESULT.class);
		if(rs.errCode != JsonStatus.SUCCESS_CODE) {
			return null;
		}
		return rs.data;
	}
	public static String getImageKey( ImageType type, String ext) {
		return type + "/" + DATE_FORMAT.format(new Date()) + (int) (Math.random() * 100) + "." + ext;
	}
	/**
	 * @param accessToken
	 * @param classiFyId
	 * @param contents
	 * @param ext
	 * @return
	 * @throws Exception
	 */
	public static String uploadClassify(String accessToken, byte[] contents, String ext) throws Exception {
		String name = getImageKey(ImageType.classify, ext);
		return upload(accessToken, name, contents);
	}
	
	/**
	 * @param accessToken
	 * @param brandId
	 * @param contents
	 * @param ext
	 * @return
	 * @throws Exception
	 */
	public static String uploadBrand(String accessToken, byte[] contents, String ext) throws Exception {
		String name = getImageKey(ImageType.brand, ext);
		return upload(accessToken, name, contents);
	}
	/**
	 * 
	 * @param accessToken
	 * @param sku
	 * @param contents
	 * @param ext
	 * @return
	 * @throws Exception
	 */
	public static String uploadGoods(String accessToken, byte[] contents, String ext) throws Exception {
		String name = getImageKey(ImageType.goods, ext);
		return upload(accessToken, name, contents);
	}

	/**
	 * 
	 * @param username
	 * @param contents
	 * @param ext
	 * @return
	 * @throws Exception
	 */
	public static String uploadFavicon(String accessToken, byte[] contents, String ext) throws Exception {
		String name = getImageKey(ImageType.favicon, ext);
		return upload(accessToken, name, contents);
	}
	
	/**
	 * @param code
	 * @param contents
	 * @param ext
	 * @return
	 * @throws Exception
	 */
	public static String uploadVerify(String accessToken, byte[] contents, String ext) throws Exception {
		String name = getImageKey(ImageType.verify, ext);
		return upload(accessToken, name, contents);
	}

	public static String upload(String accessToken, String name, byte[] contents) throws Exception {
		InputStream reader = new ByteArrayInputStream(contents);
		PutExtra extra = new PutExtra();
		File file = null;
		try {
			file = copyToTmpFile(reader);
			MultipartEntity requestEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName(JsonStatus.CHARSET));
			AbstractContentBody fileBody = buildFileBody(file, extra);
			requestEntity.addPart("files", fileBody);
			requestEntity.addPart("accessToken", new StringBody(accessToken,Charset.forName(JsonStatus.CHARSET)));
			setKey(requestEntity, name);
			setParam(requestEntity, extra.params);
			String url = UP_HOST;
			CallRet ret = callWithMultiPart(url, requestEntity);
			if(ret.response.contains("success")) {
				return IMAGE_HOST + name;
			}
		}catch (Exception e) {
			return null;
		}finally{
			if(file != null){
				try{file.delete();}catch(Exception e){}
			}
		}
		return null;
	}
	private static File copyToTmpFile(InputStream from){
		FileOutputStream os = null;
		try{
			File to = File.createTempFile("auto_", ".tmp");
			os = new FileOutputStream(to);
			byte[] b = new byte[64 * 1024];
			int l;
			while ((l = from.read(b)) != -1) {
				os.write(b, 0, l);
			}
			os.flush();
			return to;
		}catch(Exception e){
			throw new RuntimeException("create tmp file failed.", e);
		}finally{
			if (os != null){
				try{os.close();}catch(Exception e){}
			}
			if (from != null){
				try{from.close();}catch(Exception e){}
			}
		}
	}
	
	public static HttpPost newPost(String url){
		HttpPost postMethod = new HttpPost(url);
		postMethod.setHeader(HEADER_AGENT, getUserAgent());
		return postMethod;
	}
	private static String getUserAgent(){
		String javaVersion = "Java/" + System.getProperty("java.version");
		String os = System.getProperty("os.name") + " "
		+  System.getProperty("os.arch") + " " + System.getProperty("os.version");
		String sdk = "autoJava/" + JsonStatus.VERSION;
		return sdk + " (" + os +") " + javaVersion;
	}
	
	private static void setKey(MultipartEntity requestEntity, String key) throws UnsupportedEncodingException{
		if(key != null){
			requestEntity.addPart("key", new StringBody(key,Charset.forName(JsonStatus.CHARSET)));
		}
	}
	private static void setParam(MultipartEntity requestEntity, Map<String, String> params) throws UnsupportedEncodingException{
		if(params == null){
			return;
		}
		for(String name : params.keySet()){
			requestEntity.addPart(name, new StringBody(params.get(name),Charset.forName(JsonStatus.CHARSET)));
		}
	}
	private static FileBody buildFileBody(File file,PutExtra extra){
		if(extra.mimeType != null){
			return new FileBody(file, extra.mimeType);
		}else{
			return new FileBody(file);
		}
	}
	public static CallRet callWithMultiPart(String url, MultipartEntity requestEntity) {
		HttpPost postMethod = newPost(url);
		postMethod.setEntity(requestEntity);
		HttpClient client = Http.getClient();

		try {
			HttpResponse response = client.execute(postMethod);
			return handleResult(response);
		} catch (Exception e) {
			e.printStackTrace();
			return new CallRet(JsonStatus.ERROR, e);
		}
	}
	private static CallRet handleResult(HttpResponse response) {
		if (response == null || response.getStatusLine() == null) {
			return new CallRet(JsonStatus.ERROR, "No response");
		}

		String responseBody;
		try {
			responseBody = EntityUtils.toString(response.getEntity(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return new CallRet(JsonStatus.ERROR, e);
		}

		StatusLine status = response.getStatusLine();
		int statusCode = (status == null) ? JsonStatus.ERROR : status.getStatusCode();
		return new CallRet(statusCode, responseBody);
	}
	
	public static byte[] File2byte(File file)
    {
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }
	public static void main(String[] args) throws Exception {
		String token =  getImageToken();
		System.out.println(token);
		if(token == null) {
			System.out.println("授权失败！");
		}else {
			File file = new File("/Users/wanglongtao/Desktop/1.jpg");
			String ext = ImageUtils.getSuffix(file.getName());
			String url = uploadVerify(token, File2byte(file), ext);
			System.out.println(url);
		}
	}
}
class RESULT {
	Integer errCode;
	String msg;
	String data;
}
class CallRet {
	/** http status code */
	public int statusCode;

	/** The http response body */
	public String response;

	/** Any exception when dealing with the request */
	public Exception exception;

	public CallRet() {
	}

	public CallRet(int statusCode, String responseBody) {
		this.statusCode = statusCode;
		this.response = responseBody;
	}

	public CallRet(int error, Exception e) {
		this.statusCode = error;
		this.exception = e;
	}
}
class PutExtra {
    /** 用户自定义参数，key必须以 "x:" 开头 */
    public Map<String, String> params;
    // 可选
    public String mimeType;

    public long crc32;

    public int checkCrc;
}
