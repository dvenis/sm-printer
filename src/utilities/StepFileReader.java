package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.Measure;
import models.StepFile;
import models.StepFileDifficultyMap;
import models.StepLine;
import models.stepmetadata.NotesType;

public class StepFileReader {
	private static final String STEP_FILE_REGEX = "#.+?;";
	
	private File systemStepFile;
	private Pattern metaDataRegex;
	private Pattern stepLineRegex;
	
	private StepLine previouslyAddedLine = null;
	
	public StepFileReader(String stepFilePath) {
		this(new File(stepFilePath));
	}
	
	public StepFileReader(File systemStepFile) {
		this.systemStepFile = systemStepFile;
		
		metaDataRegex = Pattern.compile(STEP_FILE_REGEX, Pattern.DOTALL);
		stepLineRegex = Pattern.compile("[0-9MLF]{4,10}"); //huehuehue
	}
	
	public StepFile generateStepFile() {
		StepFile result = new StepFile();
		String[] fileParts = splitStepFile(readStepFileData());
		if (fileParts != null) {			
			generateMetaData(fileParts, result);
		}
		return result;
	}
	
	private String readStepFileData() {
		String result = null;
		try {
			byte[] fileBytes = Files.readAllBytes(Paths.get(systemStepFile.getAbsolutePath()));
			result = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(fileBytes)).toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String[] splitStepFile(String fileData) {
		return getRegexMatchingList(metaDataRegex, fileData).toArray(new String[0]);
	}
	
	private void generateMetaData(String[] stepFileParts, StepFile accumulator) {
		for (String part : stepFileParts) {
			setDataBasedOnTag(part, accumulator);
		}
	}
	
	private void setDataBasedOnTag(String part, StepFile accumulator) {
		switch (getTag(part)) {
		case "#TITLE":
			accumulator.setTitle(stripTag(part));
			break;
		case "#SUBTITLE":
			accumulator.setSubtitle(stripTag(part));
			break;
		case "#ARTIST":
			accumulator.setArtist(stripTag(part));
			break;
		case "#CREDIT":
			accumulator.setCredit(stripTag(part));
			break;
		case "#NOTES":
			generateStepData(part, accumulator);
		}
	}
	
	private void generateStepData(String notes, StepFile accumulator) {
		previouslyAddedLine = null;
		//for all note data
		notes = stripTag(notes);
		
		String[] difficultyParts = notes.split(":");
		if (difficultyParts.length != 6) {
			System.err.println("there was an error reading the difficulty: expected 6 got " + difficultyParts.length);
			return;
		}
		
		StepFileDifficultyMap difficulty = new StepFileDifficultyMap();
		difficulty.setNotesType(difficultyParts[0].trim());
		difficulty.setDescription(difficultyParts[1].trim());
		difficulty.setDifficultyClass(difficultyParts[2].trim());
		difficulty.setDifficultyMeter(difficultyParts[3].trim());
		difficulty.setRadarValues(difficultyParts[4].trim());
		
		if (difficulty.getNotesType() != null) {
			String[] measureData = difficultyParts[5].split(",");
			for (int i = 0; i < measureData.length; i++) {
				difficulty.addMeasure(parseMeasure(measureData[i], i, difficulty.getNotesType()));
			}
			
			difficulty.trimMeasures();
			accumulator.addDifficulty(difficulty);
			
			System.out.println("SET DIFFICULTY: " + difficulty);
		}
	}
	
	private Measure parseMeasure(String measure, int measureNumber, NotesType notesType) {
		Measure result = new Measure(measureNumber);
		List<String> rawLines = getRegexMatchingList(stepLineRegex, measure); 
		int lineIndex = 0;
		int numberLinesInMeasure = rawLines.size();
		for (String rawLine : rawLines) {
			//create new step line given the previously added line
			previouslyAddedLine = new StepLine(rawLine, previouslyAddedLine, notesType, lineIndex, numberLinesInMeasure);
			result.addLine(previouslyAddedLine);
			lineIndex++;
		}
		
		return result;
	}
	
	private String getTag(String part) {
		int indexHash = part.indexOf('#');
		int indexColon = part.indexOf(':');
		if (indexHash != -1 && indexColon != -1) {
			return part.substring(indexHash, indexColon);
		}
		return "";
	}
	
	private String stripTag(String part) {
		return part.substring(part.indexOf(':') + 1, part.length() - 1).trim();
	}
	
	private List<String> getRegexMatchingList(Pattern pattern, String data) {
		Matcher match = pattern.matcher(data);
		List<String> matches = new ArrayList<String>();
		while (match.find()) {
			matches.add(match.group());
		}
		return matches;
	}
	
	public static void main(String[] args) {
		StepFileReader reader = new StepFileReader("data/Feels Just Like That Night.sm");
		//StepFileReader reader = new StepFileReader("data/BREAK DOWN!.sm");
		StepFile file = reader.generateStepFile();
		System.out.println(file);
		for (Measure m : file.getDifficulties().get(0).getMeasures()) {
			System.out.println(m);
		}
	}
}
