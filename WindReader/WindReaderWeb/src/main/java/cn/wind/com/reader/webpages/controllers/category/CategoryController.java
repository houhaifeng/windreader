package cn.wind.com.reader.webpages.controllers.category;

import java.util.List;

import cn.wind.com.reader.biz.CategoryBiz;
import cn.wind.com.reader.factory.ReaderServiceFactory;
import cn.wind.com.reader.model.Category;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("")
public class CategoryController {
	CategoryBiz categoryBiz = ReaderServiceFactory.getCategoryBiz();
	
	@Get("")
	public String get(Invocation inv){
		List<Category> list = categoryBiz.getAll();
		inv.addModel("categoryList", list);
		return "category";
	}
	
	@Post("")
	public String add(@Param("id") Long id, @Param("title") String title, @Param("parentId") Long parentId, @Param("description") String description, Invocation inv){
		Category category = new Category();
		category.setTitle(title);
		category.setParentId(parentId);
		category.setDescription(description);
		categoryBiz.add(category);
		return "r:./category";
	}
	
	@Get("edit/{id:[0-9]+}/")
	public String editShow(@Param("id") Long id, Invocation inv){
		Category category = new Category();
		category.setId(id);
		category = categoryBiz.get(category);
		inv.addModel("category", category);
		return "category_edit";
	}
	
	@Post("edit/{id:[0-9]+}/")
	public String editAction(@Param("id") Long id, @Param("title") String title, @Param("parentId") Long parentId, @Param("description") String description, Invocation inv){
		Category category = new Category();
		category.setId(id);
		category.setTitle(title);
		category.setParentId(parentId);
		category.setDescription(description);
		categoryBiz.update(category);
		return "r:../../../category";
	}
}
