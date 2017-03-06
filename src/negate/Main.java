package negate;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Main {
	
	static String articlepath;
	static String acceptpath;
	static String discardpath;	
	static String outpath;

	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException {
		
		// get dir
		String dir = System.getProperty("user.dir");
		
		// declare paths
		articlepath = dir + "/resources/documents/DoubleNegatives.txt";
		acceptpath = dir + "/resources/documents/Morphological_Negation/PorterMorphologicalNegationList.txt";
		discardpath = dir + "/resources/documents/Morphological_Negation/ExemptionsToMorphologicalNegationList.txt";
		outpath = dir + "/resources/documents/DNOutput.xml";
		
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