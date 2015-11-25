package utd.project.campus.bot.knowledge;

import static org.junit.Assert.fail;

import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;

public class KnowledgeResourcesParserTest {

	@Test
	public void testGainKnowledge() {
		try {
			KnowledgeResourcesParser.getInstance().gainKnowledge();
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail(" recieved exception.  Detail : " + exception.getMessage());
		}
	}

}
