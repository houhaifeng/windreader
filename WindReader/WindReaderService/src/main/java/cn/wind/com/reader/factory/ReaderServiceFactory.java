package cn.wind.com.reader.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.wind.com.reader.biz.CategoryBiz;
import cn.wind.com.reader.biz.RssContentBiz;
import cn.wind.com.reader.biz.RssProviderBiz;

public class ReaderServiceFactory {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	public static RssContentBiz getRssContentBiz(){
		return (RssContentBiz)context.getBean("rssContentBiz");
	}
	
	public static RssProviderBiz getRssProviderBiz(){
		return (RssProviderBiz)context.getBean("rssProviderBiz");
	}
	
	public static CategoryBiz getCategoryBiz(){
		return (CategoryBiz)context.getBean("categoryBiz");
	}
}
