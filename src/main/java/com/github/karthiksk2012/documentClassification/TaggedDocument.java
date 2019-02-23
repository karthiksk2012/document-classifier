package com.github.karthiksk2012.documentClassification;

import java.util.List;

/**
 * A basic wrapper to hold a piece of text tagged to a category
 * 
 * @author karthik
 *
 */
public class TaggedDocument {
	/**
	 * Category the document belongs to
	 */
	private final String category;

	/**
	 * Document represented by a list of words
	 */
	private final List<String> words;

	public TaggedDocument(String category, List<String> words) {
		super();
		this.category = category;
		this.words = words;
	}

	public String getCategory() {
		return category;
	}

	public List<String> getWords() {
		return words;
	}

}
