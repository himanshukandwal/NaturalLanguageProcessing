package utd.project.campus.bot.knowledge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {

	@JsonProperty("title")
	private String pattern;
	
	@JsonProperty("title")
	private String template;
	
	public String getPattern() {
		return pattern;
	}
	
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public String getTemplate() {
		return pattern;
	}
	
	public void setTemplate(String template) {
		this.template = template;
	}	
	
}
