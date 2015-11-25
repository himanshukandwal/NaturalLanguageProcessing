package utd.project.campus.bot.knowledge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UniversityUser extends KnowledgeModel {
	
	@JsonProperty("links")
	private Link[] links;
	
	public Link[] getLinks() {
		return links;
	}
	
	@JsonSetter("links")
	public void setLinks(Link[] links) {
		this.links = links;
	}
}
