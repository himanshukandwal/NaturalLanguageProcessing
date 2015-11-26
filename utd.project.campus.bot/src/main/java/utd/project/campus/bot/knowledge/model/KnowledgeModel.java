package utd.project.campus.bot.knowledge.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class KnowledgeModel {

	@JsonProperty("class")
	private String classType;
	
	@JsonProperty("about")
	private String about;
	
	@JsonProperty("categories")
	private Category[] categories;
	
	@JsonSetter("class")
	public void setClassType(String classType) {
		this.classType = classType;
	}
	
	@JsonGetter("class")
	public String getClassType() {
		return classType;
	}

	@JsonGetter("about")
	public String getAbout() {
		return about;
	}
	
	@JsonSetter("about")
	public void setAbout(String about) {
		this.about = about;
	}

	@JsonGetter("categories")
	public Category[] getCategories() {
		return categories;
	}

	@JsonSetter("categories")
	public void setCategories(Category[] categories) {
		this.categories = categories;
	}
	
}
