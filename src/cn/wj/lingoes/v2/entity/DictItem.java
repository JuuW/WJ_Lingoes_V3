package cn.wj.lingoes.v2.entity;

public class DictItem {
	private String dictName;
	private String dictWord;
	private String dictValue;

	public DictItem(String dictName, String dictWord, String dictValue) {
		this.dictName = dictName;
		this.dictWord = dictWord;
		this.dictValue = dictValue;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictWord() {
		return dictWord;
	}

	public void setDictWord(String dictWord) {
		this.dictWord = dictWord;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	
	

}
