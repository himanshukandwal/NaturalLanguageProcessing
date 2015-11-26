package utd.project.campus.bot.knowledge;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import utd.project.campus.bot.NLPProjectException;

public class KnowledgeProviderTest {
	
	private static KnowledgeProvider knowledgeProvider;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		knowledgeProvider = KnowledgeProvider.getInstance();
	}
	
	@Test
	public void environmentSetupTest() {
		try {
			knowledgeProvider.getKnowledgeProcessor().prepareContext();
			System.out.println(knowledgeProvider.getKnowledgeProcessor().getKnowledgeResourcesParser().getMainKnowledgeGraphMaster().toString(""));
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}
	
	@Test
	public void testAwarenessWithHello() {
		try {
			knowledgeProvider.getKnowledgeProcessor().prepareContext();
			String response = knowledgeProvider.respond("hello how are you".toUpperCase().split("\\s+"));
			
			assertEquals("Hey there, how are you ?", response);
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}

	@Test
	public void testAwarenessWithVersionCheck() {
		try {
			knowledgeProvider.getKnowledgeProcessor().prepareContext();
			String response = knowledgeProvider.respond("Can you tell me what is your version".toUpperCase().split("\\s+"));
			
			assertEquals("I am a first version project", response);
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}
	
	@Test
	public void testAwarenessWithVersionCheck2() {
		try {
			knowledgeProvider.getKnowledgeProcessor().prepareContext();
			String response = knowledgeProvider.respond("what is your version".toUpperCase().split("\\s+"));
			
			assertEquals("I am a first version project", response);
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}
}
