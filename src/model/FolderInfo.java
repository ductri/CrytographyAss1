package model;

import constanst.Algorithm;

public class FolderInfo {
	String url;
	int size; //byte
	Algorithm al;
	public FolderInfo(String url, int size, Algorithm al) {
		super();
		this.url = url;
		this.size = size;
		this.al = al;
	}
	public String getUrl() {
		return url;
	}
	public String getSize() {
		return Integer.toString(size);
	}
	public String getAl() {
		return al.toString();
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void setAl(Algorithm al) {
		this.al = al;
	}
	
	
}
