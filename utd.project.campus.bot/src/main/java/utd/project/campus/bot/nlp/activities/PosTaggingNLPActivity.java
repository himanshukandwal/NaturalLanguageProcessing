package utd.project.campus.bot.nlp.activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.InvalidFormatException;
import utd.project.campus.bot.NLPProjectException;
import utd.project.campus.bot.model.PartOfSpeechTag;

@SuppressWarnings("rawtypes")
public class PosTaggingNLPActivity extends AbstractNLPActivity {

	public List perform(String... inputs) throws NLPProjectException {
		List<PartOfSpeechTag> responses = new LinkedList<PartOfSpeechTag>();
		inputs = cleanupInput(inputs);
		try {
			InputStream modelStream = loadModel();
			final POSModel posModel = new POSModel(modelStream);
			POSTaggerME posTaggerME = new POSTaggerME(posModel);
			modelStream.close();

			if (inputs != null && inputs.length > 0) {
				String tags[] = posTaggerME.tag(inputs);

				for (String stringTag : tags)
					responses.add(PartOfSpeechTag.valueOf(stringTag));
			}
			posModel.cleanupMetaData();
			
		} catch (InvalidFormatException e) {
			throw new NLPProjectException(e);
		} catch (IOException e) {
			throw new NLPProjectException(e);
		}

		return responses;
	}

	private String[] cleanupInput(String[] inputs) {
		List<String> cleanInputList = new ArrayList<String>();
		for (int index = 0; index < inputs.length; index++) {
			if (inputs[index].trim().length() > 0) {
				cleanInputList.add(inputs[index]);
			}
		}
		return cleanInputList.toArray(new String[0]);
	}

	@Override
	public InputStream loadModel() {
		return getClass().getClassLoader().getResourceAsStream("training-models/en-pos-maxent.bin");
	}

}
