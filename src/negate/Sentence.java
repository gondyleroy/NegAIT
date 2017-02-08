package negate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sentence {
	private ArrayList<String> rawSent;
	
	Sentence (ArrayList<String> inSent){
		this.rawSent =inSent;
	}
	
	public ArrayList<String> morphNegate(HashMap<String,String>  acceptMap,HashMap<String,String> discardMap) { 
		
		String token = null;
		ArrayList<String> annotatedSent = new ArrayList<String>();

		for (String candidate : this.rawSent){
			token = candidate.split("\t")[0];
		    if (acceptMap.containsKey(token)) {
		    	
		    	if (discardMap.containsKey(token)) {
		    		
		    		continue;
		    		
		    	} else { 
		    		
		    		candidate += "\tMorphNegation";
		    	}
		    	
		    } else { 
		    	
		    	continue;
		    
		    }
		}
		
		return annotatedSent;
		
	}
	
	public void doubleNegation(ArrayList<String> rawSentence) { 
		
		
	}
	
	public void sententialNegation(ArrayList<String> rawSentence) { 
		
		
	}
}
