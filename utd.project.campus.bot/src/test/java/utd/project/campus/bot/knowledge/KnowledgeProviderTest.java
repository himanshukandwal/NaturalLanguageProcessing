package utd.project.campus.bot.knowledge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
			
			response = knowledgeProvider.respond("what do you know about the university library".toUpperCase().split("\\s+"));
			assertEquals("The University of Texas at Dallas has Eugene McDermott Library, which has the mission of the University of Texas at Dallas by providing maximum access to relevant, authoritative, and scholarly resources.", response);
			
			response = knowledgeProvider.respond("what do you know about the university library location".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is located at following location (32.987796, -96.747634)", response);
			
			response = knowledgeProvider.respond("what do you know what is the university library location".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is located at following location (32.987796, -96.747634)", response);
			
			response = knowledgeProvider.respond("where is the university library".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is located at following location (32.987796, -96.747634)", response);
			
			response = knowledgeProvider.respond("where is the university library located".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is located at following location (32.987796, -96.747634)", response);
			
			response = knowledgeProvider.respond("what do you know about the location of the university library".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is located at following location (32.987796, -96.747634)", response);
			
			response = knowledgeProvider.respond("can you tell me something about the location of the university library".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is located at following location (32.987796, -96.747634)", response);
			
			response = knowledgeProvider.respond("can you tell me something about the location of the library".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is located at following location (32.987796, -96.747634)", response);
			
			response = knowledgeProvider.respond("can you provide me the phone number of the university library".toUpperCase().split("\\s+"));
			assertEquals("For general information, call 972-883-2955", response);
			
			response = knowledgeProvider.respond("can you provide me the phone details of the university library".toUpperCase().split("\\s+"));
			assertEquals("For general information, call 972-883-2955", response);
			
			response = knowledgeProvider.respond("can you provide me the university library phone details".toUpperCase().split("\\s+"));
			assertEquals("For general information, call 972-883-2955", response);
			
			response = knowledgeProvider.respond("can you provide me the university library phone information".toUpperCase().split("\\s+"));
			assertEquals("For general information, call 972-883-2955", response);
			
			response = knowledgeProvider.respond("can you provide me the university library phone number".toUpperCase().split("\\s+"));
			assertEquals("For general information, call 972-883-2955", response);
			
			response = knowledgeProvider.respond("can you tell me about the phone number of the university library".toUpperCase().split("\\s+"));
			assertEquals("For general information, call 972-883-2955", response);
			
			response = knowledgeProvider.respond("can you tell me about the phone details of the university library".toUpperCase().split("\\s+"));
			assertEquals("For general information, call 972-883-2955", response);
			
			response = knowledgeProvider.respond("can you tell me about the university library phone details".toUpperCase().split("\\s+"));
			assertEquals("For general information, call 972-883-2955", response);
			
			response = knowledgeProvider.respond("can you tell me about the university library phone information".toUpperCase().split("\\s+"));
			assertEquals("For general information, call 972-883-2955", response);
			
			response = knowledgeProvider.respond("can you tell me about the university library phone number".toUpperCase().split("\\s+"));
			assertEquals("For general information, call 972-883-2955", response);
			
			response = knowledgeProvider.respond("can you tell me about the university library timings".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is opened Open 24 Hours, other than the days when there are public holidays. For getting to know the holidays, ask me about the list of library holidays.", response);
			
			response = knowledgeProvider.respond("can you tell me about the university library timing".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is opened Open 24 Hours, other than the days when there are public holidays. For getting to know the holidays, ask me about the list of library holidays.", response);
			
			response = knowledgeProvider.respond("can you tell me about the university library opening timings".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is opened Open 24 Hours, other than the days when there are public holidays. For getting to know the holidays, ask me about the list of library holidays.", response);
			
			response = knowledgeProvider.respond("can you tell me about the university library open timing".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is opened Open 24 Hours, other than the days when there are public holidays. For getting to know the holidays, ask me about the list of library holidays.", response);
			
			response = knowledgeProvider.respond("can you tell me about the open timings of the university library".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is opened Open 24 Hours, other than the days when there are public holidays. For getting to know the holidays, ask me about the list of library holidays.", response);
			
			response = knowledgeProvider.respond("can you tell me about the opening timing of the university library".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library is opened Open 24 Hours, other than the days when there are public holidays. For getting to know the holidays, ask me about the list of library holidays.", response);
		
			response = knowledgeProvider.respond("can you tell me about the list of the holidays when library is closed".toUpperCase().split("\\s+"));
			assertEquals("The library is closed on the following days November 26, December 23 to 28, January 1, May 30, July 4 and September 5.", response);
		
			response = knowledgeProvider.respond("can you list the library holidays".toUpperCase().split("\\s+"));
			assertEquals("The library is closed on the following days November 26, December 23 to 28, January 1, May 30, July 4 and September 5.", response);
			
			response = knowledgeProvider.respond("can you list the holidays of the library department".toUpperCase().split("\\s+"));
			assertEquals("The library is closed on the following days November 26, December 23 to 28, January 1, May 30, July 4 and September 5.", response);
			
			response = knowledgeProvider.respond("can you please provide me the library holidays list".toUpperCase().split("\\s+"));
			assertEquals("The library is closed on the following days November 26, December 23 to 28, January 1, May 30, July 4 and September 5.", response);
			
			response = knowledgeProvider.respond("can you tell me about the days when the library is closed".toUpperCase().split("\\s+"));
			assertEquals("The library is closed on the following days November 26, December 23 to 28, January 1, May 30, July 4 and September 5.", response);
			
			response = knowledgeProvider.respond("can you please tell me the days when the library is not open".toUpperCase().split("\\s+"));
			assertEquals("The library is closed on the following days November 26, December 23 to 28, January 1, May 30, July 4 and September 5.", response);

			response = knowledgeProvider.respond("can you please tell me the days when the library does not open".toUpperCase().split("\\s+"));
			assertEquals("The library is closed on the following days November 26, December 23 to 28, January 1, May 30, July 4 and September 5.", response);

			response = knowledgeProvider.respond("can you please tell me the library timings for the thanksgiving".toUpperCase().split("\\s+"));
			assertEquals("Eugene McDermott Library and Callier Library have special hours during the week of Thanksgiving.  McDermott will close at midnight on Wednesday and reopen on Friday morning.", response);

			response = knowledgeProvider.respond("can you please tell me the thanksgiving library timings".toUpperCase().split("\\s+"));
			assertEquals("Eugene McDermott Library and Callier Library have special hours during the week of Thanksgiving.  McDermott will close at midnight on Wednesday and reopen on Friday morning.", response);

		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}
	
	@Test
	public void testUniversityLibraryWorkingKnowledge() {
		try {
			String response;
			knowledgeProvider.getKnowledgeProcessor().prepareContext();

			response = knowledgeProvider.respond("can you please tell me the names of the librarians".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library the main librarians are : Linda Snow, Kreg Walvoord, Matt Makowka, Stephanie Isham, Loreen Henry , Ellen Safley, Chris Edwards", response);
			
			response = knowledgeProvider.respond("can you please tell me in the university library who are working as librarians".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library the main librarians are : Linda Snow, Kreg Walvoord, Matt Makowka, Stephanie Isham, Loreen Henry , Ellen Safley, Chris Edwards", response);
			
			response = knowledgeProvider.respond("can you please tell me the librarians names".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library the main librarians are : Linda Snow, Kreg Walvoord, Matt Makowka, Stephanie Isham, Loreen Henry , Ellen Safley, Chris Edwards", response);
			
			response = knowledgeProvider.respond("can you name the librarians working in the university".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library the main librarians are : Linda Snow, Kreg Walvoord, Matt Makowka, Stephanie Isham, Loreen Henry , Ellen Safley, Chris Edwards", response);
			
			response = knowledgeProvider.respond("can you please share the list of the librarian names who are working in the university".toUpperCase().split("\\s+"));
			assertEquals("The Eugene McDermott Library the main librarians are : Linda Snow, Kreg Walvoord, Matt Makowka, Stephanie Isham, Loreen Henry , Ellen Safley, Chris Edwards", response);
			
			response = knowledgeProvider.respond("can you please tell what are the normal timings of any librarian who are working in the university".toUpperCase().split("\\s+"));
			assertEquals("In general, university librarian working hours on Monday to Thursday are from 9 am to 10 pm and on Friday its from 9 am to 6 pm. While on Saturday, librarian works from 10 am to 7 pm and on Sunday from noon to 9 pm.", response);
			
			response = knowledgeProvider.respond("can you please tell what are the work timings of any librarian who are working in the university".toUpperCase().split("\\s+"));
			assertEquals("In general, university librarian working hours on Monday to Thursday are from 9 am to 10 pm and on Friday its from 9 am to 6 pm. While on Saturday, librarian works from 10 am to 7 pm and on Sunday from noon to 9 pm.", response);
			
			response = knowledgeProvider.respond("can you please tell what are the librarian work timings".toUpperCase().split("\\s+"));
			assertEquals("In general, university librarian working hours on Monday to Thursday are from 9 am to 10 pm and on Friday its from 9 am to 6 pm. While on Saturday, librarian works from 10 am to 7 pm and on Sunday from noon to 9 pm.", response);
			
			response = knowledgeProvider.respond("can you please share the contact of any librarian".toUpperCase().split("\\s+"));
			assertEquals("The University librarian can be reached at contact number 972-883-2643", response);
			
			response = knowledgeProvider.respond("can you please tell something about how to pay the university library fine".toUpperCase().split("\\s+"));
			assertEquals("You can find out about your balance by calling 972-883-2953 or by logging into your UTD Account. Library Fines may also be paid at the Bursar's office located on the 2nd floor of the Student Services Building.", response);
			
			response = knowledgeProvider.respond("can you please tell something about paying the university library fine".toUpperCase().split("\\s+"));
			assertEquals("You can find out about your balance by calling 972-883-2953 or by logging into your UTD Account. Library Fines may also be paid at the Bursar's office located on the 2nd floor of the Student Services Building.", response);
			
			response = knowledgeProvider.respond("can you please tell something about payment of the university library fine".toUpperCase().split("\\s+"));
			assertEquals("You can find out about your balance by calling 972-883-2953 or by logging into your UTD Account. Library Fines may also be paid at the Bursar's office located on the 2nd floor of the Student Services Building.", response);
			
			response = knowledgeProvider.respond("can you please tell something about university library fine payment".toUpperCase().split("\\s+"));
			assertEquals("You can find out about your balance by calling 972-883-2953 or by logging into your UTD Account. Library Fines may also be paid at the Bursar's office located on the 2nd floor of the Student Services Building.", response);
			
			response = knowledgeProvider.respond("can we print in the university library".toUpperCase().split("\\s+"));
			assertEquals("Current UTD students, faculty, and staff must have a valid Comet Card present in order to print. With an Active Comet Card 0.06 Dollar per side for black and white; 0.25 Dollar per side for color", response);
			
			response = knowledgeProvider.respond("what about taking print outs from the university library".toUpperCase().split("\\s+"));
			assertEquals("Current UTD students, faculty, and staff must have a valid Comet Card present in order to print. With an Active Comet Card 0.06 Dollar per side for black and white; 0.25 Dollar per side for color", response);

			response = knowledgeProvider.respond("what are the rules regading taking print outs from the university library".toUpperCase().split("\\s+"));
			assertEquals("Current UTD students, faculty, and staff must have a valid Comet Card present in order to print. With an Active Comet Card 0.06 Dollar per side for black and white; 0.25 Dollar per side for color", response);
			
			response = knowledgeProvider.respond("what are the rules regading printing from the university library".toUpperCase().split("\\s+"));
			assertEquals("Current UTD students, faculty, and staff must have a valid Comet Card present in order to print. With an Active Comet Card 0.06 Dollar per side for black and white; 0.25 Dollar per side for color", response);
			
			response = knowledgeProvider.respond("can we take copy in the university library".toUpperCase().split("\\s+"));
			assertEquals("Current UTD students, faculty, and staff must have a valid Comet Card present in order to copy. With an Active Comet Card 0.06 Dollar per side for black and white; 0.25 Dollar per side for color", response);
			
			response = knowledgeProvider.respond("what about taking copies from the university library".toUpperCase().split("\\s+"));
			assertEquals("Current UTD students, faculty, and staff must have a valid Comet Card present in order to copy. With an Active Comet Card 0.06 Dollar per side for black and white; 0.25 Dollar per side for color", response);

			response = knowledgeProvider.respond("what are the rules regading taking copies from the university library".toUpperCase().split("\\s+"));
			assertEquals("Current UTD students, faculty, and staff must have a valid Comet Card present in order to copy. With an Active Comet Card 0.06 Dollar per side for black and white; 0.25 Dollar per side for color", response);
			
			response = knowledgeProvider.respond("what are the rules regading copying from the university library".toUpperCase().split("\\s+"));
			assertEquals("Current UTD students, faculty, and staff must have a valid Comet Card present in order to copy. With an Active Comet Card 0.06 Dollar per side for black and white; 0.25 Dollar per side for color", response);
			
			response = knowledgeProvider.respond("can we take scan some documents in the university library".toUpperCase().split("\\s+"));
			assertEquals("Scans are free and can be emailed or saved to usb drive.", response);
			
			response = knowledgeProvider.respond("what about taking scanned copies from the university library".toUpperCase().split("\\s+"));
			assertEquals("Scans are free and can be emailed or saved to usb drive.", response);

			response = knowledgeProvider.respond("what are the rules regading scanning from the university library".toUpperCase().split("\\s+"));
			assertEquals("Scans are free and can be emailed or saved to usb drive.", response);
		
		} catch (NLPProjectException exception) {
			exception.printStackTrace();
			fail("Exception while environment setup !");
		}
	}
		
}
