package utd.project.campus.bot.nlp.activities;

import java.io.InputStream;

public class OrganizationFindingNLPActivity extends NameFindingNLPActivity {

	@Override
	public InputStream loadModel() {
		return getClass().getClassLoader().getResourceAsStream("training-models/en-ner-organization.bin");
	}

}
