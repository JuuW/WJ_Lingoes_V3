package cn.wj.lingoes.v2.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dict {

	public final static String DICT_LD2 = "ld2";
	public final static String DICT_TXT = "txt";
	public final static char FLAG_YES = 'Y';
	public final static char FLAG_NO = 'N';

	private String name;
	private int sequence;
	private int id;
	private String type;
	private char isDisplay;
	private char isIndex;
	private char isDefaultIndex;
	private ArrayList<Object> dictindex;
	private String filePath;
	private String fromLang;

	public Dict() {

		dictindex = new ArrayList<Object>();
	}
	
	public Dict(String n) {

		this.name = n;

		if (n.endsWith(".txt")) {
			type = Dict.DICT_TXT;
		} else if (n.endsWith(".ld2")) {
			type = Dict.DICT_LD2;
		}

		dictindex = new ArrayList<Object>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public char getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(char isDisplay) {
		this.isDisplay = isDisplay;
	}

	public ArrayList<Object> getDictindex() {
		return dictindex;
	}

	public void setDictindex(ArrayList<Object> dictindex) {
		this.dictindex = dictindex;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public char getIsIndex() {
		return isIndex;
	}

	public void setIsIndex(char isIndex) {
		this.isIndex = isIndex;
	}

	public char getIsDefaultIndex() {
		return isDefaultIndex;
	}

	public void setIsDefaultIndex(char isDefaultIndex) {
		this.isDefaultIndex = isDefaultIndex;
	}

	

	

}
