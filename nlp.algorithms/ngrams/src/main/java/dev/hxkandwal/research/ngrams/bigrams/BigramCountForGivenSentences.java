package dev.hxkandwal.research.ngrams.bigrams;

import java.io.IOException;

/**
 * 
 * @author Himanshu Kandwal
 *
 */
public class BigramCountForGivenSentences extends AbstractBigramComputaion {
	
	private static BigramCountForGivenSentences me = new BigramCountForGivenSentences();
	
	public final static String statement_1 = "The president has relinquished his control of the company's board.";
	public final static String statement_2 = "The chief executive officer said the last year revenue was good.";

	/**
	 * Testing
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		// clean-up input corpus,txt (present in Assignment-2/resources/corpus.txt)
		me.cleanUpCorpusInformation();
		
		// generate bigram count model
		me.generateBigramModel();
		
		// show the generated model
		me.displayGlobalBigramModel();
		
		System.out.println(" ------------------------- Statement 1  ------------------------- ");
		
		me.displayBigramCountModelForInput(statement_1);
		
		System.out.println(" ------------------------- Statement 2  ------------------------- ");
		
		me.displayBigramCountModelForInput(statement_2);
	}

}
