package cn.wind.com.reader.constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.tags.CompositeTag;

public class HttpUtils {
	private static String streamToString(InputStream is,String Charset) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}
	
	public static String getHtmlContent(String url,String Charset) throws HttpException, IOException{
		String result = new String();
		HttpClient client = new HttpClient();
		//client.getParams().setParameter(HttpMethodParams.USER_AGENT,MOBILE_AGENT);
		//client.getParams().setParameter("http.protocol.cookie-policy", org.apache.http.client.params.CookiePolicy.IGNORE_COOKIES);
		GetMethod get = new GetMethod();
		InputStream is = null;
		try {
			get = new GetMethod(url);
			get.getParams().setContentCharset(Charset);
			client.executeMethod(get);
			is = get.getResponseBodyAsStream();
			result = streamToString(is,Charset);
		} finally {
			get.releaseConnection();
			if(is != null)
				is.close();
		}
		return result;
	}
	/**
	 * 
	 * html,wap链接标签过滤器
	 * 
	 */
	protected static class WmlGoTag extends CompositeTag {
		private static final long serialVersionUID = 1L;
		private static final String[] jumpTag = new String[]{"GO"};
		private static final String[] endTag = new String[]{"ANCHOR"};
		public static String[] getJumptag() {
			return jumpTag;
		}
		public static String[] getEndtag() {
			return endTag;
		}
		public String getLink() {
			return super.getAttribute("href");
		}
		public String getMethod() {
			return super.getAttribute("method");
		}
	}
}
