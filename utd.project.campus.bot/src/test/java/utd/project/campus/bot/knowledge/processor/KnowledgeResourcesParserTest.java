package utd.project.campus.bot.knowledge.processor;

import static org.junit.Assert.fail;

import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;
import utd.project.campus.bot.knowledge.processor.KnowledgeResourcesParser;

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
