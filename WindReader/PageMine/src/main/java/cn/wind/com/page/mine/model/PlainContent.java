package cn.wind.com.page.mine.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class PlainContent {
	private String title;
	private String encode;
	private String description;
	private String keywords;
	private String content;
	private String link;
	private String image;
	
	
	public PlainContent() {
		super();
	}
	
	public PlainContent(PlainContent pc) {
		this.title = pc.getTitle();
		this.encode = pc.getEncode();
		this.description = pc.getDescription();
		this.keywords = pc.getKeywords();
		this.content = pc.getContent();
		this.link = pc.getLink();
		this.image = pc.getImage();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "title:" + title + "\n" 
	            + "encode:" + encode + "\n" 
	            + "keywords:" + keywords + "\n"
				+ "description:" + description + "\n" 
	            + "link:" + link + "\n"
	            + "image:" + image + "\n"
				+ "content:" + content;
	}
	
	public void persist(String fileName){
		this.persist(new File(fileName));
	}
	
	public void persist(File file){
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			bw.write(this.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					bw = null;
				}
			}
		}
	}
}
