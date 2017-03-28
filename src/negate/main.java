package negate;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, TransformerFactoryConfigurationError, URISyntaxException {
		
		TextRead text = new TextRead(args[0]);
		
		System.out.println("Done!!!");
	}
}