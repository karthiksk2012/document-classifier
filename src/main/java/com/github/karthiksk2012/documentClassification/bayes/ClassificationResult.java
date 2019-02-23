package com.github.karthiksk2012.documentClassification.bayes;

import java.util.Map;

import com.github.karthiksk2012.documentClassification.CategoryClassification;

/**
 * Wrapper for the predicting the classification of unknown document
 * 
 * @author karthik
 *
 */
public class ClassificationResult {

	/**
	 * A map of category and classification result of the document to the
	 * category
	 */
	private final Map<String, CategoryClassification> classifications;

	public ClassificationResult(
			Map<String, CategoryClassification> classifications) {
		super();
		this.classifications = classifications;
	}

	public Map<String, CategoryClassification> getClassifications() {
		return classifications;
	}

	/**
	 * 
	 * @return the category that is the best match for the given document based
	 *         on the probability match to the category
	 */
	public CategoryClassification bestMatch() {
		return this.classifications
				.entrySet()
				.stream()
				.max((entry1, entry2) -> entry1.getValue().getProbability()
						.compareTo(entry2.getValue().getProbability())).get()
				.getValue();

	}

}
