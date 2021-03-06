package utd.project.campus.bot.knowledge.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import utd.project.campus.bot.knowledge.processor.KnowledgeResourcesParser;

public class CategoryTest {

	@Test
	public void test() {
		try {
			KnowledgeResourcesParser parser = KnowledgeResourcesParser.getInstance();
			parser.gainKnowledge();
			
			assertEquals(4, parser.getKnowledgeBank().size());
			assertEquals("* WHO ARE YOU *", parser.getKnowledgeBank().values().toArray(new KnowledgeModel[0])[0].getCategories()[0].getMatchPathAsString().trim());
		} catch (Exception exception) {
			exception.printStackTrace();
			fail();
		}
	}

}
