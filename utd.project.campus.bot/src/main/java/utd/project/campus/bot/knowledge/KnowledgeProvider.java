package utd.project.campus.bot.knowledge;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
	
			if (response != null && response.contains("${")) {
				int searchParamStartIndex = response.indexOf("${") + 2;
				int searchParamEndIndex = response.indexOf("}");
				
				String searchParam = response.substring(searchParamStartIndex, searchParamEndIndex);
				String searchReplacementValue = "";
				
				if (searchParam.equals("date")) {
					searchReplacementValue = String.valueOf((new SimpleDateFormat("EEEEEEE, dd MMMMMMM yyyy")).format(Calendar.getInstance().getTime()));
				}
				else if (searchParam.equals("time")) {
					searchReplacementValue = String.valueOf((new SimpleDateFormat("hh mm aaa")).format(Calendar.getInstance().getTime()));
				}
				else if (searchParam.equals("library_location")) {
					searchReplacementValue = String.valueOf("(32.987796, -96.747634)");
				}
				
				response = response.replace("${" + searchParam + "}", searchReplacementValue);
			}
		} catch (NLPProjectException e) {
			throw new NLPProjectException(" Exception while responding !", e);
		}
		
		return response;
	}
	
	
}
