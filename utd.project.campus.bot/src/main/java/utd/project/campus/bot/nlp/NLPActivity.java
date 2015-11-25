package utd.project.campus.bot.nlp;

import java.util.List;

import utd.project.campus.bot.NLPProjectException;

@SuppressWarnings("rawtypes")
public interface NLPActivity {

	public List perform (String ... input) throws NLPProjectException;

}
