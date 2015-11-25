package utd.project.campus.bot.knowledge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.path.json.JsonPath;

import utd.project.campus.bot.NLPProjectException;
import utd.project.campus.bot.knowledge.model.KnowledgeModel;

public class KnowledgeResourcesParser {

	private static KnowledgeResourcesParser me;
	private KnowledgeResourcesProvider resourcesProvider;
	private ObjectMapper objectMapper;
	private String jsonModelpackage = "utd.project.campus.bot.knowledge.model.";
	private Map<String, KnowledgeModel> knowledgeBank;
	private boolean isKnowledgeGained;

	private KnowledgeResourcesParser() {}

	public static KnowledgeResourcesParser getInstance() {
		if (me == null)
			me = new KnowledgeResourcesParser();
		return me;
	}

	public KnowledgeResourcesProvider getResourcesProvider() {
		if (resourcesProvider == null) {
			resourcesProvider = KnowledgeResourcesProvider.getInstance();
		}
		return resourcesProvider;
	}

	public ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
		return objectMapper;
	}

	public Map<String, KnowledgeModel> getKnowledgeBank() {
		if (null == knowledgeBank) {
			knowledgeBank = new HashMap<String, KnowledgeModel>();
		}
		return knowledgeBank;
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public void gainKnowledge() throws NLPProjectException {
		if (!isKnowledgeGained) {
			try {
				InputStream[] knowledgeInputStreams = getResourcesProvider().getKnowledge();

				for (InputStream knowledgeInputStream : knowledgeInputStreams) {
					StringBuffer jsonStringBuffer = new StringBuffer();
					BufferedReader knowledgeBufferedReader = new BufferedReader(
							new InputStreamReader(knowledgeInputStream, "UTF-8"));

					String line = null;
					while ((line = knowledgeBufferedReader.readLine()) != null)
						jsonStringBuffer.append(line).append("\n");

					String classname = JsonPath.from(jsonStringBuffer.toString()).get("class");

					Class jsonModelClass = Class.forName(jsonModelpackage + classname);

					getKnowledgeBank().put(classname, (KnowledgeModel) getObjectMapper().reader(jsonModelClass)
							.readValue(jsonStringBuffer.toString()));
				}
			} catch (ClassNotFoundException e) {
				throw new NLPProjectException(" error while gaining knowledge. ", e);
			} catch (JsonProcessingException e) {
				throw new NLPProjectException(" error while gaining knowledge. ", e);
			} catch (IOException e) {
				throw new NLPProjectException(" error while gaining knowledge. ", e);
			}
		}
		isKnowledgeGained = true;
	}

}