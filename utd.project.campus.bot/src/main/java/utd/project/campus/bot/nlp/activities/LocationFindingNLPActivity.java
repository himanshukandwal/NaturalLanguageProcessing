package utd.project.campus.bot.nlp.activities;

import java.io.InputStream;

public class LocationFindingNLPActivity extends NameFindingNLPActivity {

	@Override
	public InputStream loadModel() {
		return getClass().getClassLoader().getResourceAsStream("training-models/en-ner-location.bin");
	}

}
