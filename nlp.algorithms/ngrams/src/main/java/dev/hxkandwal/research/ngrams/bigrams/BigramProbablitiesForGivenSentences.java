package dev.hxkandwal.research.ngrams.bigrams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dev.hxkandwal.research.ngrams.bigrams.model.BigramNode;

/**
 * 
 * @author Himanshu Kandwal
 *
 */
public class BigramProbablitiesForGivenSentences extends AbstractBigramComputaion {
	
	private static BigramProbablitiesForGivenSentences object = new BigramProbablitiesForGivenSentences();
	
	public static String statement_1 = START_CODE + "The president has relinquished his control of the company's board." + END_CODE;
	public static String statement_2 = START_CODE + "The chief executive officer said the last year revenue was good." + END_CODE;

	/**
	 * Testing
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		// clean-up input corpus,txt (present in Assignment-2/resources/corpus.txt)
		object.cleanUpCorpusInformation();
		
		// generate bigram count model
		object.generateBigramModel();
		
		// show the generated model
		//me.displayGlobalBigramModel();
		
		object.printInConsole("Statement 1");
		object.computeBigramUnSmoothProbablityModelForInput(statement_1);
		object.clearConsole();
		object.computeBigramAddOneSmoothingProbablityModelForInput(statement_1);
		object.clearConsole();
		object.computeBigramGoodTuringSmoothingProbablityModelForInput(statement_1);
		
		object.printInConsole("Statement 2");
		object.computeBigramUnSmoothProbablityModelForInput(statement_2);
		object.clearConsole();
		object.computeBigramAddOneSmoothingProbablityModelForInput(statement_2);
		
		//me.computeBigramUnSmoothProbablityModelForInput(statement_2);
	}
	
	private void computeBigramUnSmoothProbablityModelForInput(String inputLine) {
		List<Map<BigramNode, Double>> computeAllPossibleBigramModel = computeAllPossibleBigramModel(inputLine);
		
		for (Iterator<Map<BigramNode, Double>> allPossibleBigramModelIterator = computeAllPossibleBigramModel.iterator(); allPossibleBigramModelIterator.hasNext();) {
			Map<BigramNode, Double> possibleBigramModel = allPossibleBigramModelIterator.next();
			
			Set<Map.Entry<BigramNode, Double>> possibleBigramModelEntries = possibleBigramModel.entrySet();
			for (Map.Entry<BigramNode, Double> possibleBigramModelEntry : possibleBigramModelEntries) {
				BigramNode localBigramNode  = possibleBigramModelEntry.getKey();
				Double unsmoothedProbablity = 0d;
				
				// P(w2|w1) = C(w1w2) / C(w1)
				if (getTokenCountMap().containsKey(localBigramNode.getCurrentWord())) {
					unsmoothedProbablity = possibleBigramModelEntry.getValue() / getTokenCountMap().get(localBigramNode.getCurrentWord());
					unsmoothedProbablity = new BigDecimal(unsmoothedProbablity).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				possibleBigramModelEntry.setValue(unsmoothedProbablity);
			}
		}
		
		displayProbablityModel(computeAllPossibleBigramModel);
	}
	
	private void computeBigramAddOneSmoothingProbablityModelForInput(String inputLine) {
		List<Map<BigramNode, Double>> computeAllPossibleBigramModel = computeAllPossibleBigramModel(inputLine);
		
		for (Iterator<Map<BigramNode, Double>> allPossibleBigramModelIterator = computeAllPossibleBigramModel.iterator(); allPossibleBigramModelIterator.hasNext();) {
			Map<BigramNode, Double> possibleBigramModel = allPossibleBigramModelIterator.next();
			
			Set<Map.Entry<BigramNode, Double>> possibleBigramModelEntries = possibleBigramModel.entrySet();
			for (Map.Entry<BigramNode, Double> possibleBigramModelEntry : possibleBigramModelEntries) {
				BigramNode localBigramNode  = possibleBigramModelEntry.getKey();
				Double bigramOccurences = possibleBigramModelEntry.getValue();
				
				Double addOneSmoothedProbablity = 0d;
				
				// (count(word1 word2) + 1) / (count(word1) + V)
				if (getTokenCountMap().containsKey(localBigramNode.getCurrentWord())) {
					Integer lastWordTokenOccurences = getTokenCountMap().get(localBigramNode.getCurrentWord());
					
					addOneSmoothedProbablity = (bigramOccurences + 1) / (lastWordTokenOccurences + getTokenCountMap().size());
					
					addOneSmoothedProbablity = new BigDecimal(addOneSmoothedProbablity).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				possibleBigramModelEntry.setValue(addOneSmoothedProbablity);
			}
		}
		
		displayProbablityModel(computeAllPossibleBigramModel);
	}
	
	private void computeBigramGoodTuringSmoothingProbablityModelForInput(String inputLine) {
		List<Map<BigramNode, Double>> computeAllPossibleBigramModel = computeAllPossibleBigramModel(inputLine);
		generateGoodTuringFrequencyDistributionModel();
		
		int totalNumberOfWordsInVocabulary = 0;
		
		for (Integer count : getTokenCountMap().values()) {
			totalNumberOfWordsInVocabulary += count;
		}
		
		for (Iterator<Map<BigramNode, Double>> allPossibleBigramModelIterator = computeAllPossibleBigramModel.iterator(); allPossibleBigramModelIterator.hasNext();) {
			Map<BigramNode, Double> possibleBigramModel = allPossibleBigramModelIterator.next();
			
			Set<Map.Entry<BigramNode, Double>> possibleBigramModelEntries = possibleBigramModel.entrySet();
			for (Map.Entry<BigramNode, Double> possibleBigramModelEntry : possibleBigramModelEntries) {
				Double bigramOccurences = possibleBigramModelEntry.getValue();
				Double goodTuringDiscountSmoothedProbablity = 0d;

				// C* = (c + 1) (Nc+1  / (Nc))
				
				// unseen bigrom
				if (bigramOccurences == 0) {
					goodTuringDiscountSmoothedProbablity = (1.0 * (getGoodTuringFrequencyDistributionMap().get(1) / totalNumberOfWordsInVocabulary));  
				} else {
					if (getGoodTuringFrequencyDistributionMap().containsKey(bigramOccurences.intValue() + 1) &&
							getGoodTuringFrequencyDistributionMap().containsKey(bigramOccurences.intValue()))
						 goodTuringDiscountSmoothedProbablity = (((bigramOccurences + 1) * getGoodTuringFrequencyDistributionMap().get(bigramOccurences.intValue() + 1)) /
							getGoodTuringFrequencyDistributionMap().get(bigramOccurences.intValue()));
					else if (!getGoodTuringFrequencyDistributionMap().containsKey(bigramOccurences.intValue() + 1) &&
							getGoodTuringFrequencyDistributionMap().containsKey(bigramOccurences.intValue()))
						goodTuringDiscountSmoothedProbablity = ((bigramOccurences + 1) / getGoodTuringFrequencyDistributionMap().get(bigramOccurences.intValue()));
					else
						goodTuringDiscountSmoothedProbablity = ((bigramOccurences + 1) / totalNumberOfWordsInVocabulary);
				}
				
				goodTuringDiscountSmoothedProbablity = new BigDecimal(goodTuringDiscountSmoothedProbablity).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
				possibleBigramModelEntry.setValue(goodTuringDiscountSmoothedProbablity);
				//System.out.println(possibleBigramModelEntry.getKey() + " " + possibleBigramModelEntry.getValue());
			}
		}
		
		displayProbablityModel(computeAllPossibleBigramModel);
	}
	
	private void printInConsole(String textToDisplay) {
		clearConsole();
		
		if (textToDisplay != null) {
			System.out.println("------------------------- " + textToDisplay + " -------------------------");
			clearConsole();
		}
	}
	
	private void clearConsole() {
		System.out.println("\n\n");
	}

}