package utd.project.campus.bot.nlp.activities;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;

public class SentimentDetectorNLPActivityTest {

	@Test @SuppressWarnings("rawtypes")
	public void testPostiveSentiment() throws NLPProjectException {
		SentimentDetectorNLPActivity activity = new SentimentDetectorNLPActivity();
		List responses = activity.perform(new String[] { "I", "am", "good", "how", "are", "you" });
		assertEquals("positive", responses.get(0));
	}

	@Test @SuppressWarnings("rawtypes")
	public void testNegativeSentiment() throws NLPProjectException {
		SentimentDetectorNLPActivity activity = new SentimentDetectorNLPActivity();
		List responses = activity.perform(new String[] { "I", "am", "not", "feeling", "good" });
		assertEquals("negative", responses.get(0));
	}
}
