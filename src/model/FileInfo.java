package model;

public class FileInfo {
	private String fileURL;
	private long fileSize; //bytes
	private boolean isCompress;
	private int compressProgress;
	private int crytoProgress;
	private boolean md5;
	public FileInfo(String fileURL, long fileSize, boolean isCompress, int compressProgress, 
			int crytoProgress, boolean md5) {
		super();
		this.fileURL = fileURL;
		this.fileSize = fileSize;
		this.setCompress(isCompress);
		this.setCompressProgress(compressProgress);
		this.crytoProgress = crytoProgress;
		this.md5 = md5;
	}
	public String getFileURL() {
		return fileURL;
	}
	public String getFileSize() {
		if (fileSize < 1024)
			return fileSize + " bytes";
		else if (fileSize < 1024*1024)
			return String.format("%.2f", fileSize/1024.0) + " KB";
		else if (fileSize < 1024*1024*1024)
			return String.format("%.2f",fileSize/1024.0/1024.0) + " MB";
		else
			return String.format("%.2f",fileSize/1024.0/1024.0/1024.0) + " GB";
	}
	public String getProgress() {
		return Integer.toString(crytoProgress);
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
		this.crytoProgress = progress;
	}
	public void setMd5(boolean md5) {
		this.md5 = md5;
	}
	
	public long getFileSizeLong() {
		return fileSize;
	}
	public String getCompressProgress() {
		return Integer.toString(compressProgress);
	}
	public void setCompressProgress(int compressProgress) {
		this.compressProgress = compressProgress;
	}
	public String isCompress() {
		return Boolean.toString(isCompress);
	}
	public void setCompress(boolean isCompress) {
		this.isCompress = isCompress;
	}
	
}
