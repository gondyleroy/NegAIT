package negate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.GrammaticalRelation;

public class SententialNegationAnnotator implements Annotator {	
	private HashSet<String> hits = new HashSet<String>();
	
	// Create short accept list for all sentence negations
	private static final HashSet<String> negArray = new HashSet<String>(Arrays.asList("no", "neither", "stop","stops","none","not"));
	
	public void annotate(Sentence s) { 
		// Stanford dependency graph of the current sentence
		SemanticGraph graph = s.getCoreMap().get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class);
		List<SemanticGraphEdge> edges = graph.edgeListSorted();
				
		for (SemanticGraphEdge edge : edges) {
			GrammaticalRelation relation = edge.getRelation();
			String token = edge.getDependent().word();
			
			if (relation.getShortName() == "neg"){
				hits.add(token);		
			}
		}
		
		int idx = 0;
			
		for (String token : s.toList()){
			if (hits.contains(token) || negArray.contains(token)) {
				s.addNegation(idx, Negation.SENTENTIAL);
			}
		    
			idx += 1;
		}
	}
}
