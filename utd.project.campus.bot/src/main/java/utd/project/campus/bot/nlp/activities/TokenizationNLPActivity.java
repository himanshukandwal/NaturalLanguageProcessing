package utd.project.campus.bot.nlp.activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import utd.project.campus.bot.NLPProjectException;

public class TokenizationNLPActivity extends AbstractNLPActivity {

	@SuppressWarnings("rawtypes")
	public List perform(String... inputs) throws NLPProjectException {
		List<String> responses = new ArrayList<String>();
		try {
			InputStream modelStream = loadModel();
			TokenizerModel model = new TokenizerModel(modelStream);
			Tokenizer tokenizer = new TokenizerME(model);
			modelStream.close();

			if (inputs != null && inputs.length > 0) {
				String tokens[] = tokenizer.tokenize(inputs[0]);

				if (tokens != null && tokens.length > 0) {
					responses.addAll(Arrays.asList(tokens));
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
		return getClass().getClassLoader().getResourceAsStream("training-models/en-token.bin");
	}

}
