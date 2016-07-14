package dev.hxkandwal.research.hmm.viterbi.model;

public class WordTagModel implements Comparable<WordTagModel> {
	private String word;
	private PartOfSpeechTag associatedPartOfSpeechTag;
	
	public WordTagModel() {	
	}
	
	public WordTagModel(String word, PartOfSpeechTag partOfSpeechTag) {
		this.word = word.toLowerCase();
		this.associatedPartOfSpeechTag = partOfSpeechTag;
	}	
	
	public static WordTagModel get(String word, PartOfSpeechTag partOfSpeechTag) {
		return new WordTagModel(word.toLowerCase(), partOfSpeechTag);
	}
	
	public String getWord() {
		return word;
	}
	
	public PartOfSpeechTag getAssociatedPartOfSpeechTag() {
		return associatedPartOfSpeechTag;
	}

	@Override
	public int compareTo(WordTagModel other) {
		if (other == null) {
			return -1;
		}
		
		int returnCode = 1;
		if (this.word.equalsIgnoreCase(other.getWord()) && this.getAssociatedPartOfSpeechTag() == other.getAssociatedPartOfSpeechTag()) {
			return 0;
		}
		return returnCode;
	}
	
	@Override
	public int hashCode() {
		return word.hashCode() + associatedPartOfSpeechTag.getDefinition().hashCode();
	}

	@Override
	public boolean equals(Object otherWordTagModel) {
		if (otherWordTagModel == null || !(otherWordTagModel instanceof WordTagModel)) {
			return false;
		}
		if (this.getWord().equalsIgnoreCase(((WordTagModel) otherWordTagModel).getWord()) 
				&& this.getAssociatedPartOfSpeechTag() == ((WordTagModel) otherWordTagModel).getAssociatedPartOfSpeechTag()) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return word + "|" + associatedPartOfSpeechTag.name();
	}
	
}