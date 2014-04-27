package cn.wind.com.observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.wind.com.parser.Parser;
import cn.wind.com.reader.biz.RssContentBiz;
import cn.wind.com.reader.factory.ReaderServiceFactory;
import cn.wind.com.reader.model.RssContent;
import cn.wind.com.reader.model.RssProvider;

public class RssObserver extends Observer{
	private static final Logger log = Logger.getLogger(RssObserver.class);
	private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static final int MAX_THREAD=1;
	private long getDate(String pubDate){
		Date date = null;
		DateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");//搜狐格式
		try {
			date = sdf.parse(pubDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//log.debug("not the correct souhu format, try another one");
		}
		if(date == null){
			sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS Z");//百度格式
			try {
				date = sdf.parse(pubDate.replace("Z", " UTC"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//log.debug("not the correct baidu format, try another one");
			}
			if(date == null){
				try{
					date = new Date(pubDate);
				}catch(Exception e){
					//log.debug("not the correct wangyi format, try another one");
				}
			}
		}
		if(date != null){
			System.out.println(date.toLocaleString());
			return date.getTime();
		}
		return -1l;
	}
	public List<RssContent> parseHtml(String link) throws ParserConfigurationException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if(StringUtils.isEmpty(link)){
			return null;
		}
		List<RssContent> list = new ArrayList<RssContent>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = null;
		try {
			document = builder.parse(link);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		} 
		Element element = document.getDocumentElement();
		NodeList nodeList = element.getElementsByTagName("item");	
	    for(int i = 0; i < nodeList.getLength(); i++){
	    	Element ele = (Element) nodeList.item(i);
	    	NodeList ilist = ele.getChildNodes();
	    	RssContent rss = new RssContent();
	    	for(int j = 0 ; j < ilist.getLength() ; j++){
	    		if(ilist.item(j).getNodeType() == Node.ELEMENT_NODE){
	    			String name = ilist.item(j).getNodeName();
	    			String value = ilist.item(j).getTextContent().replaceAll("\n", "").trim();
	    			Method method = null;
						try {
							//log.debug(name.substring(0,1).toUpperCase()+name.substring(1,name.length()));
							//log.debug(name+":"+value);
							if(name.equals("description")){
								if(value.length() > 5000)
									value=value.substring(0,5000);
							}
							if(name.equals("pubDate")){
								long dateTime = this.getDate(value);
								Calendar calendar = new GregorianCalendar();
								calendar.setTime(new Date());
								calendar.add(Calendar.DATE, -3);
								long newest = calendar.getTimeInMillis();
								if(dateTime < newest || dateTime > System.currentTimeMillis()){
									continue;
								}
								method = rss.getClass().getMethod("set" + name.substring(0,1).toUpperCase()+name.substring(1,name.length()), long.class);
								method.invoke(rss, dateTime);
							}else{
								method = rss.getClass().getMethod("set" + name.substring(0,1).toUpperCase()+name.substring(1,name.length()), String.class);
								method.invoke(rss, value);
							}
							
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							log.debug("no such method");
						}
	    		}
	    	}
	    	list.add(rss);
	    }
	    return list;
	}
	@Override
	public void update(Parser parser) {
		// TODO Auto-generated method stub
		List<RssProvider> list= parser.parse();
		RssContentBiz rssBiz = ReaderServiceFactory.getRssContentBiz();
		for(int i = 0 ; i < MAX_THREAD ; i++){
			int start = list.size()/MAX_THREAD*i;
			int end = list.size()/MAX_THREAD*(i+1) >= list.size() ? list.size() : list.size()/MAX_THREAD*(i+1);
			ParserThread parserThread = new ParserThread(start,end,list,rssBiz);
			Thread thread = new Thread(parserThread);
			thread.start();
			log.debug("Thread " + thread.getName() + " is starting.");
		}
	}
	
	class ParserThread implements Runnable{
		int start;
		int end;
		List<RssProvider> list;
		RssContentBiz rssBiz;
		
		public ParserThread(int start, int end, List<RssProvider> list,
				RssContentBiz rssBiz) {
			super();
			this.start = start;
			this.end = end;
			this.list = list;
			this.rssBiz = rssBiz;
		}

		public void run() {
			// TODO Auto-generated method stub
			try {
				for(int i = start; i < end;  i++){
					RssProvider rssProvider = list.get(i);
					if(StringUtils.isNotEmpty(rssProvider.getUrl())){
						List<RssContent> rssList = parseHtml(rssProvider.getUrl());
						//log.debug("Thread [" + Thread.currentThread().getId() + "]:" + rssProvider.getUrl());
						if(rssList != null){
							for(RssContent rss : rssList){
								rss.setProviderId(rssProvider.getId());
								if(StringUtils.isEmpty(rss.getSource())){
									rss.setSource(rssProvider.getName());
								}
								List<RssContent> rssContents = rssBiz.getByLink(rss);
								if(rssContents == null || rssContents.size() == 0){
									rssBiz.add(rss);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.debug("Thread [" + Thread.currentThread().getId() + "] Parse provider rss source failed.",e);
			}
		}
		
	}

	public static void main(String[] args) throws ParseException, IllegalArgumentException, ParserConfigurationException, IllegalAccessException, InvocationTargetException{
		 RssObserver ro = new RssObserver();
//		 ro.getDate("Fri, 15 Nov 2013 19:57:03 +0000");
//		 List<RssContent> list = ro.parseHtml("http://feed.feedsky.com/iheima");
//		 for(RssContent rss : list){
//			 System.out.println(rss.toString());
//		 }
//		 
//		 System.out.println(new Date(1386745610000l).toLocaleString());
		 RssContentBiz rssBiz = ReaderServiceFactory.getRssContentBiz();
		 List<RssProvider> list= new Parser().parse();
		 for(RssProvider rssProvider : list){
			 System.out.println(rssProvider);
		 }
	}
}
