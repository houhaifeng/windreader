package cn.wind.com.reader;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import cn.wind.com.observer.Observer;
import cn.wind.com.observer.RssObserver;
import cn.wind.com.parser.Parser;

public class WindReader {
    private static final Logger logger = Logger.getLogger(WindReader.class);
    public List<String> merge(List<String>[] listArray){
		List<String> list = new ArrayList<String>();
		for(List<String> listEle : listArray){
			for(String ele : listEle){
				if(!list.contains(ele)){
					list.add(ele);
				}
			}
		}
		return list;
	}
	
    public void run(){
    	long start = System.currentTimeMillis();
		Observer rssObserver = new RssObserver();
		rssObserver.addParser(new Parser());
		rssObserver.updateAll();
		long end = System.currentTimeMillis();
		logger.debug("cost time:" + (end-start) + "ms");
    }
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub

		long start = System.currentTimeMillis();
		Observer rssObserver = new RssObserver();
		rssObserver.addParser(new Parser());
		rssObserver.updateAll();
		long end = System.currentTimeMillis();
		System.out.println("cost time:" + (end-start) + "ms");
	}

}
