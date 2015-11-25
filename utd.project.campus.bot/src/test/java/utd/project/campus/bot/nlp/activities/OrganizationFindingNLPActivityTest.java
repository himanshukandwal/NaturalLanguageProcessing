package utd.project.campus.bot.nlp.activities;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;

@SuppressWarnings("rawtypes")
public class OrganizationFindingNLPActivityTest {

	@Test
	public void testPerform() throws NLPProjectException {
		LocationFindingNLPActivity activity = new LocationFindingNLPActivity();
		List responses = activity.perform("John is planning to specialize in Electrical Engineering in UC Berkley and pursue a career with IBM.".split("\\ "));
		
		assertEquals(0, responses.size());
		//assertEquals(responses.get(0), "Buffalo New York.");
	}

	@Test
	public void testPerformNull() throws NLPProjectException {
		LocationFindingNLPActivity activity = new LocationFindingNLPActivity();
		List responses = activity.perform("");
		
		assertEquals(0, responses.size());
	}
	
}
