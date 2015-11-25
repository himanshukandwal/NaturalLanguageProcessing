package utd.project.campus.bot.nlp.activities;

import static org.junit.Assert.assertEquals;
import static utd.project.campus.bot.model.PartOfSpeechTag.DT;
import static utd.project.campus.bot.model.PartOfSpeechTag.JJ;
import static utd.project.campus.bot.model.PartOfSpeechTag.NN;
import static utd.project.campus.bot.model.PartOfSpeechTag.NNP;
import static utd.project.campus.bot.model.PartOfSpeechTag.VBZ;

import java.util.List;

import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;

@SuppressWarnings("rawtypes")
public class PosTaggingNLPActivityTest {

	@Test
	public void testPerformTokens() throws NLPProjectException {
		PosTaggingNLPActivity activity = new PosTaggingNLPActivity();
		List responses = activity.perform(new String[]{ "Mike", "Smith", "is", "a", "good", "person" });
		
		assertEquals(6, responses.size());
		assertEquals(NNP, responses.get(0));
		assertEquals(NNP, responses.get(1));
		assertEquals(VBZ, responses.get(2));
		assertEquals(DT, responses.get(3));
		assertEquals(JJ, responses.get(4));
		assertEquals(NN, responses.get(5));
	}
	
	@Test
	public void testPerformToken() throws NLPProjectException {
		PosTaggingNLPActivity activity = new PosTaggingNLPActivity();
		List responses = activity.perform("Mike");
		
		assertEquals(1, responses.size());
		assertEquals(NNP, responses.get(0));
	}

	@Test
	public void testPerformNull() throws NLPProjectException {
		PosTaggingNLPActivity activity = new PosTaggingNLPActivity();
		List responses = activity.perform("");
		
		assertEquals(0, responses.size());
	}

}
