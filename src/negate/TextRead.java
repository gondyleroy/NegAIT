package negate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.*;

public class TextRead {
	
	private List<ArrayList<String>> documentList = new ArrayList<ArrayList<String>>();	
	
	
	public TextRead(String filepath) { 
		
		// Make a List of Array lists to save all sentences in the document
		String rawLine = null;
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			
			// File Input Vars
			fr = new FileReader(filepath);
			br = new BufferedReader(fr);
			
			// creates a StanfordCoreNLP object, with POS tagging, parsing
			Properties props = new Properties();
			props.setProperty("annotators", "tokenize, ssplit, pos, parse");
			StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
			
			// Read in File Buffer
			br = new BufferedReader(new FileReader(filepath));

			// Parse lines and annotate using Core NLP
			while ((rawLine = br.readLine()) != null) {
				
				Annotation antdLine = new Annotation(rawLine);
			    // run all the selected Annotators on this text
			    pipeline.annotate(antdLine);
			    
			    List<CoreMap> coreSents = antdLine.get(CoreAnnotations.SentencesAnnotation.class);
			    
			    for(CoreMap coreSent : coreSents) {
			    	
					// Array list of strings for each sentence
					ArrayList<String> sentenceArray = new ArrayList<String>();

			    	for (CoreLabel coreToken: coreSent.get(TokensAnnotation.class)) {
			    		
			    		// get the text of the token
			    		String word = coreToken.get(TextAnnotation.class);
			    		
			    		// get pos tag of the token
			    		String pos = coreToken.get(PartOfSpeechAnnotation.class);
			    		
			    		// Add the word and pos
			    		String token = word + "\t" + pos;
			    		
			    		// Add token to Sentence Array List
			    		sentenceArray.add(token);
			    	}

			    	// this is the parse tree of the current sentence
			    	//Tree tree = coreSent.get(TreeAnnotation.class);
			    	documentList.add(sentenceArray);
			    }    
			}
			    
			System.out.println("Works!");
			
		} catch (IOException e1) {
			
			// Couldn't open the File
			e1.printStackTrace();
			System.out.println("Couldn't parse: " + filepath + "!!!");
			
		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException e2) {

				e2.printStackTrace();

			}
		}
		
	for (ArrayList<String> s : documentList){
		System.out.println("Sentence:");
		for (String t : s){
			System.out.println(t);
			}
		}
	}
}