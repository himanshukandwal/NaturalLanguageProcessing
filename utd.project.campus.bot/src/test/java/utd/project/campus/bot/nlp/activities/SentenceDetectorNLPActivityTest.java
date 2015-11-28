package utd.project.campus.bot.nlp.activities;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;

@Ignore
@SuppressWarnings("rawtypes")
public class SentenceDetectorNLPActivityTest {

	@Test
	public void testPerform() throws NLPProjectException {
		SentenceDetectorNLPActivity activity = new SentenceDetectorNLPActivity();
		List responses = activity.perform("Hi. How are you? This is Mike.");
		
		assertEquals(2, responses.size());
		assertEquals("Hi. How are you?", responses.get(0));
		assertEquals("This is Mike.", responses.get(1));
	}

	@Test
	public void testPerformNull() throws NLPProjectException {
		SentenceDetectorNLPActivity activity = new SentenceDetectorNLPActivity();
		List responses = activity.perform("");
		
		assertEquals(0, responses.size());
	}
	
	@Test
	public void testPerformRandomInput1() throws NLPProjectException {
		String input = ".,.,...,.!4asad .A";
		
		SentenceDetectorNLPActivity activity = new SentenceDetectorNLPActivity();
		List responses = activity.perform(input);
		
		assertEquals(1, responses.size());
		assertEquals(input, responses.get(0));
	}
	
	@Test
	public void testPerformRandomInput2() throws NLPProjectException {
		String input = ".,.,...,.!4asad . A";
		
		SentenceDetectorNLPActivity activity = new SentenceDetectorNLPActivity();
		List responses = activity.perform(input);
		
		assertEquals(2, responses.size());
		assertEquals(".,.,...,.!4asad .", responses.get(0));
		assertEquals("A", responses.get(1));
	}
}
