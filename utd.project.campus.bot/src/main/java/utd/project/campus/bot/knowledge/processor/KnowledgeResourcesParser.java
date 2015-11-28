package utd.project.campus.bot.knowledge.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import utd.project.campus.bot.NLPProjectException;
import utd.project.campus.bot.knowledge.model.Category;
import utd.project.campus.bot.knowledge.model.KnowledgeModel;

public class KnowledgeResourcesParser {

	private static String BASE_DIRECTORY = "content/";
	private static KnowledgeResourcesParser me;
	private ObjectMapper objectMapper;
	private Map<String, KnowledgeModel> knowledgeBank;
	private boolean isKnowledgeGained;
	private KnowledgeGraphMaster mainKnowledgeGraphMaster;

	private KnowledgeResourcesParser() {
	}

	public static KnowledgeResourcesParser getInstance() {
		if (me == null)
			me = new KnowledgeResourcesParser();
		return me;
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

	public KnowledgeGraphMaster getMainKnowledgeGraphMaster() {
		return mainKnowledgeGraphMaster;
	}

	public void setMainKnowledgeGraphMaster(KnowledgeGraphMaster mainKnowledgeGraphMaster) {
		this.mainKnowledgeGraphMaster = mainKnowledgeGraphMaster;
	}

	@SuppressWarnings({ "deprecation" })
	public void gainKnowledge() throws NLPProjectException {
		if (!isKnowledgeGained) {
			try {
				InputStream[] knowledgeInputStreams = getKnowledge();
				int index = 1;
				for (InputStream knowledgeInputStream : knowledgeInputStreams) {
					StringBuffer jsonStringBuffer = new StringBuffer();
					BufferedReader knowledgeBufferedReader = new BufferedReader(
							new InputStreamReader(knowledgeInputStream, "UTF-8"));

					String line = null;
					while ((line = knowledgeBufferedReader.readLine()) != null)
						jsonStringBuffer.append(line).append("\n");

					getKnowledgeBank().put(String.valueOf(index), (KnowledgeModel) getObjectMapper().reader(KnowledgeModel.class)
							.readValue(jsonStringBuffer.toString()));
					
					index ++;
				}
			} catch (JsonProcessingException e) {
				throw new NLPProjectException(" error while gaining knowledge. ", e);
			} catch (IOException e) {
				throw new NLPProjectException(" error while gaining knowledge. ", e);
			}
		}
		isKnowledgeGained = true;
	}

	public void createKnowledgeGraph() {
		if (isKnowledgeGained && getMainKnowledgeGraphMaster() == null) {
			List<Category> mainCategories = new LinkedList<Category>();
			
			for (Iterator<Map.Entry<String, KnowledgeModel>> knowledgeBankEntriesIterator = getKnowledgeBank()
					.entrySet().iterator(); knowledgeBankEntriesIterator.hasNext();) {
				Map.Entry<String, KnowledgeModel> knowledgeBankEntry = knowledgeBankEntriesIterator.next();
				mainCategories.addAll(Arrays.asList(knowledgeBankEntry.getValue().getCategories()));
			}
			
			setMainKnowledgeGraphMaster(new KnowledgeGraphMaster(mainCategories));
		}
	}

	public InputStream[] getKnowledge() throws NLPProjectException {
		String[] knowledgeJsons = new String[] { "awareness.aiml", "library-contacts.aiml", "library.aiml", "university.aiml" };

		List<InputStream> knowledgeResources = new ArrayList<InputStream>();

		for (String knowledgeJson : knowledgeJsons) {
			String knowledgeResource = BASE_DIRECTORY + knowledgeJson;
			InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(knowledgeResource);

			if (resourceAsStream == null)
				throw new NLPProjectException(new RuntimeException(knowledgeResource + " :resource not found"));

			knowledgeResources.add(resourceAsStream);
		}

		return knowledgeResources.toArray(new InputStream[0]);
	}

}