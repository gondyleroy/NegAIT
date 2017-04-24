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

### Sample Input

	Aagenaes Syndrome Aagenaes Syndrome isn't a syndrome not characterised by congenital hypoplasia of lymph vessels, which does not cause Lymphedema of the legs and recurrent Cholestasis in infancy, and slow progress to Hepatic Cirrhosis and giant cell hepatitis with fibrosis of the portal tracts. The genetic cause is unknown, but it is autosomal recessively inherited and not the gene is unkown and located to Chromosome 15q1,2. A common feature of the condition is a generalised lymphatic anomaly, which may not be indicative of the defect being lymphangiogenetic in origin1. The condition isn't particularly frequent in southern Norway, where more than half the cases are not reported from, but is found in patients in other parts of Europe and the U.S.. It is named after Oystein Aagenaes, a Norwegian paediatrician.
	
### Sample Output

	<?xml version="1.0" encoding="UTF-8" standalone="no"?>
	<document>
	<sentence doublenegation="true"> Aagenaes Syndrome Aagenaes Syndrome is<sentneg>n't</sentneg>  a syndrome<sentneg>not</sentneg>  characterised by congenital hypoplasia of lymph vessels , which does<sentneg>not</sentneg>  cause Lymphedema of the legs and recurrent Cholestasis in infancy , and slow progress to Hepatic Cirrhosis and giant cell hepatitis with fibrosis of the portal tracts .</sentence>
	<sentence> The genetic cause is<morphneg>unknown</morphneg>  , but it is autosomal recessively inherited and<sentneg>not</sentneg>  the gene is unkown and located to Chromosome 15q1 ,2 .</sentence>
	<sentence> A common feature of the condition is a generalised lymphatic anomaly , which may<sentneg>not</sentneg>  be indicative of the defect being lymphangiogenetic in origin1 .</sentence>
	<sentence> The condition is<sentneg>n't</sentneg>  particularly frequent in southern Norway , where more than half the cases are<sentneg>not</sentneg>  reported from , but is found in patients in other parts of Europe and the U.S. .</sentence>
	<sentence> It is named after Oystein Aagenaes , a Norwegian paediatrician .</sentence>
	</document>
	
