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

public class StepFileReader {
	private static final String STEP_FILE_REGEX = "#.+?;";
	
	private File systemStepFile;
	private Pattern metaDataRegex;
	private Pattern stepLineRegex;
	
	public StepFileReader(String stepFilePath) {
		this(new File(stepFilePath));
	}
	
	public StepFileReader(File systemStepFile) {
		this.systemStepFile = systemStepFile;
		
		metaDataRegex = Pattern.compile(STEP_FILE_REGEX, Pattern.DOTALL);
		stepLineRegex = Pattern.compile("\\d{4,10}");
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
		//Pattern p = Pattern.compile(STEP_FILE_REGEX, Pattern.DOTALL);
		return getRegexMatchingList(metaDataRegex, fileData).toArray(new String[0]);
//		List<String> splitData = new ArrayList<String>();
//		Matcher metaDataMatcher = p.matcher(fileData);
//		while (metaDataMatcher.find()) {
//			splitData.add(metaDataMatcher.group());
//		}
//		return splitData.toArray(new String[0]);
	}
	
	private void generateMetaData(String[] stepFileParts, StepFile accumulator) {
		for (String part : stepFileParts) {
			//System.out.println(part);
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
		
		String[] measureData = difficultyParts[5].split(",");
		for (String measure : measureData) {
			difficulty.addMeasure(parseMeasure(measure));
		}
		
		accumulator.addDifficulty(difficulty);
		
		System.out.println("SET DIFFICULTY: " + difficulty);
	}
	
	private Measure parseMeasure(String measure) {
		Measure result = new Measure();
		List<String> rawLines = getRegexMatchingList(stepLineRegex, measure); 
		for (String rawLine : rawLines) {
				result.addLine(new StepLine(rawLine));
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
		StepFileReader reader = new StepFileReader("data/BREAKDOWN_expert.sm");
		//StepFileReader reader = new StepFileReader("data/BREAK DOWN!.sm");
		StepFile file = reader.generateStepFile();
		System.out.println(file);
		for (Measure m : file.getDifficulties().get(0).getMeasures()) {
			System.out.println(m);
		}
	}
}
