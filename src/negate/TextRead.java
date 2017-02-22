package negate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.*;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.util.*;


public class TextRead {
	
	// Make a List of Array lists to save all sentences in the document
	static List<ArrayList<String>> documentList = new ArrayList<ArrayList<String>>();
	static List<ArrayList<String>> annotatedList = new ArrayList<ArrayList<String>>();
	static HashMap<String, String> morphAcceptMap = null;
	static HashMap<String, String> morphDiscardMap = null;
	
	
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
			
			// create short accept list for all sentence negations
			ArrayList<String> negArray = new ArrayList<>(Arrays
					.asList("no", "neither", "stop","none","not"));

			// Parse lines and annotate using Core NLP
			while ((rawLine = inBr.readLine()) != null) {
				
				Annotation antdLine = new Annotation(rawLine);
				
				// run all the selected Annotators on this text
				pipeline.annotate(antdLine);
			    
				List<CoreMap> coreSents = antdLine
						.get(CoreAnnotations.SentencesAnnotation.class);
			    
				for(CoreMap coreSent : coreSents) {
			    	
					// Array list of strings for each sentence
					ArrayList<String> sentenceArray = new ArrayList<String>();
					
					// create list for all sentence negations tagged by dependency parser
					ArrayList<String> negDepArray = new ArrayList<>();

					// this is the Stanford dependency graph of the current sentence
					SemanticGraph graph = coreSent
								.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class);
					
					List<SemanticGraphEdge> edges = graph.edgeListSorted();
						
				    for (SemanticGraphEdge edge : edges) {
				    
						GrammaticalRelation relation = edge.getRelation();
						
						if (relation.getShortName() == "neg") {
							String werd = edge.getDependent().word();
							negDepArray.add(werd);				    	
						}
				    	
				    }
				    
					for (CoreLabel coreToken: coreSent.get(TokensAnnotation.class)) {
			    		
						// get the text of the token
						String word = coreToken.get(TextAnnotation.class);
			    		
						// get pos tag of the token
						String pos = coreToken.get(PartOfSpeechAnnotation.class);
			    		
						// Add the word and pos
						String token = word + "\t" + pos;
						
						// This is being done out of place == how can we move it?
						// Add Sentential Negation Tag
						if (negArray.contains(word) || negDepArray.contains(word)) {
							token += "\t";
							token += "sentneg";
						}
			    		
						// Add token to Sentence Array List
						sentenceArray.add(token);
					}

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
		
		// Calling all Lexicons 
		Lexicon lexs = new Lexicon();
		morphAcceptMap = lexs.readIn(acceptpath, true);
		morphDiscardMap = lexs.readIn(discardpath, false);
		
		
		// Read through sentences of Input File and Annotate
		for (ArrayList<String> s : documentList){
			
			// declare the sentence object and read in the negation
			Sentence sent = new Sentence(s);
			
			// do morphological negation
			ArrayList<String> annotatedSent = sent.morphNegate(morphAcceptMap,morphDiscardMap);
			
			// do sentential negation
			ArrayList<String> annotatedSent2 = sent.sentNegate(annotatedSent);
			
			// do double negation
			ArrayList<String> annotatedSent3 = sent.doubleNegate(annotatedSent2);			
			
			// add the annotated list to the 
			annotatedList.add(annotatedSent3);
			
			}	
		}	
	
	public void xmlwrite(String filepath) throws ParserConfigurationException, TransformerException {
		
		System.out.println("Writing Annotations...");
		
		// Convert Annotations to XML and write out
	    DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder icBuilder;
		
		// build Doc for XML output
		icBuilder = icFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = icBuilder.newDocument();
		org.w3c.dom.Element mainRootElement = doc.createElement("document");
		doc.appendChild(mainRootElement);
		
		
		for (ArrayList<String> s : annotatedList){
			
			org.w3c.dom.Element sentence = doc.createElement("sentence");
			
			String newS = new String();
			Boolean doubleNeg = false;
			
			for (String t : s){
					
				String[] tokList = t.split("\t");			
				int l = tokList.length;
				
				if (l == 4){
				
					doubleNeg = true;
				}
				
				if (l > 2){
					
					// add in the negation types
					
					if (tokList[2].equals("sentneg") || tokList[2].equals("morphneg")){
						
						// add the previous newS to the node
						Node node = doc.createTextNode(newS);
						sentence.appendChild(node);
						
						// Create Node element for negation
						org.w3c.dom.Element sentneg = doc.createElement(tokList[2]);
						Node negnode = doc.createTextNode(" " + tokList[0] + " ");
						sentneg.appendChild(negnode);
						sentence.appendChild(sentneg);
						
						// Make it so the newS is something new!
						newS = " ";

						
					}
				} else {
				
				// declare the token
				newS += " ";
				newS += tokList[0];
				
				}
			}
			
			sentence.setAttribute("doublenegation", doubleNeg.toString());
			Node node = doc.createTextNode(newS);
			sentence.appendChild(node);
			mainRootElement.appendChild(sentence);
		}
		
		// output DOM XML to console 
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		DOMSource source = new DOMSource(doc);
		StreamResult console = new StreamResult(filepath);
		transformer.transform(source, console);
 
		System.out.println("\nXML Output Created Successfully..");
	}
}
