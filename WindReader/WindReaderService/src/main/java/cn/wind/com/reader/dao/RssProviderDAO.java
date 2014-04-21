package cn.wind.com.reader.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import cn.wind.com.reader.model.Category;
import cn.wind.com.reader.model.RssProvider;

@DAO
public interface RssProviderDAO {

	String rssColumns = "id, name, url, description, category, parent_id";
	String rssFields = ":1.id, :1.name, :1.url, :1.description, :1.category, :1.parentId";
	String rssUpdate = "id=:1.id, name=:1.name, url=:1.url, description=:1.description, category=:1.category, parent_id=:1.parentId";
	
	@SQL("SELECT " + rssColumns + " FROM rss_provider WHERE id=:1.id")
	public RssProvider get(RssProvider rss);
	
	@ReturnGeneratedKeys
	@SQL("INSERT INTO rss_provider(" + rssColumns + ")VALUES(" + rssFields + ")")
	public long add(RssProvider rss);
	
	@SQL("UPDATE rss_provider SET " + rssUpdate + " WHERE id=:1.id")
	public void update(RssProvider rss);
	
	@SQL("DELETE FROM rss_provider WHERE id=:1.id")
	public void delete(RssProvider rss);
	
	@SQL("SELECT " + rssColumns + " FROM rss_provider")
	public List<RssProvider> getAll();
	
	@SQL("SELECT " + rssColumns + " FROM rss_provider where parent_id<>-1")
	public List<RssProvider> getAllValidateRss();
	
	@SQL("SELECT " + rssColumns + " FROM rss_provider where parent_id<>-1 AND category=:1.id")
	public List<RssProvider> getAllValidateRssByCategory(Category category);
}
