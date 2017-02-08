package negate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.*;

public class TextRead {
	
	// Make a List of Array lists to save all sentences in the document
	static List<ArrayList<String>> documentList = new ArrayList<ArrayList<String>>();
	static List<ArrayList<String>> annotatedList = new ArrayList<ArrayList<String>>();
	static HashMap<String, String> acceptMap = null;
	static HashMap<String, String>  discardMap = null;
	
	
	public void parse (String filepath) throws IOException { 
		
		System.out.println("Parsing the file...");
		
		BufferedReader inBr;
		String rawLine = null;
		FileInputStream inStream = null;
		
		try { 
			
			// read in file
			inStream = new FileInputStream(filepath);
			inBr = new BufferedReader(new InputStreamReader(inStream));
			
			// creates a StanfordCoreNLP object, with POS tagging, parsing
			Properties props = new Properties();
			props.setProperty("annotators", "tokenize, ssplit, pos, parse");
			StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
			

			// Parse lines and annotate using Core NLP
			while ((rawLine = inBr.readLine()) != null) {
				
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

					// this would be the parse tree of the current sentence, but we don't need it?
					//Tree tree = coreSent.get(TreeAnnotation.class);
					documentList.add(sentenceArray);
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
	
	// Annotate the Sentences
	public void annotate(String acceptpath, String discardpath) {
		
		// Vars for Accept and Discard map/lists
		System.out.println("Annotating Negations...");
		
		
		// Subclass to read in the Accept and Discard Lexicons
		class Lexicon {
			
			// Read in the Accept Lexicon (Derived and Underived Forms)
			public HashMap<String, String> readAccept(String path){
				
				HashMap<String, String> lexicon = new HashMap<>();
				String rawLine = null;
				FileInputStream stream = null;
				BufferedReader inBr;
				
				try{
					
			        stream = new FileInputStream(path);
					inBr = new BufferedReader(new InputStreamReader(stream));
						
						// Parse lines and add to Lexicon Hashmap
						while ((rawLine = inBr.readLine()) != null) {
							
							// Split the Line by , to get the Derived, Underived Pair
							String[] line = rawLine.split(",");
							
							if (line.length == 2){
								
								// Add them to Hashmap <derived, underived>
								lexicon.put(line[0], line[1]);
								
							} else {
								
								// There are some typos, and this will tell us about them
								System.out.println("The accept lexicon couldn't parse: " + rawLine  );
								continue;
							}
						}

				} catch (IOException e){
					
					e.printStackTrace();
					
			    } finally {
			    	
			    	try{
			    		
			    		if (stream != null)
			    			stream.close();
			    		
			    	} catch (IOException ex){
			    		
						ex.printStackTrace();
			    	}
			    }
				return lexicon;
			}
			
			// Read in the Discard list
			public HashMap<String, String> readDiscard(String path){
				
				HashMap<String, String> lexicon = new HashMap<>();
				String rawLine = null;
				FileInputStream stream = null;
				String derived = null;
				BufferedReader inBr;
				
				try{
					
			        stream = new FileInputStream(path);
					inBr = new BufferedReader(new InputStreamReader(stream));
						
						// Parse lines and add to Lexicon List
						while ((rawLine = inBr.readLine()) != null) {
							
							String[] line = rawLine.split(",");
							
							derived = line[0].substring(1);

							lexicon.put(derived, "null");
							
							}

				} catch (IOException e){
					
					e.printStackTrace();
					
			    } finally {
			    	
			    	try{
			    		
			    		if (stream != null)
			    			stream.close();
			    		
			    	} catch (IOException ex){
			    		
						ex.printStackTrace();
			    	}
			    }
				
				return lexicon;
			}
		}
		
		// Calling all Lexicons 
		Lexicon lexs = new Lexicon();
		acceptMap = lexs.readAccept(acceptpath);
		discardMap = lexs.readDiscard(discardpath);
		
		
		// Read through sentences of Input File and Annotate
		for (ArrayList<String> s : documentList){
			
			// declare the sentence object and read in the negation
			Sentence sent = new Sentence(s);
			annotatedList.add(sent.morphNegate(acceptMap,discardMap));
			
			}
		
		
	}	
	
	public void xmlwrite(String filepath) {
		
		System.out.println("Writing Annotations...");
		
		// Convert Annotations to XML and write out
		BufferedWriter bw = null;
		FileWriter fw = null;
		int i = 0;
		
		try{

			fw = new FileWriter(filepath);
			bw = new BufferedWriter(fw);
			

			for (ArrayList<String> s : annotatedList){
				i += 1;
				bw.write("Sentence " + Integer.toString(i) + ":");
				bw.write("\n");
				for (String t : s){
					bw.write(t);
					bw.write("\n");
				}
				bw.write("\n");
				bw.write("\n");
			}
		
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();
			}
		}
	}
}