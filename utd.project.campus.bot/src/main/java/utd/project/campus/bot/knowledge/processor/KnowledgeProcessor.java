package utd.project.campus.bot.knowledge.processor;

import utd.project.campus.bot.NLPProjectException;
import utd.project.campus.bot.knowledge.model.Category;

public class KnowledgeProcessor {

	private KnowledgeResourcesParser knowledgeResourcesParser;
	private static KnowledgeProcessor INSTANCE;
	
	private KnowledgeProcessor() {}
	
	public static KnowledgeProcessor getInstance() {
		if (INSTANCE == null)
			INSTANCE = new KnowledgeProcessor();
			
		return INSTANCE;
	}
	
	public KnowledgeResourcesParser getKnowledgeResourcesParser() {
		if (knowledgeResourcesParser == null) {
			knowledgeResourcesParser = KnowledgeResourcesParser.getInstance();
		}
		return knowledgeResourcesParser;
	}
	
	public String process(String[] questionArray) throws NLPProjectException {
		try {
			prepareContext();
			Matcher match = new Matcher(questionArray);
			Category responseCategory = getKnowledgeResourcesParser().getMainKnowledgeGraphMaster().match(match);
			return (responseCategory != null ? responseCategory.getTemplate() : null);
		} catch (NLPProjectException e) {
			throw new NLPProjectException(" Exception while processing knowledge !" ,e);
		}	
	}
	
	public void prepareContext() throws NLPProjectException {
		try {
			getKnowledgeResourcesParser().gainKnowledge();
			getKnowledgeResourcesParser().createKnowledgeGraph();
		} catch (NLPProjectException e) {
			throw new NLPProjectException(" Exception while processing knowledge !" ,e);
		}
	}
	
}
