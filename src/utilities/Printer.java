package utilities;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import models.StepFile;
import models.StepFileDifficultyMap;

public class Printer {
	public static void printSimFile(StepFile stepFile, StepFileDifficultyMap difficulty) {
		PrinterJob job = PrinterJob.getPrinterJob();
		StepFileRenderer printerRenderer = new StepFileRenderer();
		printerRenderer.setStepFileAndDifficulty(stepFile, difficulty);
		
		PageFormat defaultFormat = job.defaultPage();
		defaultFormat.setOrientation(PageFormat.REVERSE_LANDSCAPE);
		defaultFormat.setPaper(getPaper(0.5, 72));
		
		job.setPageable(createBook(printerRenderer, defaultFormat));
		
		boolean doPrint = job.printDialog();
		if (doPrint) {
			try {
				job.print();
			} catch (PrinterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private static Book createBook(StepFileRenderer renderer, PageFormat format) {
		Book book = new Book();
		for (int i = 0; i < renderer.getNumberOfPages(); i++) {
			book.append(renderer, format);
		}
		return book;
	}
	
	private static Paper getPaper(double margin, double dpi) {
		final double startX = margin * dpi;
		final double startY = margin * dpi;
		final double width = (8.5 - 2*margin) * dpi;
		final double height = (11 - 2*margin) * dpi;
		
		Paper paper = new Paper();
		paper.setImageableArea(startX, startY, width, height);
		return paper;
	}
}
