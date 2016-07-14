package dev.hxkandwal.research.ngrams.bigrams;

import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author Himanshu Kandwal
 *
 */
public class BigramCountForAnyInput extends AbstractBigramComputaion {
	
	private static BigramCountForAnyInput me = new BigramCountForAnyInput();

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
		
		System.out.println("Please enter any two words : ");
		Scanner inputScanner = new Scanner(System.in);
		
		while (inputScanner.hasNextLine()) {
			String inputLine = (String) inputScanner.nextLine();
			
			if (!inputLine.matches("^\\s+$")) {
				me.displayBigramCountModelForInput(inputLine);
			}
		}
		
		inputScanner.close();
	}

}
