package cn.wind.com.page.mine.model;

public class SegmentWord implements Comparable{
	private String term;
	private Double weight;
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		SegmentWord sw = (SegmentWord)arg0;
		return sw.getWeight().compareTo(weight);
	}
	@Override
	public String toString() {
		return term + ":" + weight;
	}
	
}
