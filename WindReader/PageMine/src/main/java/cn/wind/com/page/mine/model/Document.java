package cn.wind.com.page.mine.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Document {
	private long id;
	private String filePath;
	private SegmentWord[] segmentWords;
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void filter(){
		List<SegmentWord> list = new ArrayList<SegmentWord>();
		for(int i = 0 ; i < segmentWords.length; i++){
			if(segmentWords[i].getTerm().length() > 1){
				list.add(segmentWords[i]);
			}
		}
		segmentWords = list.toArray(new SegmentWord[list.size()]);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SegmentWord[] getSegmentWords() {
		return segmentWords;
	}
	public void setSegmentWords(SegmentWord[] segmentWords) {
		this.segmentWords = segmentWords;
	}
	
	public void sort(){
		Arrays.sort(segmentWords);
	}
	
	public void persist(){
		this.persist(new File(filePath));
	}
	
	public void persist(File file){
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			for(SegmentWord word : this.getSegmentWords()){
				bw.write(word.toString());
				bw.newLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(bw != null){
					bw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				bw = null;
			}
		}
		
		
	}
}
