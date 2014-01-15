package utilities;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import models.Measure;
import models.StepFile;
import models.StepLine;

public class StepFileReader {
	private File systemStepFile;
	
	//private StepFile stepFile;
	
	public StepFileReader(String stepFilePath) {
		this(new File(stepFilePath));
	}
	
	public StepFileReader(File systemStepFile) {
		this.systemStepFile = systemStepFile;
	}
	
	public StepFile generateStepFile() {
		StepFile result = new StepFile();
		String[] fileParts = splitFileData();
		if (fileParts != null) {
			generateMetaData(fileParts, result);
		}
		
		return result;
	}
	
	private String[] splitFileData() {
		String[] result = null;
		try {
			//TODO deal with UTF-8
			StringBuilder fileData = new StringBuilder();
			BufferedReader in = new BufferedReader(new FileReader(systemStepFile));
			String s;
			while ((s = in.readLine()) != null) {
				fileData.append(s).append("\n");
			}
			result = fileData.toString().split(";");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result;
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
		//for all note data
		notes = stripTag(notes).trim();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(notes.getBytes())));
			
			in.readLine(); //notes type
			in.readLine(); //description
			in.readLine(); //difficulty
			in.readLine(); //difficulty number
			in.readLine(); //radar values
			
			StringBuilder noteData = new StringBuilder();
			String s;
			while ((s = in.readLine()) != null) {
				//System.out.println("READ: " + s);
				noteData.append(s).append("\n");
			}
			
			String[] measures = noteData.toString().split(",");
			for (String measure : measures) {
				if (measure.trim().startsWith("#NOTES")) {
					return;
				}
				if (measure.trim().length() > 0) {
					accumulator.addMeasure(parseMeasure(measure));
				}
			}
			
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Measure parseMeasure(String measure) {
		Measure result = new Measure();
		
		//TODO make it work with windows and mac
		String[] rawLines = measure.split("\n");
		for (String rawLine : rawLines) {		
			if (rawLine.trim().length() > 0) {
				//System.out.println(rawLine);
				result.addLine(new StepLine(rawLine));
			}
		}
		
		return result;
	}
	
	private String getTag(String part) {
		return part.substring(part.indexOf('#'), part.indexOf(':'));
	}
	
	private String stripTag(String part) {
		return part.substring(part.indexOf(':') + 1);
	}
	
	public static void main(String[] args) {
		StepFileReader reader = new StepFileReader("data/steps.sm");
		StepFile file = reader.generateStepFile();
		System.out.println(file);
		for (Measure m : file.getMeasures()) {
			System.out.println(m);
		}
//		StepLine line = new StepLine("0111");
//		StepLine line2 = new StepLine("2011");
//		StepLine line3 = new StepLine("0100");
//		StepLine line4 = new StepLine("1001");
//		Measure measure = new Measure();
//		measure.addLine(line);
//		measure.addLine(line2);
//		measure.addLine(line3);
//		measure.addLine(line4);
//		System.out.println(measure);
	}
}
