package cn.wind.com.reader.webpages.controllers.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wind.com.reader.biz.CategoryBiz;
import cn.wind.com.reader.biz.RssContentBiz;
import cn.wind.com.reader.biz.RssProviderBiz;
import cn.wind.com.reader.factory.ReaderServiceFactory;
import cn.wind.com.reader.model.Category;
import cn.wind.com.reader.model.ListParam;
import cn.wind.com.reader.model.RssContent;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("")
public class ContentController {
	RssContentBiz rssContentBiz = ReaderServiceFactory.getRssContentBiz();
	RssProviderBiz rssProviderBiz = ReaderServiceFactory.getRssProviderBiz();
	CategoryBiz categoryBiz = ReaderServiceFactory.getCategoryBiz();
	
	@Get("test/")
	public String test(){
		return "@test";
	}
	
	@Get("")
	public String get(Invocation inv){
		List<Category> categoryList = categoryBiz.getAllSuperCategory();
		Map<Category,List<Category>> map = new HashMap<Category,List<Category>>();
		for(Category category : categoryList){
			List<Category> subCategory = categoryBiz.getAllSubCategory(category);
			map.put(category, subCategory);
		}
		inv.addModel("categoryMap", map);
		return "rss_content";
	}
	
	@Get("category/{id:[0-9]+}/")
	public String getContentByCategory(@Param("id") Long id, Invocation inv){
		ListParam listParam = new ListParam(0,500,1);
		List<RssContent> contentList =  rssContentBiz.getAllByPageAndCategory(id, listParam);
		inv.getModel().add("contentList",contentList);
		return "rss_content_category";
	}
	
	@Get("edit/{id:[0-9]+}/")
	public String editShow(@Param("id") Long id, Invocation inv){
		RssContent rssContent = new RssContent();
		rssContent.setId(id);
		rssContent = rssContentBiz.get(rssContent);
		inv.getModel().add("rssContent",rssContent);
		
		return "rss_content_edit";
	}
	
	@Post("edit/{id:[0-9]+}/")
	public String editAction(RssContent rssContent, Invocation inv){
		rssContentBiz.update(rssContent);
		return "r:../../../content";
	}
}
