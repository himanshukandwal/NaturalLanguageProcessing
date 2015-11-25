package utd.project.campus.bot.nlp.activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;
import utd.project.campus.bot.NLPProjectException;

public class SentenceDetectorNLPActivity extends AbstractNLPActivity {

	@SuppressWarnings("rawtypes")
	public List perform(String... inputs) throws NLPProjectException {
		List<String> responses = new ArrayList<String>();
		try {
			InputStream modelStream = loadModel();
			SentenceModel model = new SentenceModel(modelStream);
			SentenceDetectorME sdetector = new SentenceDetectorME(model);
			modelStream.close();

			if (inputs.length > 0) {
				String sentences[] = sdetector.sentDetect(inputs[0]);

				if (sentences != null && sentences.length > 0) {
					responses.addAll(Arrays.asList(sentences));
				}	
			}
		} catch (InvalidFormatException e) {
			throw new NLPProjectException(e);
		} catch (IOException e) {
			throw new NLPProjectException(e);
		}
		
		return responses;
	}

	@Override
	public InputStream loadModel() {
		return getClass().getClassLoader().getResourceAsStream("training-models/en-sent.bin");
	}

}
