package negate;

import java.io.IOException;

public class Main {
	
	static String articlepath;
	static String acceptpath;
	static String discardpath;	
	static String outpath;

	public static void main(String[] args) throws IOException {
		
		// get dir
		String dir = System.getProperty("user.dir");
		
		// declare paths
		articlepath = dir + "/resources/documents/AagenaesSyndrome.txt";
		acceptpath = dir + "/resources/documents/Morphological_Negation/MorphologicalNegationList.txt";
		discardpath = dir + "/resources/documents/Morphological_Negation/ExemptionsToMorphologicalNegationList.txt";
		outpath = dir + "/resources/documents/Output.txt";
		
		// initialize Text Read Object
		TextRead text = new TextRead();
		
		// Read in the File and Get Sentences/Tags
		text.parse(articlepath);
		
		// Annotate the Negations
		text.annotate(acceptpath,discardpath);
		
		// Write out to XML
		text.xmlwrite(outpath);
		
		System.out.println("Done!!!");
	}
}