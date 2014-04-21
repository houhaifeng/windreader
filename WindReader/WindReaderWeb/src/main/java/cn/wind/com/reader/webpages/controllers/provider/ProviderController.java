package cn.wind.com.reader.webpages.controllers.provider;

import java.util.ArrayList;
import java.util.List;

import cn.wind.com.reader.biz.CategoryBiz;
import cn.wind.com.reader.biz.RssProviderBiz;
import cn.wind.com.reader.factory.ReaderServiceFactory;
import cn.wind.com.reader.model.Category;
import cn.wind.com.reader.model.RssProvider;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("")
public class ProviderController {
	RssProviderBiz rssProviderBiz = ReaderServiceFactory.getRssProviderBiz();
	CategoryBiz categoryBiz = ReaderServiceFactory.getCategoryBiz();
	@Get("test/")
	public String test(){
		
		return "@test";
	}
	
	@Get("")
	public String get(Invocation inv){
		List<RssProvider> list = rssProviderBiz.getAll();
		List<Category> categoryList = new ArrayList<Category>();
		Category category = new Category();
		category.setId(-1);
		category.setParentId(-1);
		category.setTitle("NONE");
		category.setDescription("NONE");
		categoryList.add(category);
		categoryList.addAll(categoryBiz.getAll());
		inv.getModel().add("categoryList",categoryList);
		inv.getModel().add("providerList",list);
		return "rss_provider";
	}
	
	@Post("")
	public String add(RssProvider rssProvider,Invocation inv){
		rssProviderBiz.add(rssProvider);
		return "r:./provider";
	}
	
	@Get("edit/{id:[0-9]+}/")
	public String editShow(@Param("id") Long id, Invocation inv){
		RssProvider rssProvider = new RssProvider();
		rssProvider.setId(id);
		rssProvider = rssProviderBiz.get(rssProvider);
		List<Category> categoryList = new ArrayList<Category>();
		Category category = new Category();
		category.setId(-1);
		category.setParentId(-1);
		category.setTitle("NONE");
		category.setDescription("NONE");
		categoryList.add(category);
		categoryList.addAll(categoryBiz.getAll());
		inv.getModel().add("rssProvider",rssProvider);
		inv.getModel().add("categoryList",categoryList);
		return "rss_provider_edit";
	}
	
	@Post("edit/{id:[0-9]+}/")
	public String editAction(RssProvider rssProvider, Invocation inv){
		rssProviderBiz.update(rssProvider);
		return "r:../../../provider";
	}
	
	
	@Post("update/{id:[0-9]+}/")
	public String update(@Param("id") Long id, @Param("category") Long category,  Invocation inv){
		RssProvider rssProvider = new RssProvider();
		rssProvider.setId(id);
		rssProvider = rssProviderBiz.get(rssProvider);
		rssProvider.setCategory(category);
		rssProviderBiz.update(rssProvider);
		return "r:../../../provider";
	}
}
