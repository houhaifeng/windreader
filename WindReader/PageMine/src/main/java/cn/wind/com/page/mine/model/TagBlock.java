package cn.wind.com.page.mine.model;

import java.util.List;

public class TagBlock {
	private TagType tagType;
	private Attribute attribute;
	private List<String> linkList;
	private List<String> weightyTagList;
	
	public TagType getTagType() {
		return tagType;
	}
	public void setTagType(TagType tagType) {
		this.tagType = tagType;
	}
	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	public List<String> getLinkList() {
		return linkList;
	}
	public void setLinkList(List<String> linkList) {
		this.linkList = linkList;
	}
	public List<String> getWeightyTagList() {
		return weightyTagList;
	}
	public void setWeightyTagList(List<String> weightyTagList) {
		this.weightyTagList = weightyTagList;
	}
	
	@Override
	public String toString() {
		return "TagBlock [tagType=" + tagType + ", attribute=" + attribute
				+ ", linkList=" + linkList + ", weightyTagList="
				+ weightyTagList + "]";
	}
	
}
