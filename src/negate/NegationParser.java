package negate;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class NegationParser {
	
		public static void main (String[] args) throws IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, URISyntaxException {	
			// Call Text Read
			

			if( args.length != 2 ){
				// print out some message about usage
				System.out.println("You havent specified the correct number of files!");
			}else{
				// do whatever you need to do
				TextRead text = new TextRead(args[0],args[1]);
			
				// Print Done!
				System.out.println("Finished Parsing!");
			
			}
		} 
}
