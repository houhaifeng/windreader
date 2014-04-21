package cn.wind.com.observer;

import java.util.ArrayList;
import java.util.List;

import cn.wind.com.parser.Parser;

public abstract class Observer {
	private List<Parser> parsers = new ArrayList<Parser>();
	public void addParser(Parser parser){
		parsers.add(parser);
	}
	public void removeParser(Parser parser){
		parsers.remove(parser);
	}
	public void updateAll(){
		for(Parser parser : parsers){
			update(parser);
		}
	}
	public abstract void update(Parser parser);
}
