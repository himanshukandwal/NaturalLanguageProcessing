package utd.project.campus.bot.knowledge;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import utd.project.campus.bot.NLPProjectException;

public class KnowledgeResourcesProvider {
	
	private static KnowledgeResourcesProvider me;
	private static String BASE_DIRECTORY = "content/";
	
	private KnowledgeResourcesProvider() {}
	
	public static KnowledgeResourcesProvider getInstance() {
		if (null == me) {
			me = new KnowledgeResourcesProvider();
		}
		return me;
	}
	
	public InputStream[] getKnowledge() throws NLPProjectException {
		String[] knowledgeJsons = new String[] {"university.json"
				//, "directory.json"
				};
		
	    List<InputStream> knowledgeResources = new ArrayList<InputStream>();
	    
	    for (String knowledgeJson : knowledgeJsons) {
	    	String knowledgeResource = BASE_DIRECTORY + knowledgeJson;
			InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(knowledgeResource);
			
	    	if (resourceAsStream == null)
	    		throw new NLPProjectException(new RuntimeException(knowledgeResource + " :resource not found"));
	    	
	    	knowledgeResources.add(resourceAsStream);
		}
	    
	    return knowledgeResources.toArray(new InputStream[0]);
	}

}
