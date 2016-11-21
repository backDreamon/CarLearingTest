package kr.hdd.carleamingTest.common.data;

public class ListToJsondata {
	private String ListContent;

	public String getListContent() {
		return ListContent;
	}

	public void setListContent(String listContent) {
		ListContent = listContent;
	}
	
	@Override
	public String toString(){
		return ListContent + "";
	}
}
