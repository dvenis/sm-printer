package models;

import java.util.ArrayList;
import java.util.List;

public class StepFile {
	private String title;
	private String subtitle;
	private String artist;
	private String credit;
	
	private int stepsPerLine;
	
	private float bpm;
	
	private List<Measure> measures;
	private List<StepFileDifficultyMap> difficulties;
	
	public StepFile() {
		measures = new ArrayList<Measure>();
		//TODO get the difficulty maps working
		difficulties = new ArrayList<StepFileDifficultyMap>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	public int getStepsPerLine() {
		//TODO return steps per line, and actually parse it
		return measures.get(0).getLines().get(0).getSteps().length;
		//return stepsPerLine;
	}
	
	public void setStepsPerLine(int stepsPerLine) {
		this.stepsPerLine = stepsPerLine;
	}

	public float getBpm() {
		return bpm;
	}

	public void setBpm(float bpm) {
		this.bpm = bpm;
	}
	
	public List<StepFileDifficultyMap> getDifficulties() {
		return difficulties;
	}
	
	public void addDifficulty(StepFileDifficultyMap difficulty) {
		difficulties.add(difficulty);
	}
	
//	public List<Measure> getMeasures() {
//		return measures;
//	}
//	
//	public void setMeasures(List<Measure> measures) {
//		this.measures = measures;
//	}
//	
//	public void addMeasure(Measure newMeasure) {
//		measures.add(newMeasure);
//		
//	}
	
	@Override
	public String toString() {
		return title + " - " + artist + " [" + credit + "]";
	}
}
