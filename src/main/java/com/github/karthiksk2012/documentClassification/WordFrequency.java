package com.github.karthiksk2012.documentClassification;

/**
 * Word count in category and documents
 * 
 * @author karthik
 *
 */
public final class WordFrequency {

	/**
	 * Count of word occurrence in the category
	 */
	private Long wordCountInCategory;

	/**
	 * Number of documents the word occurs in
	 */
	private Long wordOccurenceInDocs;

	public WordFrequency() {
		super();
		this.wordCountInCategory = 0l;
		this.wordOccurenceInDocs = 0l;
	}

	public WordFrequency(Long wordCountInCategory, Long wordOccurenceInDocs) {
		super();
		this.wordCountInCategory = wordCountInCategory;
		this.wordOccurenceInDocs = wordOccurenceInDocs;
	}

	public Long getWordCountInCategory() {
		return wordCountInCategory;
	}

	public Long getWordOccurenceInDocs() {
		return wordOccurenceInDocs;
	}

	public void incrementWordOccurenceInDocsByOne() {
		this.wordOccurenceInDocs++;
	}

	public void incrementWordFrequency(long frequency) {
		this.wordCountInCategory += frequency;
	}

}
