package utd.project.campus.bot.knowledge.processor;

import utd.project.campus.bot.NLPProjectException;

public interface KnowledgeProcessor {

	public String extract(String extractionPattern, String ... extractionParameters) throws NLPProjectException;
	
}
