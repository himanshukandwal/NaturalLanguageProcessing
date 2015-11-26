package utd.project.campus.bot.nlp.activities;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;

@SuppressWarnings("rawtypes")
public class TokenizationNLPActivityTest {

	@Test
	public void testPerform() throws NLPProjectException {
		TokenizationNLPActivity activity = new TokenizationNLPActivity();
		List responses = activity.perform("Hi. How are you? This is Mike.");
		
		assertEquals(10, responses.size());
		assertEquals("is", responses.get(7));
		assertEquals(".", responses.get(9));
	}
	
	@Test
	public void testPerformNull() throws NLPProjectException {
		TokenizationNLPActivity activity = new TokenizationNLPActivity();
		List responses = activity.perform("");
		
		assertEquals(0, responses.size());
	}

}
