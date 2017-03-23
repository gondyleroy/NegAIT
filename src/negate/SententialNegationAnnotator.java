package negate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.GrammaticalRelation;

public class SententialNegationAnnotator implements Annotator {
	
	ArrayList<String> hits = new ArrayList<String>();
	
	
	// Create short accept list for all sentence negations
	ArrayList<String> negArray = new ArrayList<>(Arrays
			.asList("no", "neither", "stop","stops","none","not"));
	
	public void annotate(Sentence s) { 
		
		// Stanford dependency graph of the current sentence
		SemanticGraph graph = s.s.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class);
			
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
				
				s.negations.put(idx,2);
			    	
			}
		    
			idx += 1;
		}
	}
}
