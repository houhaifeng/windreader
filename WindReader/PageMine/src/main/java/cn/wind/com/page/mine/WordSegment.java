package cn.wind.com.page.mine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.wind.com.page.mine.model.Document;
import cn.wind.com.page.mine.model.PlainContent;
import cn.wind.com.page.mine.model.SegmentWord;

import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Word;

/**
 * IK，MM分词
 * @author eric
 *
 */
public class WordSegment {
	
	private static Set<String> stopWords = new HashSet<String>();
	private static Map<String,Integer> IDFMap = new HashMap<String,Integer>();
	private static Dictionary dic = Dictionary.getInstance();
	private static Seg seg = new ComplexSeg(dic);//取得不同的分词具体算法
	private static Analyzer analyzer = new IKAnalyzer();
	private static int TOTAL = 0;
	static{
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(new FileInputStream("ext_stopword.dic")));
			String line = "";
			while((line = br.readLine()) != null){
				stopWords.add(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					br = null;
				}
			}
		}
	}
	
	private void increase(Entry<String,Integer> entry){
		if(IDFMap.get(entry.getKey()) == null){
			IDFMap.put(entry.getKey(), 1);
		}else{
			IDFMap.put(entry.getKey(), IDFMap.get(entry.getKey())+1);
		}
	}
	
	public Document mmWordSegment(Reader reader) throws IOException{
		Document doc = null;
		List<SegmentWord> list = new ArrayList<SegmentWord>();
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		if(reader != null){
			doc = new Document();
			
			MMSeg mmSeg = new MMSeg(reader, seg);
			Word word = null;
			while((word=mmSeg.next())!=null) {
				if(stopWords.contains(word.toString())){
					continue;
				}
				if(map.containsKey(word.toString())){
					map.put(word.toString(), map.get(word.toString()) + 1);
				}else{
					map.put(word.toString(), 1);
				}
			}
			for(Entry<String,Integer> entry : map.entrySet()){
				SegmentWord segmentWord = new SegmentWord();
				segmentWord.setTerm(entry.getKey());
				segmentWord.setWeight(entry.getValue());
				list.add(segmentWord);
				increase(entry);
			}
			reader.close();
			doc.setSegmentWords(list.toArray(new SegmentWord[list.size()]));
			TOTAL++;
		}
		return doc;
	}
	

	public Document mmWordSegment(String words) throws IOException{
		StringReader reader = new StringReader(words);
		return this.mmWordSegment(reader);
	}

	public Document ikWordSegment(Reader reader) throws IOException{
		Document doc = null;
		List<SegmentWord> list = new ArrayList<SegmentWord>();
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		if(reader != null){
			doc = new Document();
			TokenStream ts = analyzer.tokenStream("", reader);
			CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
			while(ts.incrementToken()){
				if(map.containsKey(term.toString())){
					map.put(term.toString(), map.get(term.toString()) + 1);
				}else{
					map.put(term.toString(), 1);
				}
			}
			
			for(Entry<String,Integer> entry : map.entrySet()){
				SegmentWord segmentWord = new SegmentWord();
				segmentWord.setTerm(entry.getKey());
				segmentWord.setWeight(entry.getValue());
				list.add(segmentWord);
				this.increase(entry);
			}
			reader.close();
			doc.setSegmentWords(list.toArray(new SegmentWord[list.size()]));
			TOTAL++;
		}
		return doc;
	}
	
	public Document wordSegment(String words,boolean isIK) throws IOException{
		StringReader reader = new StringReader(words);
		return this.wordSegment(reader, isIK);
	}
	
	public Document wordSegment(PlainContent pc,boolean isIK) throws IOException{
		return this.wordSegment(pc.getContent(), isIK);
	}
	
	public Document wordSegment(Reader reader,boolean isIK) throws IOException{
		if(isIK){
			return this.ikWordSegment(reader);
		}else{
			return this.mmWordSegment(reader);
		}
	}
	
	public void calcuteTFIDF(Document doc){
		int sum = 0;
		if(doc != null){
			for(SegmentWord sw : doc.getSegmentWords()){
				sum += sw.getWeight();
			}
			for(SegmentWord sw : doc.getSegmentWords()){
				sw.setWeight(((double)sw.getWeight()/sum) * ((double)Math.log((TOTAL/IDFMap.get(sw.getTerm())))));
			}
			doc.sort();
		}
	}
	
	public void printDocument(Document doc){
		if(doc != null){
			StringBuilder sb = new StringBuilder();
			for(SegmentWord sw : doc.getSegmentWords()){
				sb.append(sw.getTerm() + ":" + sw.getWeight() + "|");
			}
			System.out.println(sb.toString());
		}
	}
	
}
