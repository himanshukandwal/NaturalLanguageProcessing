package utd.project.campus.bot.knowledge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;

public class KnowledgeResourcesProviderTest {
	
	private static KnowledgeResourcesProvider resourceProvider;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		resourceProvider = KnowledgeResourcesProvider.getInstance();
	}
	
	@Test
	public void testGetKnowledgeSize() {
		try {
			assertEquals(1, resourceProvider.getKnowledge().length);
		} catch (NLPProjectException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
