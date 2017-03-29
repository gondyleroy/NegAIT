package negate;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class NegationParser {
	
		public static void main (String[] args) throws IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, URISyntaxException {	
			// Call Text Read
			TextRead text = new TextRead(args[0],args[1]);
			
			// Print Done!
			System.out.println("Done!!!");
		} 

}
