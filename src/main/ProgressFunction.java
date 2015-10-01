package main;

public abstract class ProgressFunction {
	public int rowIndex;
	public abstract void updateProgress(int percent);
	public ProgressFunction(int rowIndex) {
		this.rowIndex = rowIndex;
	}
}
