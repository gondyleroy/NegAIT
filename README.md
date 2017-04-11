# NegAIT -- An English Negation Annotator

This application annotates English Text for Sentential, Morphological, and Double Negation.

## Running the Code

java -cp negate.jar:path-to-stanford-corenlp-3.7.0.jar:path-to-stanford-corenlp-3.7.0-models.jar negate.NegationParser path-to-input-file path-to-new-output-file

	e.g. java -cp negate.jar:/Users/pokea...stanford-corenlp-full-2016-10-31/stanford-corenlp-	
	3.7.0.jar:/Users/pokea.../stanford-corenlp-full-2016-10-31/stanford-corenlp-3.7.0-models.jar negate.NegationParser 
	AagenaesSyndrome.txt AagenaesSyndromeNegation
