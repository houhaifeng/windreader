package cn.wind.com.reader.dao;

import java.util.List;

import cn.wind.com.reader.model.Category;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;


@DAO
public interface CategoryDAO {

	String categoryColumns = "id, title, description, parent_id";
	String categoryFields = ":1.id, :1.title, :1.description, :1.parentId";
	String categoryUpdate = "id=:1.id, title=:1.title, description=:1.description, parent_id=:1.parentId";
	
	@SQL("SELECT " + categoryColumns + " FROM category WHERE id=:1.id")
	public Category get(Category category);
	
	@ReturnGeneratedKeys
	@SQL("INSERT INTO category(" + categoryColumns + ")VALUES(" + categoryFields + ")")
	public long add(Category category);
	
	@SQL("UPDATE category SET " + categoryUpdate + " WHERE id=:1.id")
	public void update(Category category);
	
	@SQL("DELETE FROM category WHERE id=:1.id")
	public void delete(Category category);
	
	@SQL("SELECT " + categoryColumns + " FROM category ORDER BY parent_id")
	public List<Category> getAll();
	
	@SQL("SELECT " + categoryColumns + " FROM category where parent_id=-1 ORDER BY id")
	public List<Category> getAllSuperCategory();
	
	@SQL("SELECT " + categoryColumns + " FROM category where parent_id=:1.id ORDER BY id")
	public List<Category> getAllSubCategory(Category category);
	
	@SQL("SELECT " + categoryColumns + " FROM category where parent_id<>-1")
	public List<Category> getAllSecondCategory();
}
