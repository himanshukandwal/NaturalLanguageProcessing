package utd.project.campus.bot.nlp.activities;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;

@SuppressWarnings({"rawtypes"})
public class LocationFindingNLPActivityTest {

	@Test
	public void testPerform() throws NLPProjectException {
		LocationFindingNLPActivity activity = new LocationFindingNLPActivity();
		List responses = activity.perform("This is Indian which is in Buffalo New York.".split("\\ "));
		
		assertEquals(1, responses.size());
		assertEquals("Buffalo New York.", responses.get(0));
	}

	@Test
	public void testPerformNull() throws NLPProjectException {
		LocationFindingNLPActivity activity = new LocationFindingNLPActivity();
		List responses = activity.perform("");
		
		assertEquals(0, responses.size());
	}
	
}
