package utd.project.campus.bot.knowledge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class University extends KnowledgeModel {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("about")
	private String about;
	
	@JsonProperty("user categories")
	private UniversityUser[] users;
	
	public String getTitle() {
		return title;
	}
	
	@JsonSetter("title")
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAbout() {
		return about;
	}
	
	@JsonSetter("about")
	public void setAbout(String about) {
		this.about = about;
	}
	
	public UniversityUser[] getUsers() {
		return users;
	}
	
	@JsonSetter("user categories")
	public void setUsers(UniversityUser[] users) {
		this.users = users;
	}

}
