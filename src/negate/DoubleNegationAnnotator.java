package negate;

import java.util.ArrayList;
import java.util.Map;

public class DoubleNegationAnnotator implements Annotator {
	public void annotate(Sentence s) {
		Map<Integer,Negation> negations = s.getNegations();
		ArrayList<Integer> negMatches = new ArrayList<Integer>(negations.keySet());
		
		if(negMatches.size() > 1){
			for (int i = 1; i < negMatches.size(); i++) {
				int right = negMatches.get(i);
				int left = negMatches.get(i-1);

				// if there are two matches in a 6 word window, add to window array
				// TODO: You'll have to fix the logic here!!
				if ((right-left < 7 && right-left > 0) && (negations.get(right) + negations.get(left) > 2)) {
					s.setDoubleNegation(true);	
				}
			}
		}
	}
}
