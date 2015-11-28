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
			System.out.println(knowledgeProvider.getKnowledgeProcessor().getKnowledgeResourcesParser()
					.getMainKnowledgeGraphMaster().toString(""));
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}

	@Test
	public void testAwareness() {
		try {
			String response;
			knowledgeProvider.getKnowledgeProcessor().prepareContext();

			response = knowledgeProvider.respond("hello how are you".toUpperCase().split("\\s+"));
			assertEquals("Hey there, how are you ?", response);

			response = knowledgeProvider.respond("so how are you".toUpperCase().split("\\s+"));
			assertEquals("I am good. You tell how are you?", response);
			
			response = knowledgeProvider.respond("so tell me how are you".toUpperCase().split("\\s+"));
			assertEquals("I am good. You tell how are you?", response);
			
			response = knowledgeProvider.respond("and how are you".toUpperCase().split("\\s+"));
			assertEquals("I am good. You tell how are you?", response);
			
			response = knowledgeProvider.respond("so how you doing".toUpperCase().split("\\s+"));
			assertEquals("I hope, I am doing good, how you doing ?", response);
			
			response = knowledgeProvider.respond("so dude how you doing".toUpperCase().split("\\s+"));
			assertEquals("I hope, I am doing good, how you doing ?", response);
			
			response = knowledgeProvider.respond("so what is the name of your university".toUpperCase().split("\\s+"));
			assertEquals("The university is University of Texas at Dallas.", response);
			
			response = knowledgeProvider.respond("so what is the name of my university".toUpperCase().split("\\s+"));
			assertEquals("The university is University of Texas at Dallas.", response);
			
			response = knowledgeProvider.respond("Hi how are you".toUpperCase().split("\\s+"));
			assertEquals("I am good. You tell how are you?", response);
			
			response = knowledgeProvider.respond("So how are you".toUpperCase().split("\\s+"));
			assertEquals("I am good. You tell how are you?", response);
			
			response = knowledgeProvider.respond("I am good too thanks for asking.".toUpperCase().split("\\s+"));
			assertEquals("Good then, how may I assist you in searching for library ?", response);

			response = knowledgeProvider.respond("Can you tell me what is your version".toUpperCase().split("\\s+"));
			assertEquals("I am a first version university bot project", response);
			
			response = knowledgeProvider.respond("what is your version".toUpperCase().split("\\s+"));
			assertEquals("I am a first version university bot project", response);
			
			response = knowledgeProvider.respond("so who has created you".toUpperCase().split("\\s+"));
			assertEquals("Himanshu Kandwal is my creator", response);
			
			response = knowledgeProvider.respond("so can you tell me your model version".toUpperCase().split("\\s+"));
			assertEquals("I am a first version university bot project", response);
			
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}
	
	@Test
	public void testLibraryKnowledge() {
		try {
			String response;
			knowledgeProvider.getKnowledgeProcessor().prepareContext();

			response = knowledgeProvider.respond("so how are you".toUpperCase().split("\\s+"));
			assertEquals("I am good. You tell how are you?", response);
			
			
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}
	
}
