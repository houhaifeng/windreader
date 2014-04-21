package cn.wind.com.reader.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wind.com.reader.dao.RssContentDAO;
import cn.wind.com.reader.model.ListParam;
import cn.wind.com.reader.model.RssContent;

@Service
public class RssContentBiz {
	@Autowired 
	RssContentDAO rssContentDAO;
	
	public RssContent get(RssContent rss){
		return rssContentDAO.get(rss);
	}
	public List<RssContent> getByLink(RssContent rss){
		return rssContentDAO.getByLink(rss);
	}
	public RssContent getByTitle(RssContent rss){
		return rssContentDAO.getByTitle(rss);
	}
	
  public long add(RssContent rss){
  	return rssContentDAO.add(rss);
  }
	
	public void update(RssContent rss){
		rssContentDAO.update(rss);
	}
	
	public void delete(RssContent rss){
		rssContentDAO.delete(rss);
	}
	
	public List<RssContent> getAll(){
		return rssContentDAO.getAll();
	}
	
	public List<RssContent> getAllByPage(ListParam listParam){
		if(listParam == null){
			listParam = new ListParam(0,1000,1);
		}
		return rssContentDAO.getAllByPage(listParam);
	}
	
	public List<RssContent> getAllByPageAndCategory(long category, ListParam listParam){
		if(listParam == null){
			listParam = new ListParam(0,100,1);
		}
		return rssContentDAO.getAllByPageAndCategory(category,listParam);
	}
}
