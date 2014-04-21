package cn.wind.com.page.mine.algrithm;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import cn.wind.com.page.mine.PageMine;
import cn.wind.com.page.mine.WordSegment;
import cn.wind.com.page.mine.model.Document;
import cn.wind.com.page.mine.model.PlainContent;
import cn.wind.com.page.mine.model.SegmentWord;
import cn.wind.com.page.mine.utils.HttpUtils;

public class SimHash {
	public SegmentWord[] getFeatures(PlainContent pc) throws IOException{
		WordSegment ws = new WordSegment();
		Document doc = ws.mmWordSegment(pc.getContent());
		return doc.getSegmentWords();
	}
	
	public long murmurHash64B(String key,int seed){
		char[] array = key.toCharArray();
		int len = array.length;
		int m = 0x5bd1e995;
		int r = 24;
	 
		int h1 = seed ^ len;
		int h2 = 0;
		
		int index = 0;
		
		while(len >= 8)
		{
			int k1 = array[index++];
			k1 *= m; k1 ^= k1 >> r; k1 *= m;
			h1 *= m; h1 ^= k1;
			len -= 4;
	 
			int k2 = array[index++];
			k2 *= m; k2 ^= k2 >> r; k2 *= m;
			h2 *= m; h2 ^= k2;
			len -= 4;
		}
	 
		if(len >= 4)
		{
			int k1 = array[index++];
			k1 *= m; k1 ^= k1 >> r; k1 *= m;
			h1 *= m; h1 ^= k1;
			len -= 4;
		}
	 
		switch(len)
		{
		case 3: h2 ^= array[2] << 16;
		case 2: h2 ^= array[1] << 8;
		case 1: h2 ^= array[0];
				h2 *= m;
		};
	 
		h1 ^= h2 >> 18; h1 *= m;
		h2 ^= h1 >> 22; h2 *= m;
		h1 ^= h2 >> 17; h1 *= m;
		h2 ^= h1 >> 19; h2 *= m;
	 
		long h = h1;
	 
		h = (h << 32) | h2;
		
		return h;
	}
	
	public String convertHexToString(long h){
		String result = Long.toBinaryString(h);
		StringBuffer sb = new StringBuffer();
		if(result.length() < 64){
			for(int i = 0 ; i < (64-result.length()); i++){
				sb.append("0");
			}
		}
		return sb.toString() + result;
	}
	
	public Long convertStringToLong(String value){
		BigInteger bi = new BigInteger(value,2);
		return bi.longValue();
	}
	
	
	public String simHash(SegmentWord[] segmentWords){
		StringBuffer result = new StringBuffer();
		Double[][] weightArray = new Double[segmentWords.length][64];
		for(int i = 0 ; i < segmentWords.length; i++){
			String mmHash = convertHexToString(murmurHash64B(segmentWords[i].getTerm(),17));
			char[] charArray = mmHash.toCharArray();

			for(int j = 0; j < charArray.length; j++){
				if(charArray[j] == '0'){
					weightArray[i][j] = -segmentWords[i].getWeight();
				}else{
					weightArray[i][j] = segmentWords[i].getWeight();
				}
			}
		}
		
		for(int i = 0; i < 64; i++){
			double sum = 0.0;
			for(int j = 0; j < segmentWords.length; j++){
				sum += weightArray[j][i];
			}
			if(sum > 0){
				result.append(1);
			}else{
				result.append(0);
			}
		}
		return result.toString();
	}
	
	public int getHaiMingDis(String simHash1, String simHash2){
		int dis = 0;
		for(int i = 0; i < 64; i++){
			if(simHash1.charAt(i) == simHash2.charAt(i)){
				continue;
			}
			dis++;
		}
		return dis;
	}
	public static void main(String[] args) throws IOException{
		SimHash simHash = new SimHash();
		PlainContent pc1= HttpUtils.getHtmlContent("http://tech2ipo.com/63383", 10000);
		PlainContent pc2= HttpUtils.getHtmlContent("http://www.cnbeta.com/articles/272368.htm", 10000);
		PageMine pm = new PageMine();
		pc1 = pm.parseHtmlByCx(pc1);
		pc2 = pm.parseHtmlByCx(pc2);
		System.out.println(pc1.getContent());
		System.out.println(pc2.getContent());
		WordSegment ws = new WordSegment();
		Document doc1 = ws.mmWordSegment(pc1.getContent());
		Document doc2 = ws.mmWordSegment(pc2.getContent());
		doc1.filter();
		doc2.filter();
//		doc1.sort();
//		doc2.sort();
		System.out.println(doc2.getSegmentWords().length);
		String hash1 = simHash.simHash(doc1.getSegmentWords());
		String hash2 = simHash.simHash(doc2.getSegmentWords());
		System.out.println(hash1);
		System.out.println(hash2);
		System.out.println(simHash.convertStringToLong(hash1));
		System.out.println(simHash.convertStringToLong(hash2));
		System.out.println(simHash.getHaiMingDis(hash1, hash2));
		
	}
}
