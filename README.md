# negate

This application is a distributed version of the NegAIT parser.

## Code Architecture

- Main.java does:

	1. Reads in the text to be annotated.
	2. Reads in the Accept and Discard Lexicons.
	3. Opens the File to write out.

	With these files, it invokes the TextRead class.
	
- TextRead.java does:
	
	1. Tokenizes and tags the text using CoreNLP
	2. Converts the Accept and Discard Lexicons to Maps.
	3. Invokes the Sentence class to check whether tokens are in these lists.
	4. Writes out the annotated text to file.

Thats all!
