package cn.wind.com.spider;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.wind.com.reader.biz.RssProviderBiz;
import cn.wind.com.reader.model.RssProvider;

public class BaiduSpider {
	private static final Logger log = Logger.getLogger(BaiduSpider.class);
	private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	public static void persistent(Map<String,String> map,long parentId){
		RssProviderBiz rssProviderBiz = (RssProviderBiz) context.getBean("rssProviderBiz");
		long parent=0;
		RssProvider rssProvider = null;
		if(map != null){
			int index = 0;
			for(Map.Entry<String, String> entry : map.entrySet()){
				rssProvider = new RssProvider();
				rssProvider.setName(entry.getKey());
				rssProvider.setUrl(entry.getValue());
				rssProvider.setDescription("baidu rss");
				if(index == 0){
					rssProvider.setParentId(parentId);
					parent = rssProviderBiz.add(rssProvider);
				}else{
					rssProvider.setParentId(parent);
					rssProviderBiz.add(rssProvider);
				}
				index++;
			}
		}
	}
	public static void main(String[] args){
		RssProviderBiz rssProviderBiz = (RssProviderBiz) context.getBean("rssProviderBiz");
		RssProvider rssProvider = new RssProvider();
		rssProvider.setName("百度新闻");
		rssProvider.setUrl("http://www.baidu.com/search/rss.html");
		rssProvider.setDescription("百度RSS来源");
		rssProvider.setParentId(-1);
		long parentId = rssProviderBiz.add(rssProvider);
		
		Parser htmlParser = null;
		try {
			htmlParser = new Parser("http://www.baidu.com/search/rss.html");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log.error("Html parser error:", e1);
			return;
		}
		NodeFilter filter = new TagNameFilter("li");
		NodeList nodes = null;
		try {
			htmlParser.setEncoding("GBK");
			nodes = htmlParser.extractAllNodesThatMatch(filter);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Html parser error:", e);
		}
		Map<String,String> map = null;
		String name = "";
		String value = "";
		if (nodes != null) {
			for (int i = 0; i < nodes.size(); i++) {
				Node liNode = nodes.elementAt(i);
				NodeList list = liNode.getChildren();
				for(int j = 0; j < list.size(); j++){
					Node node = list.elementAt(j);
					if(node != null && !"".equals(node.toHtml().trim())){
						if(node instanceof Span){
							Node nameNode = node.getFirstChild();
							if(nameNode instanceof TextNode){
								name=nameNode.getText();
							}
							
							if("close".equals(((Span) node).getAttribute("class"))){
								if(map != null){
									persistent(map,parentId);
									for(Map.Entry<String,String> entry : map.entrySet()){
										System.out.println(entry.getKey() + ":" + entry.getValue());
									}
								}
								map =  new LinkedHashMap<String,String>();
							}
						}
						if(node instanceof InputTag){
							value = ((InputTag) node).getAttribute("value");
							map.put(name, value);
						}
					}
				}
			}
		}
	}
}
