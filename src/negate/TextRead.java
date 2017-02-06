package negate;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

public class TextRead {
	
	public TextRead(String filepath) { 
		
		// Make a List of Array lists to save all sentences in the document
		List<ArrayList<String>> documentList = new ArrayList<ArrayList<String>>();
		
		try {
			
			// Parse the Document into Sentences using the Stanford Preproccessor Class
			DocumentPreprocessor dp = new DocumentPreprocessor(filepath);
			
			
			// Transfer the Preprocessor to an Array List of Array Lists
			for (List<HasWord> hwSentence : dp) {
				
				// Array list of strings for each sentence
				 ArrayList<String> sentenceArray = new ArrayList<String>();
				
				for (HasWord hwTok : hwSentence) {
					
					// convert HasWord to String and add to Array
					sentenceArray.add(hwTok.word());
				
				}
				
				// Add sentence array to document list
				documentList.add(sentenceArray);
			
			}
			
			
		} catch (Exception e1) {
			// Couldn't open the File
			e1.printStackTrace();
			System.out.println("Couldn't parse: " + filepath + ".");
			
		}
		
		int i = 0;
		
		for (ArrayList<String> s : documentList){
			
			i += 1;
			String num = Integer.toString(i);
			System.out.println("Sentence"+ num + ":");
			
			for (String t : s){
				System.out.println(t);
				
			}
		}
	}
}