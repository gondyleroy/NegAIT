package negate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class TextRead {
	
	public TextRead(String filepath) throws IOException { 
		
		System.out.println("Parsing the file...");
		
		BufferedReader inBr;
		String rawLine;
		FileInputStream inStream = null;
		
		try { 
			
			// read in file
			inStream = new FileInputStream(filepath);
			inBr = new BufferedReader(new InputStreamReader(inStream));
			
			// creates a StanfordCoreNLP object, with POS tagging, parsing
			Properties props = new Properties();
			props.setProperty("annotators", "tokenize, ssplit, pos, parse");
			StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
			
			// Parse line
			while ((rawLine = inBr.readLine()) != null) {
			    
				// Parse out the sentences
				Annotation antdLine = new Annotation(rawLine);
				
				// run the selected Annotators on this text
				pipeline.annotate(antdLine);
				
				List<CoreMap> coreSents = antdLine
						.get(CoreAnnotations.SentencesAnnotation.class);
				
				// Declare a Sentence Object for each Sentence
			    for(CoreMap coreSent : coreSents) {
				
			    	Sentence s = new Sentence(coreSent);
			    	
			    	System.out.println(s.toString());
			    	
			    }
			}
			
		} catch (IOException e) {
				
			e.printStackTrace();				
				
		} finally {
				
			try {
			
				if (inStream != null)
					inStream.close();
					
		    } catch (IOException ex) {

				ex.printStackTrace();
			}
		}
	}
}
