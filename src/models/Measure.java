package models;

import java.util.List;
import java.util.ArrayList;

public class Measure {
	private List<SimFileLine> lines;
	private int measureNumber;
	
	private boolean isEmpty = true;
	
	public Measure(int measureNumber) {
		lines = new ArrayList<SimFileLine>();
		this.measureNumber = measureNumber;
	}
	
	
	public List<SimFileLine> getLines() {
		return lines;
	}
	
	public void setLines(List<SimFileLine> lines) {
		this.lines = lines;
	}
	
	public void addLine(SimFileLine line) {
		lines.add(line);
		isEmpty = line.isEmpty() && isEmpty;
	}
	
	public int getNumberOfLines() {
		return lines.size();
	}
	
	public int getMeasureNumber() {
		return measureNumber;
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (SimFileLine line : lines) {
			sb.append(line).append("\n");
		}
		sb.append("----");
		return sb.toString();
	}
}
