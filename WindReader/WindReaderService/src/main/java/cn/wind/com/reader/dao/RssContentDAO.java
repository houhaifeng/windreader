package cn.wind.com.reader.dao;

import java.util.List;

import cn.wind.com.reader.model.ListParam;
import cn.wind.com.reader.model.RssContent;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface RssContentDAO {
	
	String rssColumns = "id, title, link, description, pub_date, guid, source, category_id, category, comments, provider_id, hash";
	String rssColumns_short = "id, title, link, pub_date, guid, source, category_id, category, comments, provider_id, hash";
	String rssFields = ":1.id, :1.title, :1.link, :1.description, :1.pubDate, :1.guid, :1.source, :1.categoryId, :1.category, :1.comments, :1.providerId, :1.hash";
	String rssUpdate = "id=:1.id, title=:1.title, link=:1.link, description=:1.description, pub_date=:1.pubDate, guid=:1.guid, source=:1.source, category_id=:1.categoryId, category=:1.category, comments=:1.comments, provider_id=:1.providerId, hash=:1.hash";
	
	@SQL("SELECT " + rssColumns_short + " FROM rss_content WHERE id=:1.id")
	public RssContent get(RssContent rss);
	
	@SQL("SELECT " + rssColumns_short + " FROM rss_content WHERE title=:1.title")
	public RssContent getByTitle(RssContent rss);
	
	@SQL("SELECT " + rssColumns_short + " FROM rss_content WHERE link=:1.link")
	public List<RssContent> getByLink(RssContent rss);
	
	@ReturnGeneratedKeys
	@SQL("INSERT INTO rss_content(" + rssColumns + ")VALUES(" + rssFields + ")")
	public long add(RssContent rss);
	
	@SQL("UPDATE rss_content SET " + rssUpdate + " WHERE id=:1.id")
	public void update(RssContent rss);
	
	@SQL("DELETE FROM rss_content WHERE id=:1.id")
	public void delete(RssContent rss);
	
	@SQL("SELECT " + rssColumns_short + " FROM rss_content")
	public List<RssContent> getAll();
	
	@SQL("SELECT " + rssColumns_short + " FROM rss_content ORDER BY pub_date desc limit :1.offset,:1.length")
	public List<RssContent> getAllByPage(ListParam listParam);
	
	@SQL("SELECT id, title, link, pub_date, guid, source, category_id, category, comments, provider_id FROM rss_content WHERE provider_id in (select id from rss_provider where category=:1) ORDER BY pub_date desc limit :2.offset,:2.length")
	public List<RssContent> getAllByPageAndCategory(long category, ListParam listParam);
}
