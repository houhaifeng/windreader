package cn.wind.com.page.mine.algrithm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;

import cn.wind.com.page.mine.PageMine;
import cn.wind.com.page.mine.model.PlainContent;
import cn.wind.com.page.mine.model.SegmentWord;
import cn.wind.com.page.mine.utils.HttpUtils;
import cn.wind.com.reader.biz.RssContentBiz;
import cn.wind.com.reader.factory.ReaderServiceFactory;
import cn.wind.com.reader.model.ListParam;
import cn.wind.com.reader.model.RssContent;
import cn.wind.com.reader.model.Term;

public class IMatch {
	private static final Logger log = Logger.getLogger(PageMine.class);
	private static Map<Long,List<Term>> document = new LinkedHashMap<Long,List<Term>>();
	private static Map<String,Double> idf = new LinkedHashMap<String,Double>();
	private static final RssContentBiz rssContentBiz = ReaderServiceFactory.getRssContentBiz();
	private PageMine pm = new PageMine();
	
	public IMatch() {
		ListParam listParam = new ListParam(0,10,0);
		List<RssContent> list = rssContentBiz.getAllByPage(listParam);
		for(RssContent rssContent : list){
			try {
				log.debug("document ID:[" + rssContent.getId() + "]");
				PlainContent result = HttpUtils.getHtmlContent(rssContent.getLink(), 10000);
				pm.parseHtmlByHHF(result);
//				List<SegmentWord> contentSegment = pm.mmWordSegment(result.getContent());
//				List<Term> termList = new ArrayList<Term>();
//				for(SegmentWord seg :contentSegment){
//					if(entry.getKey().equals("sum")){
//						continue;
//					}
//					Term term = new Term();
//					term.setTerm(entry.getKey());
//					term.setWeight(entry.getValue()/(double)contentSegment.get("sum"));
//					termList.add(term);
//					if(idf.containsKey(entry.getKey())){
//						idf.put(entry.getKey(), idf.get(entry.getKey()) + 1);
//					}else{
//						idf.put(entry.getKey(), (double)1);
//					}
//				}
//				document.put(rssContent.getId(), termList);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void calculate(){
		int documentSize = document.size();
		for(Entry<Long,List<Term>> entry : document.entrySet()){
			List<Term> terms = entry.getValue();
			for(Term term : terms){	
				double weight = term.getWeight() * (Math.log(documentSize/(double)(idf.get(term.getTerm()))));
				term.setWeight(weight);
			}
			Collections.sort(terms);
			document.put(entry.getKey(), terms);
		}
	}
	
	public void printDocumentDetail(){
		for(Entry<Long,List<Term>> entry : document.entrySet()){
			System.out.println("Document [" + entry.getKey() + "]:");
			StringBuilder sb = new StringBuilder();
			for(Term term : entry.getValue()){
				sb.append(term.getTerm() + ":" + term.getWeight() + "|");
			}

			System.out.println(sb.toString());
		}
	}
	
	public static void main(String[] args){
		IMatch imatch = new IMatch();
		imatch.calculate();
		imatch.printDocumentDetail();
	}
}
