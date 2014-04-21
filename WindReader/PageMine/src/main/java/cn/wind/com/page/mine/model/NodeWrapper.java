package cn.wind.com.page.mine.model;
import java.util.List;

import org.htmlparser.Node;

public class NodeWrapper {
	private Node node;
	private TagBlock tagBlock;
	private NodeWrapper parent;
	private List<NodeWrapper> children;
	
	
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	public TagBlock getTagBlock() {
		return tagBlock;
	}
	public void setTagBlock(TagBlock tagBlock) {
		this.tagBlock = tagBlock;
	}
	public NodeWrapper getParent() {
		return parent;
	}
	public void setParent(NodeWrapper parent) {
		this.parent = parent;
	}
	public List<NodeWrapper> getChildren() {
		return children;
	}
	public void setChildren(List<NodeWrapper> children) {
		this.children = children;
	}
	
	
}
