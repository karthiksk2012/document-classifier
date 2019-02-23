package com.github.karthiksk2012.documentClassifier.textProcessing;

public final class ProcessingConstants {
	/**
	 * List of stop words in the English language
	 */
	public static final String[] CONST_STOP_WORDS = { "i", "me", "my",
			"myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
			"yourself", "yourselves", "he", "him", "his", "himself", "she",
			"her", "hers", "herself", "it", "its", "itself", "they", "them",
			"their", "theirs", "themselves", "what", "which", "who", "whom",
			"this", "that", "these", "those", "am", "is", "are", "was", "were",
			"be", "been", "being", "have", "has", "had", "having", "do",
			"does", "did", "doing", "a", "an", "the", "and", "but", "if", "or",
			"because", "as", "until", "while", "of", "at", "by", "for", "with",
			"about", "against", "between", "into", "through", "during",
			"before", "after", "above", "below", "to", "from", "up", "down",
			"in", "out", "on", "off", "over", "under", "again", "further",
			"then", "once", "here", "there", "when", "where", "why", "how",
			"all", "any", "both", "each", "few", "more", "most", "other",
			"some", "such", "no", "nor", "not", "only", "own", "same", "so",
			"than", "too", "very", "s", "t", "can", "will", "just", "don",
			"should", "now" };
	/**
	 * Zscore limit configuration, the standard deviation value below which the
	 * classifications are to be preserved for tuning
	 */
	public static final double CONST_ZSCORE_LIMIT = 2;
	/**
	 * Laplace smoothing Factor - additive smoothing factor as a "fail-safe"
	 * probability
	 */
	public static final int CONST_LAPLACE_SMOOTHING_FACTOR = 1;

}
