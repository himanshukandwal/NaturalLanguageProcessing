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
			assertEquals("Hey there, I am good. You tell how are you?", response);

			response = knowledgeProvider.respond("so how are you".toUpperCase().split("\\s+"));
			assertEquals("Hey there, I am good. You tell how are you?", response);
			
			response = knowledgeProvider.respond("so tell me how are you".toUpperCase().split("\\s+"));
			assertEquals("Hey there, I am good. You tell how are you?", response);
			
			response = knowledgeProvider.respond("so tell me how are you doing today system".toUpperCase().split("\\s+"));
			assertEquals("Hey there, I am good. You tell how are you?", response);
			
			response = knowledgeProvider.respond("and how are you".toUpperCase().split("\\s+"));
			assertEquals("Hey there, I am good. You tell how are you?", response);
			
			response = knowledgeProvider.respond("so how you doing".toUpperCase().split("\\s+"));
			assertEquals("I hope, I am doing good, how you doing ?", response);
			
			response = knowledgeProvider.respond("so dude how you doing".toUpperCase().split("\\s+"));
			assertEquals("I hope, I am doing good, how you doing ?", response);
			
			response = knowledgeProvider.respond("so what is the name of your university".toUpperCase().split("\\s+"));
			assertEquals("The university is University of Texas at Dallas.", response);
			
			response = knowledgeProvider.respond("so what is the name of my university".toUpperCase().split("\\s+"));
			assertEquals("The university is University of Texas at Dallas.", response);
			
			response = knowledgeProvider.respond("Hi how are you".toUpperCase().split("\\s+"));
			assertEquals("Hey there, I am good. You tell how are you?", response);
			
			response = knowledgeProvider.respond("So how are you".toUpperCase().split("\\s+"));
			assertEquals("Hey there, I am good. You tell how are you?", response);
			
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
			
			response = knowledgeProvider.respond("so can you tell me what is nlp".toUpperCase().split("\\s+"));
            assertEquals("NLP stands for Neuro-Linguistic Programming", response); 
			
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}
	
	@Test
	public void testUniversityKnowledge() {
		try {
			String response;
			knowledgeProvider.getKnowledgeProcessor().prepareContext();

			response = knowledgeProvider.respond("so can you please tell me something about the university".toUpperCase().split("\\s+"));
			assertEquals("The University of Texas at Dallas is a public research university in the University of Texas System. The main campus is in the Richardson, Texas, Telecom Corridor, 18 miles north of downtown Dallas.", response);
			
			response = knowledgeProvider.respond("so can you please tell me something about the university achievements ".toUpperCase().split("\\s+"));
			assertEquals("Recently Dr. Aziz Sancar, who earned his PhD in molecular and cell biology from UT Dallas in 1977, is one of three scientists who received the 2015 Nobel Prize in Chemistry.", response);

			response = knowledgeProvider.respond("what are the recent university achievements ".toUpperCase().split("\\s+"));
			assertEquals("Recently Dr. Aziz Sancar, who earned his PhD in molecular and cell biology from UT Dallas in 1977, is one of three scientists who received the 2015 Nobel Prize in Chemistry.", response);

			response = knowledgeProvider.respond("list some of the recent university achievements ".toUpperCase().split("\\s+"));
			assertEquals("Recently Dr. Aziz Sancar, who earned his PhD in molecular and cell biology from UT Dallas in 1977, is one of three scientists who received the 2015 Nobel Prize in Chemistry.", response);

			response = knowledgeProvider.respond("what is the location of the university".toUpperCase().split("\\s+"));
			assertEquals("The main campus of university of Texas at Dallas is in the Richardson, Texas.", response);

			response = knowledgeProvider.respond("what is the location of the university main campus".toUpperCase().split("\\s+"));
			assertEquals("The main campus of university of Texas at Dallas is in the Richardson, Texas.", response);
	
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}
	
	@Test
	public void testUniversityLibraryKnowledge() {
		try {
			String response;
			knowledgeProvider.getKnowledgeProcessor().prepareContext();

			response = knowledgeProvider.respond("so can you please tell me something about the university library".toUpperCase().split("\\s+"));
			assertEquals("The University of Texas at Dallas has Eugene McDermott Library, which has the mission of the University of Texas at Dallas by providing maximum access to relevant, authoritative, and scholarly resources.", response);
			
				
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}
}
