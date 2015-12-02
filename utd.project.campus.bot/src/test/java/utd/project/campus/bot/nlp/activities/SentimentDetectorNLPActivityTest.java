package utd.project.campus.bot.nlp.activities;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;

public class SentimentDetectorNLPActivityTest {

	private SentimentDetectorNLPActivity activity = SentimentDetectorNLPActivity.getInstance();
	
	@Test @SuppressWarnings("rawtypes")
	public void testPostiveSentiment() throws NLPProjectException {
		List responses = activity.perform(new String[] { "I", "am", "good", "how", "are", "you" });
		assertEquals("positive", responses.get(0));
	}

	@Test @SuppressWarnings("rawtypes")
	public void testNegativeSentiment() throws NLPProjectException {
		List responses = activity.perform(new String[] { "I", "am", "not", "feeling", "good" });
		assertEquals("negative", responses.get(0));
	}
	
	@Test @SuppressWarnings("rawtypes")
	public void testAndroidResponse() throws NLPProjectException {
		List responses = activity.perform(" Good then, how may I assist you in searching for library ?".trim().replaceAll("[^a-zA-Z ]", "").split("\\s+"));
		assertEquals("positive", responses.get(0));
	}
}
