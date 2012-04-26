package net.thesocialos.client.helper;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.Command;

public class Node {
	
	String nodeName;
	
	List<Node> childs;
	
	Command command;
	
	public Node() {
		
	}
	
	public Node(String nodeName, List<Node> childs) {
		this.nodeName = nodeName;
		this.childs = childs;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public String getName() {
		return nodeName;
	}
	
	public Iterator<Node> getNodeIterator() {
		return childs.iterator();
	}
}
