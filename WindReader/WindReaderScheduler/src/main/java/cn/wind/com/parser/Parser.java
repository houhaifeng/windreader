package cn.wind.com.parser;
import java.util.List;

import cn.wind.com.reader.biz.RssProviderBiz;
import cn.wind.com.reader.factory.ReaderServiceFactory;
import cn.wind.com.reader.model.RssProvider;

public class Parser {
	protected String charSet="utf-8";
	public List<RssProvider> parse(){
		RssProviderBiz rssBiz = ReaderServiceFactory.getRssProviderBiz();
		List<RssProvider> list = rssBiz.getAllValidateRss();
		return list;
	}
}
