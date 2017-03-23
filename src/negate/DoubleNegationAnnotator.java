package negate;

import java.util.ArrayList;

public class DoubleNegationAnnotator implements Annotator {
	
	
	public void annotate(Sentence s) { 
		
		ArrayList<Integer> negMatches = new ArrayList<Integer>(s.negations.keySet());
		
		if(negMatches.size() > 1){
			
			for (int i = 1; i < negMatches.size(); i++) {
				
				int right = negMatches.get(i);
				int left = negMatches.get(i-1);

				// if there are two matches in a 6 word window, add to window array
				if ((right-left < 7 && right-left > 0) && (s.negations.get(right) + s.negations.get(left) > 2)) {
					
					s.setDoubleNegation(true);
					
				}
			}

		}
	}
}
