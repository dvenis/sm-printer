package models;

import java.util.ArrayList;
import java.util.List;

import models.stepmetadata.NotesType;

public class StepFileDifficultyMap {
	private String description;
	private String difficultyClass;
	private String difficultyMeter;
	private String radarValues;
	
	private NotesType notesType;
	
	private List<Measure> measures;
	
	public StepFileDifficultyMap() {
		measures = new ArrayList<Measure>();
	}
	
	public NotesType getNotesType() {
		return notesType;
	}

	public void setNotesType(String metaCode) {
		this.notesType = NotesType.fromMetaCode(metaCode);
	}
	
	public void setNotesType(NotesType notesType) {
		this.notesType = notesType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDifficultyClass() {
		return difficultyClass;
	}

	public void setDifficultyClass(String difficultyClass) {
		this.difficultyClass = difficultyClass;
	}

	public String getDifficultyMeter() {
		return difficultyMeter;
	}

	public void setDifficultyMeter(String difficultyMeter) {
		this.difficultyMeter = difficultyMeter;
	}

	public String getRadarValues() {
		return radarValues;
	}

	public void setRadarValues(String radarValues) {
		this.radarValues = radarValues;
	}

	public List<Measure> getMeasures() {
		return measures;
	}
	
	public void addMeasure(Measure measure) {
		measures.add(measure);
	}
	
	public int getNumberOfMeasures() {
		return measures.size();
	}
	
	@Override
	public String toString() {
		return notesType + " - " + difficultyClass;
	}
}
