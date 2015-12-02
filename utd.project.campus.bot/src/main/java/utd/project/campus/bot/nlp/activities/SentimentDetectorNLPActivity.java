package utd.project.campus.bot.nlp.activities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import utd.project.campus.bot.NLPProjectException;

public class SentimentDetectorNLPActivity extends AbstractNLPActivity {

	private static SentimentDetectorNLPActivity INSTANCE;
	private SortedMap<String,Integer> sentimentMap;
	private boolean isLoaded;
	
	private SentimentDetectorNLPActivity() {}
	
	public static SentimentDetectorNLPActivity getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SentimentDetectorNLPActivity();
		}
		return INSTANCE;
	}
	
	public SortedMap<String, Integer> getSentimentMap() {
		if (sentimentMap == null) {
			sentimentMap = new TreeMap<String, Integer>(); 
		}
		return sentimentMap;
	}
	
	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}
	
	@SuppressWarnings("rawtypes")
	public List perform(String... inputs) throws NLPProjectException {
		List<String> responses = new ArrayList<String>();
		int overallSentiment = 0;
		boolean containsNot = false;
		try {
			if (! isLoaded()) {
				processSentimentModel(loadModel());
				setLoaded(true);
			}
			
			if (inputs.length > 0) {
				for (String input : inputs) {
					if (getSentimentMap().containsKey(input.toLowerCase()))
						overallSentiment += getSentimentMap().get(input.toLowerCase());

					if (input.equalsIgnoreCase("not"))
						containsNot = true;
				}
			}
			
			if (overallSentiment == 0) {
				responses.add("neutral");
			} else if (overallSentiment > 0 && ! containsNot) {	
				responses.add("positive");
			} else {
				responses.add("negative");
			}
			
		} catch (Exception e) {
			throw new NLPProjectException(e);
		} 
		
		return responses;
	}

	private void processSentimentModel(InputStream loadModel) throws NLPProjectException {
		try {
			BufferedReader sentimentModelBufferedReader = new BufferedReader(new InputStreamReader(loadModel, "UTF-8"));
			
			String line = null;
			while ((line = sentimentModelBufferedReader.readLine()) != null)
				getSentimentMap().put(line.split("\t")[0], Integer.valueOf(line.split("\t")[1]));
		} catch (Exception e) {
			throw new NLPProjectException(" exception while reading sentiment model !", e);
		}
	}

	@Override
	public InputStream loadModel() {
		return getClass().getClassLoader().getResourceAsStream("training-models/Sentiments.txt");
	}

}
