package models;

import java.util.List;
import java.util.ArrayList;

public class Measure {
	private List<StepLine> lines;
	private int measureNumber;
	
	public Measure(int measureNumber) {
		lines = new ArrayList<StepLine>();
		this.measureNumber = measureNumber;
	}
	
	
	public List<StepLine> getLines() {
		return lines;
	}
	
	public void setLines(List<StepLine> lines) {
		this.lines = lines;
	}
	
	public void addLine(StepLine line) {
		lines.add(line);
	}
	
	public int getNumberOfLines() {
		return lines.size();
	}
	
	public int getMeasureNumber() {
		return measureNumber;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (StepLine line : lines) {
			sb.append(line).append("\n");
		}
		sb.append("----");
		return sb.toString();
	}
}
