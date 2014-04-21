package cn.wind.com.reader.webpages.controllers;

import java.util.List;

import cn.wind.com.reader.biz.RssContentBiz;
import cn.wind.com.reader.factory.ReaderServiceFactory;
import cn.wind.com.reader.model.RssContent;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("")
public class HomepageController {
	RssContentBiz rssContentBiz = ReaderServiceFactory.getRssContentBiz();
	@Get("test/")
	public String test(){
		
		return "@test";
	}
	@Get("")
	public String get(Invocation inv){
		List<RssContent> list = rssContentBiz.getAllByPage(null);
		inv.getModel().add("contentList",list);
		return "index";
	}
}
