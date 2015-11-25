package utd.project.campus.bot.nlp.activities;

import java.io.InputStream;

import utd.project.campus.bot.nlp.NLPActivity;

public abstract class AbstractNLPActivity implements NLPActivity {
	
	public abstract InputStream loadModel();
	
}
