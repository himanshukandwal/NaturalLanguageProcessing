package utd.project.campus.bot.knowledge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {

	@JsonProperty("pattern")
	private String pattern;

	@JsonProperty("template")
	private String template;

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern.trim();
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String[] getMatchPath() {
		String[] pattPath = pattern.split("\\s+");
		
		int m = pattPath.length;
		String[] matchPath = new String[m];
		System.arraycopy(pattPath, 0, matchPath, 0, m);
		
		return matchPath;
	}
	
	public String getMatchPathAsString() {
		String[] pattPath = pattern.split("\\s+");
		
		int m = pattPath.length;
		String[] matchPath = new String[m];
		System.arraycopy(pattPath, 0, matchPath, 0, m);

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < matchPath.length; i++) {
			buffer.append(matchPath[i]).append(" ");
		}
		return buffer.toString();
	}
}
