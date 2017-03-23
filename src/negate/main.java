package negate;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class main {
	
		public static void main (String[] args) throws IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
			
			// Call Text Read
			TextRead text = new TextRead("resources/documents/goldstandard.txt");
			
			// Print Done!
			System.out.println("Done!!!");
		} 

}
