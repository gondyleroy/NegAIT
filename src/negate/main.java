package negate;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class main {
	
		public main(String[] args) throws IOException, ParserConfigurationException, TransformerException {
			
			// get dir
			String filePath = System.getProperty("user.dir") + "/resources/documents/AagenaesSyndrome.txt";
			
			// Call Text Read
			TextRead text = new TextRead(filePath);
			
			// Print Done!
			System.out.println("Done!!!");
		}

}
