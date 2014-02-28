package models;

import java.util.ArrayList;
import java.util.List;

public class StepFile {
	private String title;
	private String subtitle;
	private String artist;
	private String credit;
	
	private float bpm;
	private List<StepFileDifficultyMap> difficulties;
	
	public StepFile() {
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
	
	@Override
	public String toString() {
		return title + " - " + artist + " [" + credit + "]";
	}
}
