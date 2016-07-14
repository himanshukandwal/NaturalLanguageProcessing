package dev.hxkandwal.research.ngrams.bigrams.model;

public class BigramNode {
	
	private String currentWord;
	private String followingWord;
	
	public BigramNode() {}

	public BigramNode(String currentWord, String followingWord) {
		super();
		this.currentWord = currentWord;
		this.followingWord = followingWord;
	}
	
	public static BigramNode getBigramNode(String currentWord, String followingWord) {
		return new BigramNode(currentWord, followingWord);
	}

	public String getCurrentWord() {
		return currentWord;
	}

	public void setCurrentWord(String previousWord) {
		this.currentWord = previousWord;
	}

	public String getFollowingWord() {
		return followingWord;
	}

	public void setFollowingWord(String followingWord) {
		this.followingWord = followingWord;
	}

	@Override
	public int hashCode() {
		return currentWord.hashCode() + followingWord.hashCode();
	}

	@Override
	public boolean equals(Object otherBigram) {
		 
		if (otherBigram == null || !(otherBigram instanceof BigramNode)) {
			return false;
		}
		
		if (this.getCurrentWord().equals(((BigramNode) otherBigram).getCurrentWord()) 
				&& this.getFollowingWord().equals(((BigramNode) otherBigram).getFollowingWord())) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return getFollowingWord() + "|" + getCurrentWord();
	}
	
}
