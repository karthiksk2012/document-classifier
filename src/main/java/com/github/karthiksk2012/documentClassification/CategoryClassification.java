package com.github.karthiksk2012.documentClassification;

/**
 * Classification of a document to a particular category. This is the result of
 * classifying untagged document to a category.
 * 
 * @author karthik
 *
 */
public class CategoryClassification {
	/**
	 * Predicted category of the classified document, category with the maximum
	 * probability
	 */
	private final String category;
	/**
	 * Predicted log probability of the classified document. The value is
	 * expected to be negative as it is the logarithm of a very small number.
	 * You can take exponential of this value to get the true value, but it will
	 * be a very small value.
	 */
	private final Double logProbability;
	/**
	 * Threshold probability of the category above which a document can be said
	 * to belong to the category
	 */
	private final Double categoryThreshold;

	public CategoryClassification(String category, Double probability,
			Double topicThreshold) {
		super();
		this.category = category;
		this.logProbability = probability;
		this.categoryThreshold = topicThreshold;
	}

	public String getCategory() {
		return category;
	}

	public Double getProbability() {
		return logProbability;
	}

	public Double getTopicThreshold() {
		return categoryThreshold;
	}

	/**
	 * 
	 * @return if the document truly belongs to the category based on whether
	 *         the probability match is more than the threshold for the category
	 */
	public boolean isProbableMatch() {
		return logProbability >= categoryThreshold;
	}

}
