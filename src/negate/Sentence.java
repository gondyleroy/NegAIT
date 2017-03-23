package negate;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;

public class Sentence {
	
	// Instance Variables
	public CoreMap s;
	public boolean hasDoubleNegation = false;
	public HashMap<Integer,Integer> negations = new HashMap<Integer,Integer>();
	
	
	// Constructor
	public Sentence(CoreMap coreSent){
		s = coreSent;
	}
	
	// Get Token Map
	public ArrayList<String> toList(){
		
		ArrayList<String> tList = new ArrayList<String>();
		
		for (CoreLabel c : s.get(TokensAnnotation.class)){
			tList.add(c.get(TextAnnotation.class));
		}
		return tList;
	}
	
	// Set double negation
	public void setDoubleNegation(boolean dn){
		   hasDoubleNegation = dn;
		}
	
	// Add the type of negation marked by the annotators
	public void addNegation(int wordIndex, int negType){
		negations.put(wordIndex, negType);
	}

	//toXML
	public Element toXML(org.w3c.dom.Document doc) {
		
		org.w3c.dom.Element elmS = doc.createElement("sentence");
		String newS = new String();
		
		int idx = 0;
		
		for (CoreLabel c : s.get(TokensAnnotation.class)){
			
			String token = c.get(TextAnnotation.class);
			
			if (negations.containsKey(idx)){
			
				String neglabel;
				
				// add the previous newS to the node
				Node node = doc.createTextNode(newS);
				elmS.appendChild(node);
					
				// Infer type of negation
				if (negations.get(idx) == 2){
					neglabel = "sentneg";
				} else {
					neglabel = "morphneg";
				}
					
				// Create Node element for negation
				org.w3c.dom.Element sentneg = doc.createElement(neglabel);
				Node negnode = doc.createTextNode(token);
				sentneg.appendChild(negnode);
				elmS.appendChild(sentneg);
					
				// Make it so the newS is something new!
				newS = " ";
				
			} else {
				
				newS += " ";
				newS += token;
			}
			
			idx += 1;
					
		}
		
		if(hasDoubleNegation){
			elmS.setAttribute("doublenegation", Boolean.toString(hasDoubleNegation));
		}
				
		Node node = doc.createTextNode(newS);
		elmS.appendChild(node);
		
		return elmS;
	}
}
