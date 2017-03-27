package negate;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException {
		
		TextRead text = new TextRead("resources/documents/goldstandard.txt");
		
		System.out.println("Done!!!");
	}
}