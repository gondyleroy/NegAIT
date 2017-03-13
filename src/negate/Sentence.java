package negate;

import java.util.ArrayList;

import edu.stanford.nlp.util.CoreMap;

public class Sentence {
	
	//instance variables
	private CoreMap s = null;
	private ArrayList<Integer> negs = new ArrayList<Integer>();
	
	
	//Raw Core Map
	public Sentence(CoreMap coreSent){
		s = coreSent;
	}
	
	// Get the String
	public String toString(){
		return s.toString();	
	}
	
	
	//toXML
}
