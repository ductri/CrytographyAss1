package model;

public class FileInfo {
	private String fileURL;
	private int fileSize; //bytes
	private int progress;
	private boolean md5;
	public FileInfo(String fileURL, int fileSize, int progress, boolean md5) {
		super();
		this.fileURL = fileURL;
		this.fileSize = fileSize;
		this.progress = progress;
		this.md5 = md5;
	}
	public String getFileURL() {
		return fileURL;
	}
	public String getFileSize() {
		return fileSize + "bytes";
	}
	public String getProgress() {
		return Integer.toString(progress);
	}
	public String getMd5() {
		return Boolean.toString(md5);
	}
	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public void setMd5(boolean md5) {
		this.md5 = md5;
	}
	
	
	
}
