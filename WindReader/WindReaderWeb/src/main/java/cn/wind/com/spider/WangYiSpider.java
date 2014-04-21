package cn.wind.com.spider;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.wind.com.reader.biz.RssProviderBiz;
import cn.wind.com.reader.constant.HttpUtils;
import cn.wind.com.reader.model.RssProvider;

public class WangYiSpider {
	private static final Logger log = Logger.getLogger(BaiduSpider.class);
	private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	public static void main(String[] args) throws HttpException, IOException{
		RssProviderBiz rssProviderBiz = (RssProviderBiz) context.getBean("rssProviderBiz");
		RssProvider rssProvider = new RssProvider();
		rssProvider.setName("网易新闻");
		rssProvider.setUrl("http://www.163.com/rss");
		rssProvider.setDescription("网易RSS来源");
		rssProvider.setParentId(-1);
		long parentId = rssProviderBiz.add(rssProvider);
		String page = HttpUtils.getHtmlContent("http://www.163.com/rss", "GBK");
		String[] array = page.split("\n");
		long parent = 0;
		String name="";
		String value="";
		for(String str : array){
			if(str.contains("http://tech.163.com/special/000915SN/simplerss.html")){
				continue;
			}
			if(str.contains("class=\"cBlue\"")){
				name = str.substring(0,str.lastIndexOf("</a>"));
				name = name.substring(name.lastIndexOf(">")+1);
				System.out.println(name);
			}else if(str.contains("class=\"xml\"") ){
				value = str.substring(str.indexOf("href=\"")+6);
				value = value.substring(0,value.indexOf("\""));
				rssProvider = new RssProvider();
				rssProvider.setName(name);
				rssProvider.setUrl(value);
				rssProvider.setDescription("网易RSS来源");
				rssProvider.setParentId(parent);
				rssProviderBiz.add(rssProvider);
				System.out.println(value);
				System.out.println(rssProvider.toString());
			}else if(str.contains("class=\"normal")){
				String category = str.substring(str.indexOf("---")+3,str.lastIndexOf("---"));
				System.out.println(category);
				rssProvider = new RssProvider();
				rssProvider.setName(category);
				rssProvider.setUrl("");
				rssProvider.setDescription("网易RSS来源");
				rssProvider.setParentId(parentId);
				parent = rssProviderBiz.add(rssProvider);
				System.out.println(rssProvider.toString());
			}
		}
	}
}
