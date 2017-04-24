# NegAIT -- An English Negation Annotator

This application annotates English Text for Sentential, Morphological, and Double Negation. The input file is a text file, and the output file is in .xml format.

## Running the Code

The application is run on the Command Line or terminal using the Following commands. The application requires the Stanford Core NLP package.

### Mac/Linux

The jar file delimiter is :

java -cp negate.jar:path-to-stanford-corenlp-3.7.0.jar:path-to-stanford-corenlp-3.7.0-models.jar negate.NegationParser path-to-input-file path-to-new-output-file

	e.g. java -cp negate.jar:/Users/pokea.../stanford-corenlp-3.7.0.jar:/Users/pokea.../stanford-corenlp-3.7.0-models.jar 
	     negate.NegationParser AagenaesSyndrome.txt AagenaesSyndromeNegation
	
### Windows

The jar file delimiter is ;

E:\dir>java -cp negate.jar;path-to-stanford-corenlp-3.7.0.jar;path-to-stanford-corenlp-3.7.0-models.jar negate.NegationParser path-to-input-file path-to-new-output-file


	e.g. E:\lmn\project>java -cp negate.jar;E:/lmn/...stanford-corenlp-3.7.0.jar;E:/lmn/...stanford-corenlp-3.7.0-
	     models.jar negate.NegationParser AagenaesSyndrome.txt AagenaesSyndromeNegation
	    
## Results

The application will return an annoted xml file which flags morphological, sentential, and double negation in the original text file.
