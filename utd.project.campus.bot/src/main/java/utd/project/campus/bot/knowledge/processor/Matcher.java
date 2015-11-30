package utd.project.campus.bot.knowledge.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utd.project.campus.bot.knowledge.KnowledgeProvider;

public class Matcher {

	public enum Section {
		PATTERN;
	}

	private final Map<Section, List<String>> sections = new HashMap<Section, List<String>>();
	private KnowledgeProvider callback;
	private String[] input;
	private String[] matchPath;
	private boolean isExtended;

	{
		sections.put(Section.PATTERN, new ArrayList<String>(2));
	}

	public Matcher() {}

	public Matcher(KnowledgeProvider callback, String[] input) {
		this.callback = callback;
		this.input = input;
		setUpMatchPath(input);
	}

	public Matcher(String[] input) {
		this(null, input);
	}

	public boolean isExtended() {
		return isExtended;
	}

	private void appendWildcard(List<String> section, String[] source, int beginIndex, int endIndex) {
		if (beginIndex == endIndex)
			section.add(0, "");
		else
			try {
				// section.add(0, source[beginIndex, endIndex]);
			} catch (Exception e) {
				throw new RuntimeException("Source: {\"" + source + "\", \"" + source + "\"}\n" + "Begin Index: "
						+ beginIndex + "\n" + "End Index: " + endIndex, e);
			}
	}

	private void setUpMatchPath(String[] pattern) {
		int m = pattern.length;
		matchPath = new String[m];
		System.arraycopy(pattern, 0, matchPath, 0, m);

	}

	public void extendMatchPath(String matchPathElement) {
		if (!isExtended) {
			int currentMatchPathLength = matchPath.length;
			String[] extendedMatchPath = new String[currentMatchPathLength + 1];
			System.arraycopy(matchPath, 0, extendedMatchPath, 0, currentMatchPathLength - 1);
			extendedMatchPath[currentMatchPathLength] = matchPathElement;
			setUpMatchPath(extendedMatchPath);
			isExtended = true;
		}
	}

	public void appendWildcard(int beginIndex, int endIndex) {
		int inputLength = input.length;
		if (beginIndex <= inputLength) {
			appendWildcard(sections.get(Section.PATTERN), input, beginIndex, endIndex);
			return;
		}

		beginIndex = beginIndex - (inputLength + 1);
		endIndex = endIndex - (inputLength + 1);
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
