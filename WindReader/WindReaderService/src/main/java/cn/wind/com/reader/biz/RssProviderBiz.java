package cn.wind.com.reader.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wind.com.reader.dao.RssProviderDAO;
import cn.wind.com.reader.model.Category;
import cn.wind.com.reader.model.RssProvider;

@Service
public class RssProviderBiz {
	@Autowired 
	RssProviderDAO rssProviderDAO;
	
	public RssProvider get(RssProvider rss){
		return rssProviderDAO.get(rss);
	}
	
	public long add(RssProvider rss){
		return rssProviderDAO.add(rss);
	}

	public void update(RssProvider rss){
		rssProviderDAO.update(rss);
	}

	public void delete(RssProvider rss){
		rssProviderDAO.delete(rss);
	}
	
	public List<RssProvider> getAll(){
		return rssProviderDAO.getAll();
	}
	
	public List<RssProvider> getAllValidateRss(){
		return rssProviderDAO.getAllValidateRss();
	}
	
	public List<RssProvider> getAllValidateRssByCategory(Category category){
		return rssProviderDAO.getAllValidateRssByCategory(category);
	}
}
