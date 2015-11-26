package utd.project.campus.bot.knowledge;

import utd.project.campus.bot.NLPProjectException;
import utd.project.campus.bot.knowledge.processor.KnowledgeProcessor;

public class KnowledgeProvider {
	
	private static KnowledgeProvider me;
	
	private KnowledgeProcessor knowledgeProcessor;
	
	private KnowledgeProvider() {}
	
	public static KnowledgeProvider getInstance() {
		if (null == me) {
			me = new KnowledgeProvider();
		}
		return me;
	}
	
	public KnowledgeProcessor getKnowledgeProcessor() {
		if (knowledgeProcessor == null)
			knowledgeProcessor = KnowledgeProcessor.getInstance();
		
		return knowledgeProcessor;
	}
	
	public void setKnowledgeProcessor(KnowledgeProcessor knowledgeProcessor) {
		this.knowledgeProcessor = knowledgeProcessor;
	}
	
	public String respond(String[] questionArray) throws NLPProjectException {
		if (questionArray == null || questionArray.length == 0) {
			return null;
		}
		
		String response = null;
		try {
			response = getKnowledgeProcessor().process(questionArray);
		} catch (NLPProjectException e) {
			throw new NLPProjectException(" Exception while responding !", e);
		}
		
		return response;
	}
	
	
}
