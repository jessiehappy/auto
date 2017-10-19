package auto.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

@CommonsLog
public class HttpUtils {

	public static final int DEFAULT_TIMEOUT = 5000; // 5 seconds
	
	public static final String GBK = "GBK";
	
	public static final String UTF8 = "UTF-8";

	public static byte[] getBytes(String url) throws Exception {
		return executeBytes(new HttpGet(url), DEFAULT_TIMEOUT, null);
	}

	public static String get(String url) throws Exception {
		return execute(new HttpGet(url), DEFAULT_TIMEOUT, null);
	}

	public static <T> T get(String url, Class<T> jsonClass) throws Exception {
		return (T) StringUtils.GSON.fromJson(get(url), jsonClass);
	}

	public static String get(String url, Map<String, String> paramsMap) throws Exception {
		String params = UriUtils.formatParams(paramsMap);
		if (StringUtils.isNotEmpty(params)) {
			url = url + "?" + params;
		}
		return execute(new HttpGet(url), DEFAULT_TIMEOUT, null);
	}

	public static String post(String url, Map<String, String> paramsMap) throws Exception {
		return post(url, UriUtils.getNameValuePairs(paramsMap), DEFAULT_TIMEOUT, null);
	}

	public static String post(String url, Map<String, String> paramsMap, String urlCode) throws Exception {
		return post(url, UriUtils.getNameValuePairs(paramsMap), urlCode);
	}
	public static String post(String url, List<NameValuePair> params, String urlCode) throws Exception {
		HttpPost method = new HttpPost(url);
		if (params != null) {
			method.setEntity(new UrlEncodedFormEntity(params, urlCode));
		}
		return execute(method, DEFAULT_TIMEOUT, null);
	}

	public static String post(String url, List<NameValuePair> params, int timeout, String password) throws Exception {
		HttpPost method = new HttpPost(url);
		if (params != null) {
			method.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		}
		return execute(method, timeout, password);
	}

	public static String put(String url, Map<String, String> paramsMap) throws Exception {
		HttpPut method = new HttpPut(url);
		List<NameValuePair> params = UriUtils.getNameValuePairs(paramsMap);
		if (params != null) {
			method.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		}
		return execute(method, DEFAULT_TIMEOUT, null);
	}

	public static String delete(String url) throws Exception {
		return execute(new HttpDelete(url), DEFAULT_TIMEOUT, null);
	}

	public static String delete(String url, Map<String, String> paramsMap) throws Exception {
		String params = UriUtils.formatParams(paramsMap);
		if (StringUtils.isNotEmpty(params)) {
			url = url + "?" + params;
		}
		return execute(new HttpDelete(url), DEFAULT_TIMEOUT, null);
	}

	private static byte[] executeBytes(HttpRequestBase method, int timeout, String password) throws Exception {
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			if (password != null) {
				UsernamePasswordCredentials creds = new UsernamePasswordCredentials(password);
				client.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
			}
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
			HttpResponse response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return IOUtils.toByteArray(entity.getContent());
			} else {
				return null;
			}
		} finally {
			try {
				method.releaseConnection();
			} catch (Exception e) {
				log.error(e, e);
			}
		}
	}

	private static String execute(HttpRequestBase method, int timeout, String password) throws Exception {
		byte[] bytes = executeBytes(method, timeout, password);
		if (bytes == null)
			return null;
		return new String(bytes, "UTF-8");
	}

	public static String postXml(String url, String content) throws Exception {
		return post(url, content, DEFAULT_TIMEOUT, "application/xml");
	}

	public static String postJson(String url, String content) throws Exception {
		return post(url, content, DEFAULT_TIMEOUT, "application/json");
	}

	public static String postImage(String url, File file, Map<String, String> paramsMap) throws Exception{

		Validate.notNull(url);
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		try {
			connection.setConnectTimeout(DEFAULT_TIMEOUT);
			connection.setReadTimeout(DEFAULT_TIMEOUT);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "multipart/form-data");
			
			MultipartEntity mutiEntity = new MultipartEntity();
			mutiEntity.addPart("desc",new StringBody("", Charset.forName("utf-8")));
			mutiEntity.addPart("pic", new FileBody(file));
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static String post(String url, String content, int timeout, String contentType) throws Exception {
		Validate.notNull(url);
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		try {
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", contentType);
			// write
			if (content != null) {
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
				try {
					out.write(content);
				} finally {
					IOUtils.closeQuietly(out);
				}
			}
			// read
			return IOUtils.toString(connection.getInputStream(), Charset.forName("utf-8"));
		} finally {
			IOUtils.close(connection);
		}
	}

	public static void download(String url, File file) throws IOException {
		Validate.notNull(url);
		Validate.notNull(file);
		if (log.isDebugEnabled()) {
			log.debug(url + "," + file);
		}
		URLConnection connection = new URL(url).openConnection();
		OutputStream out = null;
		try {
			connection.setConnectTimeout(DEFAULT_TIMEOUT);
			connection.setReadTimeout(DEFAULT_TIMEOUT);
			out = new BufferedOutputStream(new FileOutputStream(file));
			IOUtils.copyLarge(connection.getInputStream(), out);
		} finally {
			IOUtils.close(connection);
			if (out != null) {
				out.close();
			}
		}
	}

}
