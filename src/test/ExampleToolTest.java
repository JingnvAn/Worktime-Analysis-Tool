package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.InputFileReader;
import main.WorkEntry;
import main.WorkTimeAnalysisTool;

/**
 * This file holds a single JUnit test case that mimics the user interaction described on the example output in the PDF.
 * <p><b>You should not edit this file.</b></p> 
 * 
 * @author cs21a
 * @version 2.0
 */
class ExampleToolTest {

	/* Example test: this tests your tool based on the user inputs outlined in the PDF
	 */
	@Test
	void exampleTest() {
		WorkEntry[] entries = InputFileReader.getWorkEntriesFromCSV("pdf_table_csv.txt");
		WorkTimeAnalysisTool tool = new WorkTimeAnalysisTool(entries);
		assertEquals("office hours\nmet with tas\ngrading hw\ngrading exam", tool.parse("list r").trim());
		assertEquals("grading exam\ngrading hw\nmet with tas\noffice hours", tool.parse("list l").trim());
		
		// haven�t called search, illegal state exception thrown
		assertThrows(IllegalStateException.class, () -> {  
			tool.parse("del 0");
		});
		
		// not an activity in the input data, nothing should be returned
		assertTrue(tool.parse("search \"grading pa\"").trim().isEmpty()); 
		
		// list r should reflect recent search even though it was unsuccessful
		assertEquals("grading hw\ngrading exam\nmet with tas\noffice hours", tool.parse("list r").trim()); 
		assertEquals("[9/18/2019] office hours (1.5 h): helped students with pa1\n[10/1/2019] office hours (1.0 h):\nTotal: 2.5 h", tool.parse("search \"office hours\"").trim());
		
		// list r should reflect recent successful search 
		assertEquals("office hours\nmet with tas\ngrading hw\ngrading exam", tool.parse("list r").trim());
		
		// only 2 entries associated with activity (indexed 0, 1) 
		assertThrows(IndexOutOfBoundsException.class, () -> {
			tool.parse("del 2");
		});
		
		tool.parse("del 0");
		
		// valid delete should result in entry for 9/18/2019 being removed from entries associated with "office hours"
		assertEquals("[10/1/2019] office hours (1.0 h):\nTotal: 1.0 h", tool.parse("search \"office hours\"").trim());
	}
	

}
