package utd.project.campus.bot.nlp.activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;
import utd.project.campus.bot.NLPProjectException;

@SuppressWarnings({ "rawtypes" })
public class NameFindingNLPActivity extends AbstractNLPActivity {

	public List perform(String... inputs) throws NLPProjectException {
		List<String> responses = new ArrayList<String>();
		try {
			InputStream modelStream = loadModel();
			TokenNameFinderModel model = new TokenNameFinderModel(modelStream);
			NameFinderME nameFinder = new NameFinderME(model);
			modelStream.close();

			if (inputs != null && inputs.length > 0) {
				Span nameSpans[] = nameFinder.find(inputs);

				if (nameSpans != null && nameSpans.length > 0) {

					for (Span nameSpan : nameSpans) {
						StringBuffer name = new StringBuffer();

						for (int index = nameSpan.getStart(); index < nameSpan.getEnd(); index++) {
							name.append(inputs[index]);

							if (index + 1 != nameSpan.getEnd())
								name.append(" ");
						}
						responses.add(name.toString());
					}
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
		return getClass().getClassLoader().getResourceAsStream("training-models/en-ner-person.bin");
	}

}
