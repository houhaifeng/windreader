package cn.wind.com.page.mine.utils;

import java.util.ArrayList;

import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;

import cn.wind.com.page.mine.model.Attribute;
import cn.wind.com.page.mine.model.TagBlock;

public class TagUtils {
	public static  boolean isWeightedTag(Node node){
		if(node instanceof Tag){
			Tag tag= (Tag)node;
			if("b".equals(tag.getTagName())){
				return true;
			}else if("strong".equals(tag.getTagName())){
			    return true;	
			}else if("h1".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("h2".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("h3".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("h4".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("h5".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("h6".equals(tag.getTagName().toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isContainerTag(Node node){
		if(node instanceof Tag){
			Tag tag = (Tag)node;
			if("table".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("tr".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("td".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("p".equals(tag.getTagName().toLowerCase())){
				System.out.println(node.toHtml());
				return true;
			}else if("div".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("span".equals(tag.getTagName().toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isContentTag(Node node){
		if(node instanceof Tag){
			Tag tag = (Tag)node;
			if("p".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("span".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("img".equals(tag.getTagName().toLowerCase())){
				return true;
			}else if("strong".equals(tag.getTagName().toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	public static void setAttribute(Node node,Attribute attr){
		if(node instanceof LinkTag){
			attr.setLink_sum(attr.getLink_sum() + 1);
		}else if(node instanceof TextNode){
			TextNode textNode = (TextNode)node;
			attr.setWord_sum(attr.getWord_sum() + textNode.getText().length());
		}else if(TagUtils.isWeightedTag(node)){
			attr.setWeighty_tag_sum(attr.getWeighty_tag_sum() + 1);
		}
	}
	
	public static void setTagBlock(Node node,TagBlock tagBlock){
		if(node instanceof LinkTag){
			LinkTag linkTag = (LinkTag)node;
			if(tagBlock.getLinkList() == null){
				tagBlock.setLinkList(new ArrayList<String>());
			}
			tagBlock.getLinkList().add(linkTag.getAttribute("href"));
		}else if(TagUtils.isWeightedTag(node)){
			if(tagBlock.getWeightyTagList() == null){
				tagBlock.setWeightyTagList(new ArrayList<String>());
			}
			Tag tag = (Tag)node;
			tagBlock.getWeightyTagList().add(tag.getTagName());
		}
	}
}
