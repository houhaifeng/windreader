package cn.wind.com.page.mine.utils;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.wind.com.page.mine.model.PlainContent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {
	private static final Logger log = Logger.getLogger(HttpUtils.class);
	public static final String MOBILE_AGENT = "Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; MI 2 Build/JRO03L) UC AppleWebKit/534.31 (KHTML, like Gecko) Mobile Safari/534.31";
	public static final String UTF_8 = "utf-8";
	public static final String HTTP_HEAD = "http://";
	private static final String BAIDU_READER="#!/zw/";
	private static HttpClient httpClient;
	static {
		httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, MOBILE_AGENT);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
	}
	private static Pattern chinesePattern = Pattern.compile("^[\\u4E00-\\u9FA5_a-zA-Z0-9 , ，+<>?:,·./;'，。、‘：“《》？~!！@#￥%……（）—]+$");

	private static boolean isChinese(char c){
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS 
	          || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B 
	          || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS 
	          || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) { 
	        return true; 
	    }
		return false;
	}
	
	public static boolean isChinese(String strName) { 
        char[] ch = strName.toCharArray(); 
        for (int i = 0; i < ch.length; i++) { 
            char c = ch[i]; 
            if (isChinese(c)) { 
                return true; 
            } 
        } 
        return false; 
    } 
	
	public static PlainContent getHtmlContent(String url,int timeout){
		HttpURLConnection conn = null;
		PlainContent pc = null;
		BufferedReader reader = null;
		String encode = UTF_8;
		try {
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setReadTimeout(timeout);
			conn.setConnectTimeout(timeout);
			int responseCode = conn.getResponseCode();
			if(responseCode == 500 || responseCode == 404){
				return pc;
			}
			String line = null;
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				if(line.toLowerCase().contains("charset")){
					line = line.trim();
					encode = line.toLowerCase().substring(line.indexOf("charset=") + "charset=".length());
					if(encode.startsWith("\"")){
						encode = encode.substring(1);
				    }
					if(encode.contains("\"")){
						encode = encode.substring(0,encode.indexOf("\""));
					}else{
						encode = encode.substring(0,encode.indexOf("/>")).trim();
					}
					break;
				}
			}
			
			log.debug("The page [" +url + "]'s charset encode is :" + encode);
			if(url.contains("m.baidu.com")){
				String searchKeyWords = getSearchWord(url);
				if(searchKeyWords != null){
					pc = new PlainContent();
					pc.setLink(url);
					pc.setEncode(encode);
					pc.setKeywords(searchKeyWords);
					pc.setContent(searchKeyWords);
					return pc;
				}else{
					if(url.contains(BAIDU_READER)){
						url = url.substring(url.indexOf(BAIDU_READER) + BAIDU_READER.length());
						return getHtmlContent(url,timeout);
					}else{
						String src = getURLParameters(url,"src");
						if(src != null){
							return getHtmlContent(src,timeout);
						}
					}
				}
			}
			
			String content = getHTML(url,encode,timeout);
			if(StringUtils.isNotEmpty(content)){
				pc = new PlainContent();
				pc.setLink(url);
				pc.setEncode(encode);
				pc.setContent(content);
			}
			
		} catch (Exception e) {
			log.error(e);
			return null;
		} finally {
			if(conn != null){
				conn.disconnect();
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					reader = null;
				}
			}
		}
		
		return pc;
	}
	
	public static String getHTML(String url, String encode, int timeout) throws IOException {
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setConnectTimeout(timeout);
		conn.setReadTimeout(timeout);
		BufferedReader reader = null;
		try {
			String line = null;
			StringBuffer buffer = new StringBuffer();
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),encode));
			while ((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}
			return buffer.toString();
		} catch (Exception e) {
			log.error("Get html content error:" + url,e);
			return null;
		} finally {
			conn.disconnect();
			if (reader != null) {
				reader.close();
			}
		}
	}

	/**
	 * 剔除参数
	 * 
	 * @param link
	 * @return
	 */
	public static String extractUrl(String link) {
		if (StringUtils.isEmpty(link)) {
			return null;
		}
		int index = link.indexOf("?");
		if (index != -1) {
			link = link.substring(0, index);
		}
		index = link.lastIndexOf("/");
		if (index != -1 && index > 6) {
			link = link.substring(0, index);
		}
		return link;
	}

	/**
	 * 获取url的host
	 * 
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	@SuppressWarnings("deprecation")
	public static String getHost(String url) {
		if (!checkUrl(url)) {
			return null;
		}
		GetMethod getMethod = new GetMethod(url);

		return getMethod.getHostConfiguration().getHost();
	}

	/**
	 * 判断url是否合法
	 * 
	 * @param link
	 * @return
	 */
	private static boolean checkUrl(String link) {
		if (StringUtils.isEmpty(link)) {
			return false;
		}
		boolean isValidate = false;
		try {
			GetMethod getMethod = new GetMethod(link);
			URI uri = getMethod.getURI();
			isValidate = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn("The link:" + link + "is not a legal URL", e);
			isValidate = false;
		}
		return isValidate;
	}

	/**
	 * 判断当前url是否为死链或跳转链接
	 * 
	 * @param url
	 * @return response code
	 * @throws HttpException
	 * @throws IOException
	 */
	public static boolean checkUrlValidate(String link) {
		boolean isValidate = true;
		if (!checkUrl(link)) {
			return false;
		}

		GetMethod get = null;
		try {
			get = new GetMethod(link);
			get.setFollowRedirects(false);
			int status = httpClient.executeMethod(get);
			if (status == HttpStatus.SC_NOT_FOUND || status == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
				isValidate = false;
			}
		} catch (Exception e) {
			log.error(e);
			isValidate = false;
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}

		return isValidate;
	}

	/**
	 * 获取url的属性参数
	 * 
	 * @param url
	 * @param parameterName
	 * @return 参数值
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public static String getURLParameters(String link, String parameterName) throws MalformedURLException, UnsupportedEncodingException {
		GetMethod getMethod = new GetMethod(link);
		String query = getMethod.getQueryString();
		String result = null;
		if (!StringUtils.isEmpty(query)) {
			String[] parameters = query.split("&");
			for (String parameter : parameters) {
				int pos = parameter.indexOf("=");
				if ((pos != -1) && parameter.subSequence(0, pos).equals(parameterName)) {
					query = parameter.substring(pos + 1, parameter.length());
					if (query.endsWith("%")) {
						return null;
					}
					result = URLDecoder.decode(query, UTF_8);
				}
			}
		}
		return result;
	}

	/**
	 * 获取302跳转的源url
	 * 
	 * @param url
	 * @return
	 */
	public static List<String> getRedirectLinkList(String url) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isEmpty(url)) {
			return list;
		}
		GetMethod method = null;
		int status = 0;
		try {
			method = new GetMethod(url);
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
			method.setFollowRedirects(false);
			status = httpClient.executeMethod(method);
		} catch (Exception e) {
			log.error("URL parse error:" + e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		list.add(HttpUtils.extractUrl(url));
		log.debug(url + " : " + status);
		int index = 0;
		String oldurl = url;
		String hostname = getHost(url);
		while (method != null && (status == HttpStatus.SC_MOVED_TEMPORARILY || status == HttpStatus.SC_MOVED_PERMANENTLY)) {
			index++;
			if (index > 5) {
				break;
			}
			Header header = method.getResponseHeader("Location");
			if (header == null) {
				break;
			}
			System.out.println(header.getValue());
			list.add(HttpUtils.extractUrl(getCompleteUrl(header.getValue(), hostname, oldurl)));
			try {
				method = new GetMethod(header.getValue());
				method.setFollowRedirects(false);
				method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
				status = httpClient.executeMethod(method);
			} catch (Exception e) {
				log.error("URL parse error:" + e);
			} finally {
				if (method != null) {
					method.releaseConnection();
				}
			}
		}
		return list;
	}

	/**
	 * 处理相对地址
	 * 
	 * @param link
	 * @param hostname
	 * @param relativeLink
	 * @return
	 */
	public static String getCompleteUrl(String link, String hostname, String relativeLink) {
		if (StringUtils.isEmpty(link)) {
			return null;
		}
		if (!link.startsWith(HTTP_HEAD)) {
			if (link.startsWith("www")) {
				link = HTTP_HEAD + link;
			} else if (link.startsWith("/")) {
				if (!StringUtils.isEmpty(hostname)) {
					link = HTTP_HEAD + hostname + link;
				} else {
					return null;
				}
			} else {
				if (!StringUtils.isEmpty(relativeLink)) {
					if (relativeLink.endsWith("/")) {
						link = relativeLink + link.substring(1, link.length());
					} else {
						link = relativeLink + "/" + link.substring(1, link.length());
					}
				} else {
					return null;
				}
			}
		}
		return link;
	}

	public static Map<String, String> getParameterPairs(String url) {
		Map<String, String> pairs = new HashMap<String, String>();
		String[] urlParts = url.split("\\?");
		if (urlParts.length > 1) {
			String query = urlParts[1];
			for (String param : query.split("&")) {
				String[] pair = param.split("=");
				if (pair.length == 2) {
					pairs.put(pair[0], pair[1]);
				}
			}
		}
		return pairs;
	}

	public static String getSiteAndRequestPath(String url) {
		if (!isValidUrl(url))
			return null;
		String[] urlParts = url.split("\\?");
		urlParts = urlParts[0].split("(http|https)://");
		if (urlParts[1].endsWith("/"))
			return urlParts[1].substring(0, urlParts[1].length() - 1);
		return urlParts[1];
	}

	public static boolean isValidUrl(String url) {
		if (url != null && url.startsWith("http"))
			return true;
		else
			return false;
	}

	public static String decodeSearchWord(String src) throws IllegalArgumentException {
		if (src == null)
			return null;
		try {
			String utfStr = URLDecoder.decode(src, "utf-8");
			String gbkStr = URLDecoder.decode(src, "gbk");
			if (chinesePattern.matcher(utfStr).matches())
				return utfStr;
			else if (chinesePattern.matcher(gbkStr).matches())
				return gbkStr;
			else
				System.out.println(src);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getSearchWord(String url) throws IllegalArgumentException {
		Map<String, String> parmMap = getParameterPairs(url);
		String searchWord = parmMap.get("word");
		if (searchWord == null) {
			searchWord = parmMap.get("wd");
		}
		return decodeSearchWord(searchWord);
	}

	public static String getCurrentUrl(HttpServletRequest request) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer(request.getRequestURI());
		Map<String, String[]> map = request.getParameterMap();
		int index = 0;
		if (map != null && map.size() > 0) {
			Set<Map.Entry<String, String[]>> set = map.entrySet();
			for (Entry<String, String[]> entry : set) {
				if (index == 0) {
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue()[0], "UTF-8"));
				index++;
			}
		}
		return sb.toString();
	}
	/**
	 * 查看url是否有效
	 * 
	 * @param urlStr
	 * @return
	 */
	public static boolean isConnect(String urlStr) {
		long start = System.currentTimeMillis();
		long end = 0;
		URL url;
		HttpURLConnection con = null;
		int state = -1;
		int counts = 0;
		if (urlStr == null || urlStr.length() <= 0) {
			return false;
		}
		while (counts < 2) {
			System.out.println(counts);
			try {
				counts++;
				url = new URL(urlStr);
				System.out.println("Start to connect the url:" + urlStr);
				con = (HttpURLConnection) url.openConnection();
				System.out.println("after open Connection.");
				con.setRequestMethod("HEAD");
				con.setConnectTimeout(5000);
				con.setReadTimeout(5000);
				String msg = con.getResponseMessage();
				System.out.println("after get Response.");
				if (!msg.equals("Not Found")) {
					end = System.currentTimeMillis();
					System.out.println("Found cost:" + (end - start) + "ms");
					return true;
				}
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
				continue;
			} finally {
				if (con != null) {
					con.disconnect();
				} else {
					con = null;
				}
			}
		}
		end = System.currentTimeMillis();
		System.out.println("NOT Found cost:" + (end - start) + "ms");
		return false;
	}

	public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
//		System.out
//				.println(getRedirectLinkList("http://rss.feedsportal.com/c/33390/f/640222/p/1/s/25c02735/l/0Lsports0B1630N0C140C0A1220C140C9J6TOM550A0A0A51C890Bhtml/story01.htm"));
		//System.out.println(getURLParameters("http://m.baidu.com/from=1269a/ssid=0/uid=0/baiduid=B4DF7AE9F345372B54CD510E7D110DB2/bd_page_type=1/tc?appui=alaxs&srd=1&dir=1&ref=book_iphone&tj=book_cate_1_1_1&gid=1832909557&sec=34574&di=b09c759012beef63&src=http%3A%2F%2Fwww.jdxs.net%2Ffiles%2Farticle%2Fhtml%2F42%2F42823%2Findex.html#!/zw/http://www.yxgsk.com/files/article/html/53/53124/4614999.html","src"));
		System.out.println(getSearchWord("http://m.baidu.com/s?from=1269a&word=%D1%D4%C7%E9%D0%A1%CB%B5"));
	}

}
