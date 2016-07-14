package dev.hxkandwal.research.ngrams.bigrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import dev.hxkandwal.research.ngrams.bigrams.model.BigramNode;

/**
 * 
 * @author Himanshu Kandwal
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractBigramComputaion {

	private BufferedReader corpusFileBufferedInputReader;
	private Map<String, Integer> uniqueCorpusInformationMap;
	private Map<Integer, Integer> goodTuringFrequencyDistributionMap;
	private Map<String, Integer> tokenCountMap;
	private Map<BigramNode, Integer> globalBigramNodeAndOccurenceMap;
	private long totalCleanedSentences;

	public final static String START_CODE = "START ";
	public final static String END_CODE = " END";
	
	private Set<String> ignoredTokens = new HashSet<String>() {{ 
		add("`"); add("''"); add("'"); add("."); add(";"); add("``"); 
	}};

	public AbstractBigramComputaion() {
		setCorpusFileBufferedInputReader(new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("corpus.txt"))));
	}

	public BufferedReader getCorpusFileBufferedInputReader() {
		return corpusFileBufferedInputReader;
	}

	public void setCorpusFileBufferedInputReader(BufferedReader corpusFileBufferedInputReader) {
		this.corpusFileBufferedInputReader = corpusFileBufferedInputReader;
	}

	public Map<String, Integer> getUniqueCorpusInformationMap() {
		if (uniqueCorpusInformationMap == null) {
			uniqueCorpusInformationMap = new LinkedHashMap<String, Integer>();
		}
		return uniqueCorpusInformationMap;
	}

	public Map<BigramNode, Integer> getGlobalBigramNodeAndOccurenceMap() {
		if (globalBigramNodeAndOccurenceMap == null) {
			globalBigramNodeAndOccurenceMap = new LinkedHashMap<BigramNode, Integer>();
		}
		return globalBigramNodeAndOccurenceMap;
	}
	
	public Map<String, Integer> getTokenCountMap() {
		if (tokenCountMap == null) {
			tokenCountMap = new HashMap<String, Integer>();
		}
		return tokenCountMap;
	}

	public Map<Integer, Integer> getGoodTuringFrequencyDistributionMap() {
		if (goodTuringFrequencyDistributionMap == null) {
			goodTuringFrequencyDistributionMap = new HashMap<Integer, Integer>();
		}
		return goodTuringFrequencyDistributionMap;
	}
	
	public Set<String> getIgnoredTokens() {
		return ignoredTokens;
	}

	public void cleanUpCorpusInformation()
	{
		try {
			System.out.println(" Started cleaning corpus !");
			StringBuffer corpusStringBuffer = new StringBuffer();

			long totalSentences = 0;
			String line = null;

			while (( line = getCorpusFileBufferedInputReader().readLine()) != null)
				corpusStringBuffer.append(" ").append(line);

			StringTokenizer tokenizer = new StringTokenizer(corpusStringBuffer.toString(), ",");

			while (tokenizer.hasMoreElements()) {
				String corpusSentence = (String) tokenizer.nextElement();
				
				// if it's an empty string, the skip.
				if (corpusSentence.matches("^\\s+$")) 
					continue;
				corpusSentence = START_CODE + corpusSentence + END_CODE;
				totalSentences ++;
				if (getUniqueCorpusInformationMap().containsKey(corpusSentence))
					getUniqueCorpusInformationMap().put(corpusSentence, getUniqueCorpusInformationMap().get(corpusSentence) + 1);
				else
					getUniqueCorpusInformationMap().put(corpusSentence, 1);
			}
			totalCleanedSentences = totalSentences;
			
			System.out.println(" Finished cleaning corpus [total sentences - " + totalSentences + "] !");	
			
		} catch (Exception exception) {
			try { getCorpusFileBufferedInputReader().close(); } 
			catch (Exception anotherException) { }
		} finally {
			if (getCorpusFileBufferedInputReader() != null) {
				try { getCorpusFileBufferedInputReader().close(); } 
				catch (Exception exception) { }
			}
		}
	}

	public void generateBigramModel() throws IOException 
	{
		System.out.println(" Started generating bigram model for corpus [ total sentences - " + totalCleanedSentences + "] !");

		Set<Map.Entry<String, Integer>> uniqueCorpusInformationSentenceEntries = getUniqueCorpusInformationMap().entrySet();

		for (Map.Entry<String, Integer> uniqueCorpusInformationSentenceEntry : uniqueCorpusInformationSentenceEntries) {
			String sentence = uniqueCorpusInformationSentenceEntry.getKey();
			Integer occurences = uniqueCorpusInformationSentenceEntry.getValue();

			Map<BigramNode, Integer> localBigramNodeAndOccurenceMap = computeLocalBigramModel(sentence, occurences);
			Set<Map.Entry<BigramNode, Integer>> localBigramNodeAndOccurenceEntries = localBigramNodeAndOccurenceMap.entrySet();
			
			for (Map.Entry<BigramNode, Integer> localBigramNodeAndOccurenceEntry : localBigramNodeAndOccurenceEntries) {
				BigramNode keyBigramNode = localBigramNodeAndOccurenceEntry.getKey();
				Integer localOccurences = localBigramNodeAndOccurenceEntry.getValue();
				
				if (getGlobalBigramNodeAndOccurenceMap().containsKey(keyBigramNode)) 
					getGlobalBigramNodeAndOccurenceMap().put(keyBigramNode, getGlobalBigramNodeAndOccurenceMap().get(keyBigramNode) + localOccurences);
				else 
					getGlobalBigramNodeAndOccurenceMap().put(keyBigramNode, localOccurences);
			}	
		}

		System.out.println(" generated bigram model !");	
	}
	
	public void generateGoodTuringFrequencyDistributionModel() {
		for (Integer occurenceValue : getGlobalBigramNodeAndOccurenceMap().values()) {
			if (getGoodTuringFrequencyDistributionMap().containsKey(occurenceValue)) {
				getGoodTuringFrequencyDistributionMap().put(occurenceValue, getGoodTuringFrequencyDistributionMap().get(occurenceValue) + 1);
			} else {
				getGoodTuringFrequencyDistributionMap().put(occurenceValue, 1);
			}
		}
	}

	public void displayGlobalBigramModel() {
		Set<Map.Entry<BigramNode, Integer>> globalBigramNodeAndOccurenceEntries = getGlobalBigramNodeAndOccurenceMap().entrySet();
		
		for (Map.Entry<BigramNode, Integer> globalBigramNodeAndOccurenceEntry : globalBigramNodeAndOccurenceEntries) {
			BigramNode keyBigramNode = globalBigramNodeAndOccurenceEntry.getKey();
			Integer occurences = globalBigramNodeAndOccurenceEntry.getValue();
			
			System.out.println(" " + keyBigramNode + " : " + occurences);
		}
	}
	
	public void displayBigramCountModelForInput(String inputLine)
	{
		List<String> validSentenceTokens = removeIgnoredTokensFromInput(inputLine);
		StringBuffer displayScreen = new StringBuffer();
		
		int maxTokenLength = 0;
		for (String validSentenceToken : validSentenceTokens) {
			if (validSentenceToken.length() > maxTokenLength)
				maxTokenLength = validSentenceToken.length();
		}
		
		String tab = (maxTokenLength > 7 ? "\t\t" : "\t");
		displayScreen.append(tab);
		
		for (int index = 0; index < validSentenceTokens.size(); index++) {
			displayScreen.append(validSentenceTokens.get(index)).append("\t");
		}
		
		for (int rowIndex = 0; rowIndex < validSentenceTokens.size(); rowIndex ++) {
			String currentWord = validSentenceTokens.get(rowIndex);
			displayScreen.append("\n").append(currentWord).append(currentWord.length() <= 7 ? tab : "\t");
						
			for (int columnIndex = 0; columnIndex < validSentenceTokens.size(); columnIndex ++) {
				String followingWord = validSentenceTokens.get(columnIndex);
				Integer countValue = getGlobalBigramNodeAndOccurenceMap().get(BigramNode.getBigramNode(currentWord, followingWord));
				
				displayScreen.append((countValue == null ? 0 : countValue.intValue())).append(getTabbedColumn(followingWord));
			}	
		}
		System.out.println(displayScreen.toString());
	}
	
	public List<Map<BigramNode, Double>> computeAllPossibleBigramModel(String sentence) {

		List<Map<BigramNode, Double>> allPossibleBigramModel = new LinkedList<Map<BigramNode, Double>>();
		Map<BigramNode, Double> possibleBigramModel = null;
		
		List<String> validSentenceTokens = removeIgnoredTokensFromInput(sentence);
		
		for (int rowIndex = 0; rowIndex < validSentenceTokens.size(); rowIndex ++) {
			String currentWord = validSentenceTokens.get(rowIndex);
			possibleBigramModel = new LinkedHashMap<BigramNode, Double>();
			
			for (int columnIndex = 0; columnIndex < validSentenceTokens.size(); columnIndex ++) {
				String followingWord = validSentenceTokens.get(columnIndex);
				BigramNode possibleBigramNode = BigramNode.getBigramNode(currentWord, followingWord);
				Integer count = null;
				
				if (getGlobalBigramNodeAndOccurenceMap().containsKey(possibleBigramNode)) {
					count = getGlobalBigramNodeAndOccurenceMap().get(possibleBigramNode);
				} else {
					count = 0;
				}
				
				possibleBigramModel.put(possibleBigramNode, Double.valueOf(count));
			}
			
			allPossibleBigramModel.add(possibleBigramModel);
		}
		
		return allPossibleBigramModel;
	}
	
	public void displayProbablityModel(List<Map<BigramNode, Double>> computedPossiblityBigramModel) {
		
		if (computedPossiblityBigramModel == null || computedPossiblityBigramModel.size() == 0) {
			return;
		}
		
		StringBuffer displayScreen = new StringBuffer();
		List<String> validSentenceTokens = new LinkedList<String>();
		int maxTokenLength = 0;
		
		for (Map.Entry<BigramNode, Double> possibleBigramModel : computedPossiblityBigramModel.get(0).entrySet()) {
			String sentenceToken = possibleBigramModel.getKey().getFollowingWord();
			validSentenceTokens.add(sentenceToken);
	
			if (sentenceToken.length() > maxTokenLength)
				maxTokenLength = sentenceToken.length();
		}
		
		String tab = (maxTokenLength > 7 ? "\t\t" : "\t");
		displayScreen.append(tab);
		
		for (int index = 0; index < validSentenceTokens.size(); index++) {
			displayScreen.append(validSentenceTokens.get(index)).append("\t");
		}
		
		for (int rowIndex = 0; rowIndex < validSentenceTokens.size(); rowIndex ++) {
			String currentWord = validSentenceTokens.get(rowIndex);
			displayScreen.append("\n").append(currentWord).append(currentWord.length() <= 7 ? tab : "\t");
						
			for (int columnIndex = 0; columnIndex < validSentenceTokens.size(); columnIndex ++) {
				String followingWord = validSentenceTokens.get(columnIndex);
				
				Map<BigramNode, Double> possibleBigramModel = computedPossiblityBigramModel.get(rowIndex);
				
				Double probablityValue = possibleBigramModel.get(BigramNode.getBigramNode(currentWord, followingWord));
				displayScreen.append(probablityValue).append(getTabbedColumn(followingWord));
			}	
		}	
		System.out.println(displayScreen.toString());
	}
	
	private String getTabbedColumn(String string) {
		return string.length() > 7 ? "\t\t" : "\t";
	}
	
	private Map<BigramNode, Integer> computeLocalBigramModel(String sentence, Integer occurences) {

		Map<BigramNode, Integer> localBigramModel = new LinkedHashMap<BigramNode, Integer>();
		List<String> validSentenceTokens = removeIgnoredTokensFromInput(sentence);

		// loop through the valid tokens to compute the local bigram model.
		for (int index = 0; index < validSentenceTokens.size() - 1; index++) {
			BigramNode node = new BigramNode(validSentenceTokens.get(index), validSentenceTokens.get(index + 1));
			
			if (localBigramModel.containsKey(node))
				localBigramModel.put(node, localBigramModel.get(node) + 1 * occurences);
			else
				localBigramModel.put(node, 1 * occurences);
		}
		
		for (String validToken : validSentenceTokens)
			getTokenCountMap().put(validToken, (getTokenCountMap().containsKey(validToken) ? getTokenCountMap().get(validToken) + 1 : 1));

		return localBigramModel;
	}
	
	private List<String> removeIgnoredTokensFromInput(String sentence) {
		StringTokenizer sentenceTokenizer = new StringTokenizer(sentence, " ");
		List<String> validSentenceTokens = new ArrayList<String>();

		// remove ignored tokens.
		while (sentenceTokenizer.hasMoreElements()) {
			String sentenceToken = (String) sentenceTokenizer.nextElement();

			if (!getIgnoredTokens().contains(sentenceToken))
				validSentenceTokens.add(sentenceToken);
		}
		return validSentenceTokens;
	}

}