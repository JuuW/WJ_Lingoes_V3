package cn.wj.lingoes.v2.utility;

import java.awt.Container;
import java.util.HashMap;

public class ContainerList {
	
	
	
	private HashMap<String, Container> conList;
	
	public ContainerList() {
		conList =  new HashMap<String, Container>();
		// TODO Auto-generated constructor stub
	}
	public void addContainer(String Key, Container Con) {
		conList.put(Key, Con);
	}
	public Container getContainer(String Key) {
		return conList.get(Key);
	}
}
