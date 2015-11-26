package utd.project.campus.bot.nlp.activities;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;

@SuppressWarnings("rawtypes")
public class NameFindingNLPActivityTest {

	@Test
	public void testPerform() throws NLPProjectException {
		NameFindingNLPActivity activity = new NameFindingNLPActivity();
		List responses = activity.perform(new String[]{ "Mike", "Smith", "is", "a", "good", "person" });
		
		assertEquals(1, responses.size());
		assertEquals("Mike Smith", responses.get(0));
	}

	@Test
	public void testPerformNull() throws NLPProjectException {
		NameFindingNLPActivity activity = new NameFindingNLPActivity();
		List responses = activity.perform("");
		
		assertEquals(0, responses.size());
	}

}
