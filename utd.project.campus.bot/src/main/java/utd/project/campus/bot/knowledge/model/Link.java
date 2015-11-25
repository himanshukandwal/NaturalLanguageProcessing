package utd.project.campus.bot.knowledge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Link extends KnowledgeModel {

	@JsonProperty("name")
	private String linkName;
	
	public String getLinkName() {
		return linkName;
	}
	
	@JsonSetter("name")
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
}
