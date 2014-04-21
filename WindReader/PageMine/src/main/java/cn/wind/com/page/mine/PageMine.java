package cn.wind.com.page.mine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;

import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;

import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.xml.sax.SAXException;

import cn.wind.com.page.mine.model.Attribute;
import cn.wind.com.page.mine.model.Document;
import cn.wind.com.page.mine.model.NodeWrapper;
import cn.wind.com.page.mine.model.PlainContent;
import cn.wind.com.page.mine.model.SegmentWord;
import cn.wind.com.page.mine.model.TagBlock;
import cn.wind.com.page.mine.utils.HttpUtils;
import cn.wind.com.page.mine.utils.TagUtils;
import cn.wind.com.reader.biz.CategoryBiz;
import cn.wind.com.reader.biz.RssContentBiz;
import cn.wind.com.reader.factory.ReaderServiceFactory;
import cn.wind.com.reader.model.Category;
import cn.wind.com.reader.model.RssContent;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class PageMine {
	
	private static final Logger log = Logger.getLogger(PageMine.class);
	private static final int TIME_OUT=10000;
	private static final RssContentBiz rssContentBiz = ReaderServiceFactory.getRssContentBiz();
	private static final CategoryBiz categoryBiz = ReaderServiceFactory.getCategoryBiz();
	private static final String[] PUNCTUATION = new String[]{"(",")","（","）",",",".","?","'","\"","!",";",":","，","。","！","？","；","：","‘","“","”","’"};
	private NodeWrapper rootWrapper;
	
	public NodeWrapper getRootWrapper() {
		return rootWrapper;
	}

	public void setRootWrapper(NodeWrapper rootWrapper) {
		this.rootWrapper = rootWrapper;
	}

	
	public String filterContent(String content){
		String result = "";
		if(StringUtils.isNotEmpty(content)){
			result = content.replaceAll("&nbsp;", " ");
			result = result.replaceAll("\t", "");
		}
		return result.trim();
	}
	
	public void traverse(Node node,StringBuilder sb) throws UnsupportedEncodingException{
		if(node == null){
			return ;
		}
		if(node.getChildren() == null){
			return ;
		}
		NodeList nodeList = node.getChildren();
		for(int i = 0 ; i < nodeList.size(); i++){
			Node currentNode = nodeList.elementAt(i);
			if(currentNode instanceof ScriptTag){
				continue;
			}
			if(currentNode instanceof StyleTag){
				continue;
			}
			if(currentNode.getChildren() != null){
				traverse(currentNode,sb);
			}else{
				if(!"".equals(node.toPlainTextString().trim())){
					Node parent = currentNode.getParent();
					int contentLength = currentNode.toPlainTextString().trim().length();
					int htmlLength = parent.toHtml().trim().length();
					double tfRate = contentLength/(double)htmlLength;
					//double idfRate = Math.log(length/(double)contentLength);
					double rate = tfRate;
					if(rate > 0.6){
						if(HttpUtils.isChinese(currentNode.toPlainTextString().trim())){
							//log.debug("parent" + ":" + currentNode.getParent().toHtml().trim());
							if(currentNode.toHtml().trim().contains("@") || currentNode.toHtml().trim().toLowerCase().contains("copyright")){
								continue;
							}
							String content = filterContent(currentNode.toPlainTextString().trim());
							if(StringUtils.isNotEmpty(content)){
								sb.append(content + "\n");
							}
						}
					}
				}
			}
		}
	}
	
	private void traverse(NodeWrapper node){
		if(node == null){
			log.debug("Root node is null");
			return;
		}
		if(node.getChildren() == null){
			return;
		}
		List<NodeWrapper> nodeList = node.getChildren();
		log.debug(node.getTagBlock());
		
		for(int i = 0 ; i < nodeList.size(); i++){
			NodeWrapper currentNode = nodeList.get(i);
			if(currentNode.getChildren() != null){
				traverse(currentNode);
			}else{
				log.debug(node.getTagBlock());
			}
		}
	}
	
	
	private void traverse(Node node, NodeWrapper root){
		if(node == null){
			return;
		}
		if(node.getChildren() == null){
			return;
		}
		NodeList nodeList = node.getChildren();
		if(root == null){
			root = new NodeWrapper();
			root.setParent(null);
			root.setNode(node);
			rootWrapper = root;
		}
		Attribute attr = new Attribute();
		TagBlock tagBlock = new TagBlock();
		Node currentNode  = null;
		List<NodeWrapper> wrapperList = new ArrayList<NodeWrapper>();
		List<Node> nodes = new ArrayList<Node>();
		for(int i = 0 ; i < nodeList.size(); i++){
			currentNode = nodeList.elementAt(i);
			if(currentNode instanceof ScriptTag){
				continue;
			}
			if("".equals(currentNode.toHtml().trim())){
				continue;
			}
			TagUtils.setAttribute(currentNode, attr);
			TagUtils.setTagBlock(currentNode, tagBlock);
			NodeWrapper nodeWrapper = new NodeWrapper();
			nodeWrapper.setParent(root);
			nodeWrapper.setNode(currentNode);
			if(root.getChildren() == null){
				root.setChildren(new ArrayList<NodeWrapper>());
			}
			root.getChildren().add(nodeWrapper);
			wrapperList.add(nodeWrapper);
			nodes.add(currentNode);
		}
		
		tagBlock.setAttribute(attr);	
		root.setTagBlock(tagBlock);
		
		for(int i = 0 ; i < nodes.size(); i++){
			currentNode = nodes.get(i);
			if(currentNode.getChildren() != null){
				traverse(currentNode, wrapperList.get(i));
			}
		}
	}
	
	public double getPunctuationPercentage(String content){
		if(StringUtils.isEmpty(content)){
			return 0.0;
		}
		int index = 0;
		for(int i = 0 ; i < content.length() ; i++){
			for(int j = 0 ; j < PUNCTUATION.length; j++){
				if(PUNCTUATION[j].equals(String.valueOf(content.charAt(i)))){
					index++;
				}
			}
		}
		return (double)index/content.length();
	}
	
	/**
	 * 阈值法正文提取
	 * @param pc
	 * @return
	 * @throws ParserConfigurationException
	 * @throws UnsupportedEncodingException
	 */
	public PlainContent parseHtmlByHHF(PlainContent pc) throws ParserConfigurationException, UnsupportedEncodingException{
		if(pc == null){
			return null;
		}
		PlainContent result = new PlainContent(pc);
		Parser htmlParser = null;
		StringBuilder sb = new StringBuilder();
		try{
			htmlParser = new Parser();
			htmlParser.setInputHTML(pc.getContent());
			htmlParser.setEncoding(pc.getEncode());
			NodeFilter bodyFilter = new TagNameFilter("body");
			NodeFilter titleFilter = new TagNameFilter("title");
			NodeFilter metaFilter = new TagNameFilter("meta");
			NodeFilter filter = new OrFilter(metaFilter,new OrFilter(bodyFilter,titleFilter));
			NodeList nodes = htmlParser.extractAllNodesThatMatch(filter);
			if (nodes != null) {
				for (int i = 0; i < nodes.size(); i++) {
					Node node = nodes.elementAt(i);
					if(node instanceof TitleTag){
						log.debug("title:" +  filterContent(node.toPlainTextString()));
						result.setTitle(filterContent(node.toPlainTextString()));
					}else if(node instanceof MetaTag){
						MetaTag metaTag = (MetaTag)node;
						String name = metaTag.getAttribute("name");
						String value = metaTag.getAttribute("content");
						value = filterContent(value);
						if(name != null && "keywords".equals(name.toLowerCase())){
							log.debug("keywords:" + value);
							if(value != null){
								result.setKeywords(value.trim());
							}
						}else if(name != null && "description".equals(name.toLowerCase())){
							log.debug("description:" + value);
							if(value != null){
								result.setDescription(value.trim());
							}
						}
						
					}else{
						//traverse(node,rootWrapper);
						if(node instanceof BodyTag){
							int length = node.toPlainTextString().trim().length();
							log.debug("该网页纯文字总长度为： " + length);
						}
						traverse(node,sb);
						result.setContent(sb.toString());
					}
				}
			}
			log.info("------------------------------end----------------------------------");
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("【hhf】" + pc.getLink() + ":" + this.getPunctuationPercentage(result.getContent()));
		return result;
	}
	
	/**
	 * Bipe法正文提取
	 * @param pc
	 * @return
	 */
	public PlainContent parseHtmlByBipe(PlainContent pc){
		if(pc == null){
			return null;
		}
		PlainContent result = new PlainContent(pc);
		String content = "";
		try {
			content = ArticleExtractor.INSTANCE.getText(pc.getContent());
		} catch (BoilerpipeProcessingException e) {
			// TODO Auto-generated catch block
			log.error("boilerpipe process error:", e);
		}
		result.setContent(content);
		System.out.println("【bipe】" + pc.getLink() + ":" + this.getPunctuationPercentage(result.getContent()));
		return result;
	}
	
	/**
	 * 统计法正文提取
	 * @param pc
	 * @return
	 */
	public PlainContent parseHtmlByCx(PlainContent pc){
		if(pc == null){
			return null;
		}
		PlainContent result = new PlainContent(pc);
		TextExtractor te = new TextExtractor();
		te.extractHTML(pc.getContent());
		result.setContent(te.getText());
		if(StringUtils.isNotEmpty(te.getTitle())){
			result.setTitle(te.getTitle());
		}
		if(StringUtils.isNotEmpty(te.getDescription())){
			result.setDescription(te.getDescription());
		}
		if(StringUtils.isNotEmpty(te.getKeyWords())){
			result.setKeywords(te.getKeyWords());
		}
		System.out.println("【cx】" + pc.getLink() + ":" + this.getPunctuationPercentage(result.getContent()));
		return result;
	}
	
	public PlainContent parseHtml(PlainContent pc){
		PlainContent result = parseHtmlByCx(pc);
		double score = this.getPunctuationPercentage(result.getContent());
		if(result != null && score > 0.035){
			PlainContent temp = parseHtmlByBipe(pc);
			if(this.getPunctuationPercentage(temp.getContent()) > score){
				return temp;
			}else{
				return result;
			}
		}else{
			result = parseHtmlByBipe(pc);
			if(result != null && this.getPunctuationPercentage(result.getContent()) > 0.035){
				return result;
			}else{
				try {
					result = parseHtmlByHHF(pc);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result != null && this.getPunctuationPercentage(result.getContent()) > 0.02){
					return result;
				}
			}
		}
		return null;
	}
	
	
	public void segmentFile(File file,boolean isIk) throws FileNotFoundException, IOException{
		List<Document> list = new ArrayList<Document>();
		File[] concepts = file.listFiles();
		WordSegment ws = new WordSegment();
		for(File concept : concepts){
			System.out.println(concept.getAbsolutePath());
			File[] instances = concept.listFiles();
			for(File instance : instances){
				System.out.println(instance.getAbsolutePath());
				Document doc = ws.wordSegment(new InputStreamReader(new FileInputStream(instance)), isIk);
				File wordSegmentDir = new File("output" + File.separator + "sougou-wordsegment" + File.separator + concept.getName());
				if(!wordSegmentDir.exists()){
					wordSegmentDir.mkdirs();
				}
				doc.setFilePath(wordSegmentDir.getAbsolutePath() + File.separator + instance.getName());
				list.add(doc);
			}
			System.out.println("-----------------------------------");
		}
		for(Document doc : list){
			ws.calcuteTFIDF(doc);
			doc.sort();
			doc.persist();
		}
	}

	public void extractContent() throws ParserConfigurationException, IOException, BoilerpipeProcessingException, SAXException{
		List<Category> categoryList = categoryBiz.getAllSuperCategory();
		for(Category category : categoryList){
			List<Category> subCategoryList = categoryBiz.getAllSubCategory(category);
			for(Category subCategory : subCategoryList){
				List<RssContent> contentList = rssContentBiz.getAllByPageAndCategory(subCategory.getId(), null);
				int index = 0;
				for(RssContent rssContent: contentList){
					log.debug(rssContent.getId() + ":" + rssContent.getTitle() + ":" + rssContent.getLink());
					PlainContent pc = HttpUtils.getHtmlContent(rssContent.getLink(), TIME_OUT);
					if(pc != null){
						log.debug("HHF parser:");
						long start = System.currentTimeMillis();
						PlainContent result = this.parseHtmlByHHF(pc);
						long end = System.currentTimeMillis();
						log.debug("cost time:" + (end-start) + "ms");
						if(result != null){
							File contentDir = new File("output" + File.separator + "content" + File.separator+ "HHF" + File.separator + category.getTitle());
							if(!contentDir.exists()){
								contentDir.mkdirs();
							}
							result.persist(contentDir.getAbsolutePath() + File.separator + rssContent.getId() + ".txt");
						}
						
						log.debug("Bipe parser:");
						start = System.currentTimeMillis();
						result = parseHtmlByBipe(pc);
						end = System.currentTimeMillis();
						log.debug("cost time:" + (end-start) + "ms");
						if(result != null){
							File contentDir = new File("output" + File.separator + "content" + File.separator+ "Bipe" + File.separator + category.getTitle());
							if(!contentDir.exists()){
								contentDir.mkdirs();
							}
							result.persist(contentDir.getAbsolutePath() + File.separator + rssContent.getId() + ".txt");
						}
						
						log.debug("Cx parser:");
						start = System.currentTimeMillis();
						result = parseHtmlByCx(pc);
						end = System.currentTimeMillis();
						log.debug("cost time:" + (end-start) + "ms");
						if(result != null){
							File contentDir = new File("output" + File.separator + "content" + File.separator+ "Cx" + File.separator + category.getTitle());
							if(!contentDir.exists()){
								contentDir.mkdirs();
							}
							result.persist(contentDir.getAbsolutePath() + File.separator + rssContent.getId() + ".txt");
						}
					}
					
					log.debug("------------------------------------------------------------");
					if(((++index) % 10) == 0){
						break;
					}
				}
			}
			
			
		}
	}
	

	public void extractContentAndSegment() throws ParserConfigurationException, IOException{
		Map<String,Integer> map = new HashMap<String,Integer>();
		List<Category> categoryList = categoryBiz.getAllSuperCategory();
		WordSegment ws = new WordSegment();
		List<Document> list  = new ArrayList<Document>();
		for(Category category : categoryList){
			if(!category.getTitle().equals("科技")){
				continue;
			}
			List<Category> subCategoryList = categoryBiz.getAllSubCategory(category);
			for(Category subCategory : subCategoryList){
				if(!subCategory.getTitle().equals("互联网")){
					continue;
				}
				List<RssContent> contentList = rssContentBiz.getAllByPageAndCategory(subCategory.getId(), null);
				int index = 0;
				for(RssContent rssContent: contentList){
					PlainContent pc = HttpUtils.getHtmlContent(rssContent.getLink(), TIME_OUT);
					PlainContent result = this.parseHtmlByCx(pc);
					log.debug(rssContent.getId() + ":" + rssContent.getTitle() + ":" + rssContent.getLink());
					if(result != null){
						File contentDir = new File("internet" + File.separator + "content" + File.separator + category.getTitle());
						if(!contentDir.exists()){
							contentDir.mkdirs();
						}
						result.persist(contentDir.getAbsolutePath() + File.separator + rssContent.getId() + ".txt");
						
						Document doc = ws.wordSegment(result,true);
						File wordSegmentDir = new File("internet" + File.separator + "wordsegment" + File.separator + category.getTitle());
						if(!wordSegmentDir.exists()){
							wordSegmentDir.mkdirs();
						}
						doc.setFilePath(wordSegmentDir.getAbsolutePath() + File.separator + rssContent.getId() + ".txt");
						list.add(doc);
						
					}
					log.debug("------------------------------------------------------------");
//					if(((++index) % 10) == 0){
//						break;
//					}
				}
			}
		}
		List<SegmentWord> words = new ArrayList<SegmentWord>();
		for(Document doc : list){
			ws.calcuteTFIDF(doc);
			doc.persist();
			int index = 0;
			for(SegmentWord sw : doc.getSegmentWords()){
				index++;
				if(sw.getTerm().length() < 2){
					continue;
				}
				if(index >= doc.getSegmentWords().length*0.8){
					break;
				}
				if(map.get(sw.getTerm()) == null){
					map.put(sw.getTerm(), 1);
				}else{
					map.put(sw.getTerm(),map.get(sw.getTerm()) + 1);
				}
			}
		}
		System.out.println("Total " + list.size() + " documents.");
		for(Entry<String,Integer> entry : map.entrySet()){
			SegmentWord sw = new SegmentWord();
			sw.setTerm(entry.getKey());
			sw.setWeight(entry.getValue());
			words.add(sw);
		}
		Collections.sort(words);
		for(SegmentWord sw : words){
			System.out.println(sw.toString());
		}
	}
	
	
	public static void main(String[] args) throws ParserConfigurationException, IOException, BoilerpipeProcessingException, SAXException{

//		long start = System.currentTimeMillis();
//		PageMine pm = new PageMine();
//		pm.segmentFile(new File("output/sougou-content/"),false);
//		long end = System.currentTimeMillis();
//		log.debug((end-start) + "ms");
		
//		String url = "http://society.dbw.cn/system/2014/01/20/055430686.shtml";
//		TextExtractor te = new TextExtractor();
//		te.extractURL(url);
//		PageMine pm = new PageMine();
//		System.out.println(pm.parseHtmlByBipe(url));
//		System.out.println("---------------------------------------------------------");
//		System.out.println(pm.parseHtmlByHHF(url));
//		System.out.println("---------------------------------------------------------");
//		String content = getHTML(url);
//		System.out.println(te.getText());
		
//		PlainContent pc = HttpUtils.getHtmlContent("http://tech.163.com/13/0728/11/94SAVM6D000915BE.html", 10000);
//		PageMine pm = new PageMine();
//		pm.parseHtmlByCx(pc);
//		System.out.println(pc);
		
//		PageMine pm = new PageMine();
//		PlainContent pc = HttpUtils.getHtmlContent("http://rss.feedsportal.com/c/33390/f/628983/p/1/s/c7d048f/l/0Lnews0B1630N0C140C0A120A0C190C9J2BVQEM0A0A0A1124J0Bhtml/story01.htm", TIME_OUT);
//		PlainContent result = pm.parseHtmlByCx(pc);
//		System.out.println(result);
		
//		PageMine pm = new PageMine();
//		pm.extractContentAndSegment();
		
//		PlainContent pc = HttpUtils.getHtmlContent("http://www.1396me.com", 10000);
//		PageMine pm = new PageMine();
//		PlainContent result = pm.parseHtmlByCx(pc);
//		System.out.println(result);
		
		PageMine pm = new PageMine();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/home/eric/browser_data")));
		String line = null;
		int i = 0;
		WordSegment ws = new WordSegment();
		Map<String,List<Document>> userAccessRecord = new LinkedHashMap<String,List<Document>>();
		while((line = br.readLine()) != null){
			String[] array = line.split("\t");
			int index = 0;
			String imei = "";
			for(String str : array){
				if(index == 0){ 
					List<Document> list = new ArrayList<Document>();
					imei=str;
					userAccessRecord.put(imei, list);
				}else{
					PlainContent pc = HttpUtils.getHtmlContent(str, 10000);
					PlainContent result = pm.parseHtmlByCx(pc);
					System.out.println(result);
					String words = "";
					if(result != null){
						if(StringUtils.isEmpty(result.getDescription()) && StringUtils.isEmpty(result.getKeywords()) && StringUtils.isEmpty(result.getTitle())){
							if(StringUtils.isNotEmpty(result.getContent())){
								words = result.getContent();
							}
						}else{
							if(StringUtils.isNotEmpty(result.getTitle())){
								words = words + result.getTitle();
							}
							if(StringUtils.isNotEmpty(result.getKeywords())){
								words = words + result.getKeywords();
							}
							if(StringUtils.isNotEmpty(result.getDescription())){
								words = words + result.getDescription();
							}
						}
						if(StringUtils.isNotEmpty(words)){
							Document doc = ws.wordSegment(words, false);
							Arrays.sort(doc.getSegmentWords());
							userAccessRecord.get(imei).add(doc);
						}
					}
				}
				index++;
			}
			i++;
			if(i == 100){
				break;
			}
		}
		br.close();
		for(Entry<String,List<Document>> entry : userAccessRecord.entrySet()){
			Map<String,Integer> map = new HashMap<String,Integer>();
			int index = 0;
			System.out.println(entry.getKey()+"-------------------");
			for(Document doc : entry.getValue()){
				index++;
				System.out.println("doc " + index);
				for(SegmentWord sw : doc.getSegmentWords()){
//					if(map.get(sw.getTerm()) == null){
//						map.put(sw.getTerm(), 1);
//					}else{
//						map.put(sw.getTerm(), map.get(sw.getTerm())+1);
//					}
					System.out.print(sw.getTerm() + ":" + sw.getWeight() + "|");
				}
				System.out.println("");
			}
		}
		
	}
}
