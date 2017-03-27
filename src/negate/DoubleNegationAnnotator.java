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

				// if there are two matches in a 6 word window
				if (right-left < 7 && right-left > 0) {
					// If both matches aren't morphological (i.e. only morph+sent, or sent+sent are ok)
					if (!((negations.get(right) == Negation.MORPHOLOGICAL) && (negations.get(left) == Negation.MORPHOLOGICAL))){
						s.setDoubleNegation(true);
					}
				}
			}
		}
	}
}
