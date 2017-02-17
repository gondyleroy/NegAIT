package negate;

import java.util.ArrayList;
import java.util.HashMap;

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
		    		
		    		annotatedSent.add(candidate);
		    		continue;
		    		
		    	} else { 
		    		
		    		candidate += "\tMorphNeg";
		    		annotatedSent.add(candidate);
		    	}
		    	
		    } else {
		    	annotatedSent.add(candidate);
		    	continue;
		    
		    }
		    
		}

		return annotatedSent;
		
	}
	
	public ArrayList<String> sentNegate(ArrayList<String> rSentence) { 
		
		ArrayList<String> annotatedSent = new ArrayList<String>();
		String tag = null;
		
		for (String candidate : rSentence){

			tag = candidate.split("\t")[1];
			
			if (tag.equals("RB")){
				
				// I did this above in the Stanford Dependencies
				// How can I do it here?
				//candidate += "\tSNeg";
				
		    	annotatedSent.add(candidate);
		    	continue;
				
			} else {
				
		    	annotatedSent.add(candidate);
		    	continue;
			}
		}
		
		return annotatedSent;
	}
	
	public ArrayList<String> doubleNegate(ArrayList<String> rSentence) {
		
		ArrayList<String> annotatedSent = new ArrayList<String>();
		int Negcount;
		
		Negcount = 0;
		
		for (String candidate : rSentence){
			
			String[] mainList = candidate.split("\t");
			
			if (mainList.length == 3){
				
				Negcount += 1;
				
			}
		}
		
		for (String candidate : rSentence){
				
			if (Negcount > 1){
				
				String[] mainList = candidate.split("\t");
				
				if (mainList.length == 3){
					
					candidate += "\tDoubleNeg";
					annotatedSent.add(candidate);
					
				} else {
					annotatedSent.add(candidate);
				}
					
			} else {
				annotatedSent.add(candidate);
			}
			
		}
		
		
		
/*		for (int i = 0; i < rSentence.size()-6; i++) {

			String mainCandidate = rSentence.get(i);
			
			String rightCandidate1 = rSentence.get(i+1);
			String rightCandidate2 = rSentence.get(i+2);
			String rightCandidate3 = rSentence.get(i+3);
			String rightCandidate4 = rSentence.get(i+4);
			String rightCandidate5 = rSentence.get(i+5);
			String rightCandidate6 = rSentence.get(i+6);
			
			String[] mainList = mainCandidate.split("\t");
			
			String[] rightList1 = rightCandidate1.split("\t");
			String[] rightList2 = rightCandidate2.split("\t");
			String[] rightList3 = rightCandidate3.split("\t");
			String[] rightList4 = rightCandidate4.split("\t");
			String[] rightList5 = rightCandidate5.split("\t");
			String[] rightList6 = rightCandidate6.split("\t");
			
			if (mainList.length == 3){
				
				if (rightList1.length == 3) {
					
					mainCandidate += "\tDoubleNeg";
					rightCandidate1 += "\tDoubleNeg";
					
				} else if (rightList2.length == 3) {
					
					mainCandidate += "\tDoubleNeg";
					rightCandidate2 += "\tDoubleNeg";
					
				} else if (rightList3.length == 3) {
					
					mainCandidate += "\tDoubleNeg";
					rightCandidate3 += "\tDoubleNeg";
					
				} else if (rightList4.length == 3) {
					
					mainCandidate += "\tDoubleNeg";
					rightCandidate4 += "\tDoubleNeg";
					
				} else if (rightList5.length == 3) {
					
					mainCandidate += "\tDoubleNeg";
					rightCandidate5 += "\tDoubleNeg";
					
				} else if (rightList6.length == 3) {
					
					mainCandidate += "\tDoubleNeg";
					rightCandidate6 += "\tDoubleNeg";
					
				}
				
			} 
			
			annotatedSent.add(mainCandidate);
			
		}*/
		return annotatedSent;
	}
	
}