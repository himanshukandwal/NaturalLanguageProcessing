package dev.hxkandwal.research.hmm.viterbi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import dev.hxkandwal.research.hmm.viterbi.model.NLPAssignmentException;
import dev.hxkandwal.research.hmm.viterbi.model.PartOfSpeechTag;
import dev.hxkandwal.research.hmm.viterbi.model.WordTagModel;

public class PartOfSpeechTagAnalyzer {

	private BufferedReader corpusFileBufferedInputReader;
	private BufferedWriter corpusFileBufferedOutputWriter;
	
	private Map<WordTagModel, Integer> originalWordTagModelCountMap;
	private Map<String, Integer> wordCountMap;
	private Set<WordTagModel> bestProbableWordTagModel;
	private Map<WordTagModel, List<String>> incorrectWordTagModelDetailMap;

	private Comparator<Map.Entry<WordTagModel, List<String>>> mapValuesComparator = new Comparator<Map.Entry<WordTagModel, List<String>>>() {
		@Override
		public int compare(Map.Entry<WordTagModel, List<String>> o1, Map.Entry<WordTagModel, List<String>> o2) {
			int returnCode;
			
			if (o1.getValue().size() == o2.getValue().size()) {
				returnCode = 0;
			} else if (o1.getValue().size() > o2.getValue().size()) {
				returnCode = 1;
			} else {
				returnCode = -1;
			}
			return returnCode;
		}
	};
	
	public PartOfSpeechTagAnalyzer() {
		setCorpusFileBufferedInputReader(new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("HW2_F15_NLP6320_POSTaggedTrainingSet.txt"))));
	}

	public Set<WordTagModel> getBestProbableWordTagModel() {
		if (null == bestProbableWordTagModel) {
			bestProbableWordTagModel = new HashSet<>();
		}
		return bestProbableWordTagModel;
	}
	
	public Map<WordTagModel, Integer> getOriginalWordTagModelCountMap() {
		if (null == originalWordTagModelCountMap) {
			originalWordTagModelCountMap = new HashMap<>();
		}
		return originalWordTagModelCountMap;
	}
	
	public Map<String, Integer> getWordCountMap() {
		if (null == wordCountMap) {
			wordCountMap = new HashMap<>();
		}
		return wordCountMap;
	}
	
	public BufferedReader getCorpusFileBufferedInputReader() {
		return corpusFileBufferedInputReader;
	}
	
	public void setCorpusFileBufferedInputReader(BufferedReader corpusFileBufferedInputReader) {
		this.corpusFileBufferedInputReader = corpusFileBufferedInputReader;
	}

	public BufferedWriter getCorpusFileBufferedOutputWriter() {
		return corpusFileBufferedOutputWriter;
	}
	
	public void setCorpusFileBufferedOutputWriter(BufferedWriter corpusFileBufferedOutputWriter) {
		this.corpusFileBufferedOutputWriter = corpusFileBufferedOutputWriter;
	}
	
	public Map<WordTagModel, List<String>> getIncorrectWordTagModelDetailMap() {
		if (null == incorrectWordTagModelDetailMap) {
			incorrectWordTagModelDetailMap = new HashMap<WordTagModel, List<String>>();
		}
		return incorrectWordTagModelDetailMap;
	}
	
	public void readCorpusData()
	{
		try {
			System.out.println(" Started reading corpus !");
			
			StringBuffer corpusStringBuffer = new StringBuffer();
			String line = null;

			while (( line = getCorpusFileBufferedInputReader().readLine()) != null)
				corpusStringBuffer.append(" ").append(line);

			StringTokenizer tokenizer = new StringTokenizer(corpusStringBuffer.toString(), " ");

			while (tokenizer.hasMoreElements()) {
				String corpusWord = (String) tokenizer.nextElement();

				// if it's an empty string, not valid POS format like : abc_<POS-TAG>,  then skip.
				if (corpusWord.matches("^\\s+$") || corpusWord.length() == 0 || !corpusWord.matches(".*\\_[A-Z]{2,4}")) 
					continue;
				
				String[] corpusWordArray = corpusWord.split("\\_");
				PartOfSpeechTag partOfSpeechTag = PartOfSpeechTag.valueOf(corpusWordArray[corpusWordArray.length -1]);
				
				if (partOfSpeechTag == null) {
					System.out.println(" part of speech tag not found = " + corpusWordArray[corpusWordArray.length -1]);
					continue;
				}
				
				StringBuffer corpusWordOnly = new StringBuffer();
				
				int index = 0;
				for (; index < corpusWordArray.length -2; index ++) {
					corpusWordOnly.append(corpusWordArray[index]).append("_");
				}
				corpusWordOnly.append(corpusWordArray[index]);
				
				getWordCountMap().put(corpusWordOnly.toString().toLowerCase(), (getWordCountMap().containsKey(corpusWordOnly.toString().toLowerCase()) 
						? getWordCountMap().get(corpusWordOnly.toString().toLowerCase()) + 1 : 1));
				
				WordTagModel wordTagModel = WordTagModel.get(corpusWordOnly.toString(), partOfSpeechTag);
				
				if (getOriginalWordTagModelCountMap().containsKey(wordTagModel)) {
					getOriginalWordTagModelCountMap().put(wordTagModel, getOriginalWordTagModelCountMap().get(wordTagModel) + 1);
				} else {
					getOriginalWordTagModelCountMap().put(wordTagModel, 1);
				}	
			}
			System.out.println(" Finished reading corpus !");	
			
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
	
	public void estimateCorrectTagForEachWord() {
		/* 	In this we will find the argmax of P(t|w) for each word. 
		 *  The formula is :
		 *  	P(tag|word) = count(tag|word) / count(word) 
		 */
		Set<String> corpusWordSet = getWordCountMap().keySet();
		
		for (Iterator<String> corpusWordSetIterator = corpusWordSet.iterator(); corpusWordSetIterator.hasNext();) {
			String corpusWord = corpusWordSetIterator.next();
			
			Integer corpusWordOccurence = getWordCountMap().get(corpusWord);
			Double maxCorpusWordProbability = 0d;
			WordTagModel bestWordTagModel = null;
			
			Set<WordTagModel> wordTagModels = getOriginalWordTagModelCountMap().keySet();
			for (WordTagModel wordTagModel : wordTagModels) {
				if (!wordTagModel.getWord().equals(corpusWord)) 
					continue;
				
				Double localWordTagProbability = BigDecimal.valueOf(getOriginalWordTagModelCountMap().get(wordTagModel))
						.divide(BigDecimal.valueOf(corpusWordOccurence), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP).doubleValue();
				
				if (localWordTagProbability > maxCorpusWordProbability) {
					maxCorpusWordProbability = localWordTagProbability;
					bestWordTagModel = wordTagModel;
				}
			}
			
			if (null != bestWordTagModel) {
				getBestProbableWordTagModel().add(bestWordTagModel);
				System.out.println(" found best POS TAG - " + bestWordTagModel.getAssociatedPartOfSpeechTag() + " for word - " + bestWordTagModel.getWord());
			}
		}
	}
	
	@SuppressWarnings("serial")
	public void recreateCorpusFileWithBestTagProbabilities() throws NLPAssignmentException {
		try {
			InputStream correctCorpusFileInputStream = getClass().getClassLoader().getResourceAsStream("HW2_F15_NLP6320_POSTaggedTrainingSet.txt");
			File correctCorpusFile = new File(System.getProperty("user.dir") + "/HW2_F15_NLP6320_POSTaggedTrainingSet-AllCorrect.txt");
			File top5correctCorpusFile = new File(System.getProperty("user.dir") + "/HW2_F15_NLP6320_POSTaggedTrainingSet-Top5Correct.txt");
			
			System.out.println(" >> printing totally correct file @ " + correctCorpusFile.getAbsolutePath());
			
			setCorpusFileBufferedInputReader(new BufferedReader(new InputStreamReader(correctCorpusFileInputStream)));
			setCorpusFileBufferedOutputWriter(new BufferedWriter(new FileWriter(correctCorpusFile)));
			
			StringBuffer corpusStringBuffer = new StringBuffer();
			String line = null;

			while (( line = getCorpusFileBufferedInputReader().readLine()) != null)
				corpusStringBuffer.append(" ").append(line);

			StringTokenizer corpusStringBufferTokenizer = new StringTokenizer(corpusStringBuffer.toString(), " ");

			int incorrectWordsWithTagCount = 0;
			while (corpusStringBufferTokenizer.hasMoreElements()) {
				String corpusWord = (String) corpusStringBufferTokenizer.nextElement();

				// if it's an empty string, not valid POS format like : abc_<POS-TAG>,  then skip.
				if (corpusWord.matches("^\\s+$") || corpusWord.length() == 0 || !corpusWord.matches(".*\\_[A-Z]{2,4}")) { 
					getCorpusFileBufferedOutputWriter().write(corpusWord + " ");
					continue;
				}
				
				String[] corpusWordArray = corpusWord.split("\\_");
				PartOfSpeechTag existingPartOfSpeechTag = PartOfSpeechTag.valueOf(corpusWordArray[corpusWordArray.length -1]);

				if (existingPartOfSpeechTag == null) {
					System.out.println(" part of speech tag not found = " + corpusWordArray[corpusWordArray.length -1]);
					getCorpusFileBufferedOutputWriter().write(corpusWord + " ");
					continue;
				}
				
				StringBuffer corpusWordOnly = new StringBuffer();
				int index = 0;
				for (; index < corpusWordArray.length -2; index ++) {
					corpusWordOnly.append(corpusWordArray[index]).append("_");
				}
				corpusWordOnly.append(corpusWordArray[index]);

				PartOfSpeechTag bestProbableWordTag = null;
				for (WordTagModel bestProbableWordTagModel : getBestProbableWordTagModel()) {
					if (bestProbableWordTagModel.getWord().equalsIgnoreCase(corpusWordOnly.toString())) {
						bestProbableWordTag = bestProbableWordTagModel.getAssociatedPartOfSpeechTag();
						break;
					}
				}
				
				WordTagModel currentWordTagModel = WordTagModel.get(corpusWordOnly.toString(), bestProbableWordTag);
				if (existingPartOfSpeechTag != bestProbableWordTag) {
					incorrectWordsWithTagCount ++;
					
					if (getIncorrectWordTagModelDetailMap().containsKey(currentWordTagModel)) {
						getIncorrectWordTagModelDetailMap().get(currentWordTagModel).add(existingPartOfSpeechTag.toString());
					} else {
						getIncorrectWordTagModelDetailMap().put(currentWordTagModel, new ArrayList<String>() {{ add(existingPartOfSpeechTag.name()); }});
					}
				}
				
				getCorpusFileBufferedOutputWriter().write(corpusWordOnly.toString() + "_" + bestProbableWordTag + " ");
			}
			
			getCorpusFileBufferedOutputWriter().flush();
			getCorpusFileBufferedOutputWriter().close();
			
			System.out.println("Retagged original file ! ");
			
			int totalValidTokens = 0;
			for (Integer value :  originalWordTagModelCountMap.values()) 
				totalValidTokens += value;
			
			System.out.println("\n Overall error rate : \n\t\t" + incorrectWordsWithTagCount + " (Incorrect tagged words) \n\t\tper " 
							+ totalValidTokens + " (Total valid tagged words) \n\t\t ==> " + BigDecimal.valueOf(incorrectWordsWithTagCount)
							.divide(BigDecimal.valueOf(totalValidTokens), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP).doubleValue());

			StringBuffer displayBuffer = new StringBuffer(" List of Errorneous words  (TOP 5 ) :: \n\n");
			
			List<Map.Entry<WordTagModel, List<String>>> incorrectWordTagModelEntryList = new LinkedList<Map.Entry<WordTagModel, List<String>>>(getIncorrectWordTagModelDetailMap().entrySet());
			
			Collections.sort(incorrectWordTagModelEntryList, Collections.reverseOrder(mapValuesComparator));
			
			List<Map.Entry<WordTagModel, List<String>>> top5IncorrectWordDetails = new ArrayList<>();
			
			for (int i = 0; i < 5; i++) {
				top5IncorrectWordDetails.add(incorrectWordTagModelEntryList.get(i));
				
				displayBuffer.append("Word : " + incorrectWordTagModelEntryList.get(i).getKey().getWord())
				 .append("\n\t correct tag - " + incorrectWordTagModelEntryList.get(i).getKey().getAssociatedPartOfSpeechTag())
				 .append("\t\t incorrect tags (" + incorrectWordTagModelEntryList.get(i).getValue().size() + ") : ")
				 .append(new HashSet<String>(incorrectWordTagModelEntryList.get(i).getValue())).append("\n");
			}
			System.out.println(displayBuffer.toString());
			// clear buffer
			displayBuffer.setLength(0);
			
			System.out.println("Writing grammer for top 5 mistakes :");
			int top5IncorrectWordsCount = 0;
			for (Map.Entry<WordTagModel, List<String>> entry : top5IncorrectWordDetails) {
				System.out.println("\t adding rule : use tag - " + entry.getKey().getAssociatedPartOfSpeechTag() + " for word - " + entry.getKey().getWord());
				top5IncorrectWordsCount += entry.getValue().size();
			}
			
			setCorpusFileBufferedOutputWriter(new BufferedWriter(new FileWriter(top5correctCorpusFile)));
			corpusStringBufferTokenizer = new StringTokenizer(corpusStringBuffer.toString(), " ");
			
			while (corpusStringBufferTokenizer.hasMoreElements()) {
				String corpusWord = (String) corpusStringBufferTokenizer.nextElement();

				// if it's an empty string, not valid POS format like : abc_<POS-TAG>,  then skip.
				if (corpusWord.matches("^\\s+$") || corpusWord.length() == 0 || !corpusWord.matches(".*\\_[A-Z]{2,4}")) { 
					getCorpusFileBufferedOutputWriter().write(corpusWord + " ");
					continue;
				}
				
				String[] corpusWordArray = corpusWord.split("\\_");
				PartOfSpeechTag existingPartOfSpeechTag = PartOfSpeechTag.valueOf(corpusWordArray[corpusWordArray.length -1]);

				if (existingPartOfSpeechTag == null) {
					System.out.println(" part of speech tag not found = " + corpusWordArray[corpusWordArray.length -1]);
					getCorpusFileBufferedOutputWriter().write(corpusWord + " ");
					continue;
				}
				
				StringBuffer corpusWordOnly = new StringBuffer();
				int index = 0;
				for (; index < corpusWordArray.length -2; index ++) {
					corpusWordOnly.append(corpusWordArray[index]).append("_");
				}
				corpusWordOnly.append(corpusWordArray[index]);
				
				for (Map.Entry<WordTagModel, List<String>> top5IncorrectWordDetailsEntry : top5IncorrectWordDetails) {
					if (top5IncorrectWordDetailsEntry.getKey().getWord().equalsIgnoreCase(corpusWordOnly.toString())) {
						existingPartOfSpeechTag = top5IncorrectWordDetailsEntry.getKey().getAssociatedPartOfSpeechTag();
					}
				}
				
				getCorpusFileBufferedOutputWriter().write(corpusWordOnly.toString() + "_" + existingPartOfSpeechTag + " ");
			}
			
			getCorpusFileBufferedOutputWriter().flush();
			getCorpusFileBufferedOutputWriter().close();
			
			System.out.println(" >> printing top 5 grammer correct file @ " + top5correctCorpusFile.getAbsolutePath());
			
			System.out.println("\n New error rate : \n\t\t" + (incorrectWordsWithTagCount - top5IncorrectWordsCount) + " (Incorrect tagged words) \n\t\tper " 
					+ totalValidTokens + " (Total valid tagged words) \n\t\t ==> " + BigDecimal.valueOf(incorrectWordsWithTagCount - top5IncorrectWordsCount)
					.divide(BigDecimal.valueOf(totalValidTokens), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP).doubleValue());
			
		} catch (Exception exception) {
			try { getCorpusFileBufferedInputReader().close(); } 
			catch (Exception anotherException) { throw new NLPAssignmentException(anotherException); }
			try { if (getCorpusFileBufferedOutputWriter() != null) { getCorpusFileBufferedOutputWriter().close(); }} 
			catch (Exception anotherException) { throw new NLPAssignmentException(anotherException.getMessage(), anotherException); }
		} finally {
			if (getCorpusFileBufferedInputReader() != null) {
				try { getCorpusFileBufferedInputReader().close(); } 
				catch (Exception exception) { throw new NLPAssignmentException(exception); }
			}
			if (getCorpusFileBufferedOutputWriter() != null) {
				try { getCorpusFileBufferedOutputWriter().close(); } 
				catch (Exception exception) { throw new NLPAssignmentException(exception); }
			}
		}
	}
	
	public void displayReadCorpus() {
		Set<Map.Entry<WordTagModel, Integer>> wordTagModelEntries = getOriginalWordTagModelCountMap().entrySet();
		
		StringBuffer displayBuffer = new StringBuffer();
		for (Map.Entry<WordTagModel, Integer> wordTagModelEntry : wordTagModelEntries) {
			displayBuffer.append("\t" + wordTagModelEntry.getKey() + " \t " + wordTagModelEntry.getValue()).append("\n");
		}
		
		System.out.println(displayBuffer.toString());
	}
	
	public static void main(String[] args) {
		PartOfSpeechTagAnalyzer analyzer = new PartOfSpeechTagAnalyzer();
		try {
			analyzer.readCorpusData();

			//analyzer.displayReadCorpus();

			analyzer.estimateCorrectTagForEachWord();

			analyzer.recreateCorpusFileWithBestTagProbabilities();
			
		} catch (NLPAssignmentException exception) {
			exception.printStackTrace();
		}
	}
	
}