package utd.project.campus.bot.knowledge.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utd.project.campus.bot.knowledge.KnowledgeProvider;

public class Matcher {

	public enum Section {
		PATTERN, THAT, TOPIC;
	}

	private static final String[] ASTERISK = { "*" };

	private final Map<Section, List<String>> sections = new HashMap<Section, List<String>>();

	private KnowledgeProvider callback;

	private String[] input;

	private String[] that;

	private String[] topic;

	private String[] matchPath;

	{
		sections.put(Section.PATTERN, new ArrayList<String>(2));
		sections.put(Section.THAT, new ArrayList<String>(2));
		sections.put(Section.TOPIC, new ArrayList<String>(2));
	}

	public Matcher() {
	}

	public Matcher(KnowledgeProvider callback, String[] input, String[] that, String[] topic) {
		this.callback = callback;
		this.input = input;
		this.that = that;
		this.topic = topic;
		setUpMatchPath(input, that, topic);
	}

	public Matcher(String[] input) {
		this(null, input, ASTERISK, ASTERISK);
	}

	private void appendWildcard(List<String> section, String[] source, int beginIndex, int endIndex) {
		if (beginIndex == endIndex)
			section.add(0, "");
		else
			try {
				//section.add(0, source[beginIndex, endIndex]);
			} catch (Exception e) {
				throw new RuntimeException("Source: {\"" + source + "\", \"" + source
						+ "\"}\n" + "Begin Index: " + beginIndex + "\n" + "End Index: " + endIndex, e);
			}
	}

	private void setUpMatchPath(String[] pattern, String[] that, String[] topic) {
		int m = pattern.length, n = that.length, o = topic.length;
		matchPath = new String[m + 1 + n + 1 + o];
		matchPath[m] = "<THAT>";
		matchPath[m + 1 + n] = "<TOPIC>";

		System.arraycopy(pattern, 0, matchPath, 0, m);
		System.arraycopy(that, 0, matchPath, m + 1, n);
		System.arraycopy(topic, 0, matchPath, m + 1 + n + 1, o);
	}

	public void appendWildcard(int beginIndex, int endIndex) {
		int inputLength = input.length;
		if (beginIndex <= inputLength) {
			appendWildcard(sections.get(Section.PATTERN), input, beginIndex, endIndex);
			return;
		}

		beginIndex = beginIndex - (inputLength + 1);
		endIndex = endIndex - (inputLength + 1);

		int thatLength = that.length;
		if (beginIndex <= thatLength) {
			appendWildcard(sections.get(Section.THAT), that, beginIndex, endIndex);
			return;
		}

		beginIndex = beginIndex - (thatLength + 1);
		endIndex = endIndex - (thatLength + 1);

		int topicLength = topic.length;
		if (beginIndex < topicLength)
			appendWildcard(sections.get(Section.TOPIC), topic, beginIndex, endIndex);
	}

	public String wildcard(Section section, int index) {
		List<String> wildcards = sections.get(section);
		return wildcards.get(index - 1);
	}

	public KnowledgeProvider getCallback() {
		return callback;
	}

	public void setCallback(KnowledgeProvider callback) {
		this.callback = callback;
	}

	public String[] getMatchPath() {
		return matchPath;
	}

	public String getMatchPath(int index) {
		return matchPath[index];
	}

	public int getMatchPathLength() {
		return matchPath.length;
	}
}
