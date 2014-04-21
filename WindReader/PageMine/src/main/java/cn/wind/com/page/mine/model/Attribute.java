package cn.wind.com.page.mine.model;

public class Attribute {
	private double weight;
	private int width;
	private int align;
	private int word_sum;
	private int link_sum;
	private int weighty_tag_sum;
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getAlign() {
		return align;
	}
	public void setAlign(int align) {
		this.align = align;
	}
	public int getWord_sum() {
		return word_sum;
	}
	public void setWord_sum(int word_sum) {
		this.word_sum = word_sum;
	}
	public int getLink_sum() {
		return link_sum;
	}
	public void setLink_sum(int link_sum) {
		this.link_sum = link_sum;
	}
	public int getWeighty_tag_sum() {
		return weighty_tag_sum;
	}
	public void setWeighty_tag_sum(int weighty_tag_sum) {
		this.weighty_tag_sum = weighty_tag_sum;
	}
	
	@Override
	public String toString() {
		return "Attribute [weight=" + weight + ", width=" + width + ", align="
				+ align + ", word_sum=" + word_sum + ", link_sum=" + link_sum
				+ ", weighty_tag_sum=" + weighty_tag_sum + "]";
	}
	
	
}
