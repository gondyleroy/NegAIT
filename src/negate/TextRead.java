package negate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class TextRead {
	public TextRead(String filepath) throws IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, URISyntaxException { 
		
		// Declare Instance Variables
		BufferedReader inBr;
		String rawLine;
		FileInputStream inStream = null;
		
		// Declare Annotators
		Annotator mn = new MorphNegationAnnotator();
		Annotator sn = new SententialNegationAnnotator();
		Annotator dn = new DoubleNegationAnnotator();
		
		try { 
			
			// read in file
			inStream = new FileInputStream(filepath);
			inBr = new BufferedReader(new InputStreamReader(inStream));
			
			// creates a StanfordCoreNLP object, with POS tagging, parsing
			Properties props = new Properties();
			props.setProperty("annotators", "tokenize, ssplit, pos, parse");
			StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
			
			// Open XML File to Write Out
		    DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder icBuilder;
			
			// build Doc for XML output
			icBuilder = icFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = icBuilder.newDocument();
			org.w3c.dom.Element mainRootElement = doc.createElement("document");
			doc.appendChild(mainRootElement);
			
			// Parse line
			while ((rawLine = inBr.readLine()) != null) {
			    
				// Parse out the sentences
				Annotation antdLine = new Annotation(rawLine);
				
				// run the selected Stanford Annotators on the text
				pipeline.annotate(antdLine);
				
				List<CoreMap> coreSents = antdLine
						.get(CoreAnnotations.SentencesAnnotation.class);
				
				// Declare a Sentence Object for each Sentence
			    for(CoreMap coreSent : coreSents) {
			    		
			    		Sentence s = new Sentence(coreSent);
			    	 
			    		// Do the annotations
			    		mn.annotate(s);
			    		sn.annotate(s);
			    		dn.annotate(s);
			    		
			    		//Write to XML
			    		mainRootElement.appendChild(s.toXML(doc));
			    		
			    }

			}
			
			// output DOM XML to console 
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			DOMSource source = new DOMSource(doc);
			CodeSource codeSource = main.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();
			//String decodedPath = URLDecoder.decode(path, "UTF-8");
			StreamResult console = new StreamResult(jarDir + "Output.xml");
			transformer.transform(source, console);
		} catch (IOException e) {
			e.printStackTrace();				
		} finally {
			try {
				if (inStream != null){
					inStream.close();
				}
		    } catch (IOException ex) {

				ex.printStackTrace();
			}
		}
	}
}
