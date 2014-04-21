package cn.wind.com.reader.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;

import cn.wind.com.page.mine.PageMine;
import cn.wind.com.page.mine.WordSegment;
import cn.wind.com.page.mine.algrithm.SimHash;
import cn.wind.com.page.mine.model.Document;
import cn.wind.com.page.mine.model.PlainContent;
import cn.wind.com.page.mine.utils.HttpUtils;
import cn.wind.com.reader.biz.RssContentBiz;
import cn.wind.com.reader.factory.ReaderServiceFactory;
import cn.wind.com.reader.model.ListParam;
import cn.wind.com.reader.model.RssContent;

public class UniqueWebPage {
	private static RssContentBiz rssContentBiz = ReaderServiceFactory.getRssContentBiz();
	private PageMine pm = new PageMine();
	private WordSegment ws = new WordSegment();
	private SimHash simHash = new SimHash();
	class HashDocument{
		Long docId;
		String link;
		String hashcode;
		
		public HashDocument(Long docId, String hashcode, String link) {
			super();
			this.docId = docId;
			this.hashcode = hashcode;
			this.link = link;
		}
		
		public Long getDocId() {
			return docId;
		}
		public void setDocId(Long docId) {
			this.docId = docId;
		}
		public String getHashcode() {
			return hashcode;
		}
		public void setHashcode(String hashcode) {
			this.hashcode = hashcode;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		@Override
		public String toString() {
			return "HashDocument [docId=" + docId + ", link=" + link
					+ ", hashcode=" + hashcode + "]";
		}
		
	}
	
	class KeyValue{
		private long first;
		private long second;
		public long getFirst() {
			return first;
		}
		public void setFirst(long first) {
			this.first = first;
		}
		public long getSecond() {
			return second;
		}
		public void setSecond(long second) {
			this.second = second;
		}
		
		public KeyValue(long first, long second) {
			super();
			this.first = first;
			this.second = second;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + (int) (first ^ (first >>> 32));
			result = prime * result + (int) (second ^ (second >>> 32));
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			KeyValue other = (KeyValue) obj;
			if(first == other.first && second == other.second){
				return true;
			}
			if(first == other.second && second == other.first){
				return true;
			}
			return false;
		}
		
		private UniqueWebPage getOuterType() {
			return UniqueWebPage.this;
		}
		
	}
	public Map<String, List<HashDocument>> generateSimHash() throws IOException{
		Map<String, List<HashDocument>> map = new ConcurrentHashMap<String, List<HashDocument>>();
		ListParam param = new ListParam(0,Integer.MAX_VALUE,1);
		List<RssContent> list = rssContentBiz.getAllByPage(param);
		int index = 0;
		for(RssContent content : list){
			System.out.println((index++) + ":" + content.getLink());
			PlainContent pc = HttpUtils.getHtmlContent(content.getLink(), 10000);
			if(pc != null){
				pc = pm.parseHtml(pc);
				if(pc != null && StringUtils.isNotEmpty(pc.getContent())){
					Document doc = ws.mmWordSegment(pc.getContent());
					doc.filter();
					String hash = simHash.simHash(doc.getSegmentWords());
					content.setHash(hash);
					System.out.println(content.getId() + ":" + hash);
					rssContentBiz.update(content);
					for(int i = 0; i < 4; i++){
						String arrange = hash.substring(i*16, (i+1)*16);
						String key = i + "-" + arrange;
						if(!map.containsKey(key)){
							List<HashDocument> documents = new ArrayList<HashDocument>();
							map.put(key, documents);
						}
						map.get(key).add(new HashDocument(content.getId(),hash,content.getLink()));
					}
				}
			}
		}
		return map;
	}
	
	public Set<KeyValue> getNeighborDocument(Map<String, List<HashDocument>> map){
		Set<KeyValue> set = new HashSet<KeyValue>();
		if(map != null && map.size() > 0){
			for(Entry<String,List<HashDocument>> entry : map.entrySet()){
				for(int i = 0; i < entry.getValue().size(); i++){
					for(int j = i+1 ; j < entry.getValue().size(); j++){
						if(simHash.getHaiMingDis(entry.getValue().get(i).getHashcode(), entry.getValue().get(j).getHashcode()) <= 3){
							KeyValue keyValue = new KeyValue(entry.getValue().get(i).getDocId(),entry.getValue().get(j).getDocId());
							if(!set.contains(keyValue)){
								set.add(keyValue);
								System.out.println(entry.getValue().get(i).getDocId() + ":" + entry.getValue().get(j).getDocId());
								System.out.println(entry.getValue().get(i).getLink() + ":" + entry.getValue().get(j).getLink());
							}
						}
					}
				}
			}
		}
		return set;
	}
	
	public void compare(String url1,String url2) throws IOException{
		PlainContent pc1 = HttpUtils.getHtmlContent(url1, 10000);
		PlainContent pc2 = HttpUtils.getHtmlContent(url2, 10000);
		pc1 = pm.parseHtml(pc1);
		pc2 = pm.parseHtml(pc2);
		System.out.println(pc1.getContent());
		System.out.println(pc2.getContent());
		Document doc1 = ws.mmWordSegment(pc1.getContent());
		Document doc2 = ws.mmWordSegment(pc2.getContent());
		doc1.filter();
		doc2.filter();
		String hash1 = simHash.simHash(doc1.getSegmentWords());
		String hash2 = simHash.simHash(doc2.getSegmentWords());
		System.out.println(hash1);
		System.out.println(hash2);
		System.out.println(simHash.getHaiMingDis(hash1, hash2));
	}
	
	public static void main(String[] args) throws IOException, ParserConfigurationException{
		UniqueWebPage uwp = new UniqueWebPage();
		Map<String, List<HashDocument>> map = uwp.generateSimHash();
		for(Entry<String,List<HashDocument>> entry : map.entrySet()){
			System.out.println(entry.getKey() + ":");
			for(HashDocument doc : entry.getValue()){
				System.out.println(doc);
			}
			System.out.println("------------------------------------");
		}
		uwp.getNeighborDocument(map);
		
//		uwp.compare("http://world.people.com.cn/n/2014/0214/c157278-24365002.html", "http://chinese.people.com.cn/n/2014/0214/c42309-24364811.html");
	}
}
