package utd.project.campus.bot.knowledge.processor;

import java.util.Map;

import utd.project.campus.bot.NLPProjectException;
import utd.project.campus.bot.knowledge.KnowledgeResourcesParser;
import utd.project.campus.bot.knowledge.model.KnowledgeModel;

public abstract class AbstractKnowledgeProcessor implements KnowledgeProcessor {
	
	private Map<String, ? extends KnowledgeModel> completeKnowledgeBank;
		
	public Map<String, ? extends KnowledgeModel> getCompleteKnowledgeBank() throws NLPProjectException {
		if (null == completeKnowledgeBank) {
			KnowledgeResourcesParser knowledgeResourcesParser = KnowledgeResourcesParser.getInstance();
			knowledgeResourcesParser.gainKnowledge();
			completeKnowledgeBank = knowledgeResourcesParser.getKnowledgeBank();
		}
		return completeKnowledgeBank;
	}
	
}
