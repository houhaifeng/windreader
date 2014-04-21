package cn.wind.com.reader.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wind.com.reader.dao.CategoryDAO;
import cn.wind.com.reader.model.Category;

@Service
public class CategoryBiz {

	@Autowired
	CategoryDAO categoryDAO;
	
	
	public Category get(Category category){
		return categoryDAO.get(category);
	}
	
	public long add(Category category){
		return categoryDAO.add(category);
	}
	
	public void update(Category category){
		categoryDAO.update(category);
	}
	
	public void delete(Category category){
		categoryDAO.delete(category);
	}
	
	public List<Category> getAll(){
		return categoryDAO.getAll();
	}
	
	public List<Category> getAllSuperCategory(){
		return categoryDAO.getAllSuperCategory();
	}
	
	public List<Category> getAllSubCategory(Category category){
		return categoryDAO.getAllSubCategory(category);
	}

	public List<Category> getAllSecondCategory(){
		return categoryDAO.getAllSecondCategory();
	}
}
