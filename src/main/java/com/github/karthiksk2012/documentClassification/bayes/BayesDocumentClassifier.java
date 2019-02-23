package com.github.karthiksk2012.documentClassification.bayes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.karthiksk2012.documentClassification.CategoryClassification;
import com.github.karthiksk2012.documentClassification.DocumentClassifier;
import com.github.karthiksk2012.documentClassification.TaggedDocument;
import com.github.karthiksk2012.documentClassification.WordFrequency;
import com.github.karthiksk2012.documentClassification.bayes.modelTuning.ModelTuner;
import com.github.karthiksk2012.documentClassifier.textProcessing.ProcessingConstants;
import com.github.karthiksk2012.documentClassifier.textProcessing.TextProcessor;

/**
 * A multinomial Naive bayes classifier - a concrete implementation of document
 * classifier overrides the classify method, classifying a document to a
 * category based on feature count
 * 
 * @author karthik
 *
 */
public class BayesDocumentClassifier extends DocumentClassifier {

	/**
	 * Map of category to the minimum threshold of probability a document should
	 * have to be regarded as a true match to the category
	 */
	private Map<String, Double> topicThreshold = new HashMap<String, Double>();

	public BayesDocumentClassifier(List<TaggedDocument> trainingDocuments) {
		super(trainingDocuments);
		this.topicThreshold = new ModelTuner(this).getThreshold();
	}

	/**
	 * Classifies a given document(list of words) into a known category(i.e the
	 * best match category among the categories present in training the set
	 */
	@Override
	public ClassificationResult classify(List<String> document) {
		Set<String> categories = this.getAllCategories();
		Map<String, CategoryClassification> categoryClassificationMap = new HashMap<>();
		for (String category : categories) {
			Double matchProbability = predictProbability(document, category);
			CategoryClassification categoryMatch = new CategoryClassification(
					category, matchProbability, this.getThreshold(category));
			categoryClassificationMap.put(category, categoryMatch);
		}
		ClassificationResult result = new ClassificationResult(
				categoryClassificationMap);
		return result;
	}

	private Double getThreshold(String category) {
		return this.topicThreshold.getOrDefault(category, 0.0d);
	}

	/**
	 * 
	 * @param document
	 *            Untagged document to be classified
	 * @param category
	 *            Category to be matched against
	 * @return probability value representing the relationship of document to
	 *         the category
	 */
	public Double predictProbability(List<String> document, String category) {
		Set<String> prunedDocument = TextProcessor.processDocument(document);
		Double probability = prunedDocument
				.stream()
				.mapToDouble(
						word -> Math.log(getWordProbability(word, category)))
				.sum();
		return probability;
	}

	/**
	 * Retrieves the word count and importance for the specified category
	 * 
	 * @return probability value representing the importance of the word to the
	 *         category
	 */
	private Double getWordProbability(String word, String category) {
		Map<String, WordFrequency> wordCategoryMap = this.wordOccurence
				.getOrDefault(word, new HashMap<String, WordFrequency>());
		Long wordCountInCategory = wordCategoryMap.getOrDefault(category,
				new WordFrequency()).getWordCountInCategory()
				+ ProcessingConstants.CONST_LAPLACE_SMOOTHING_FACTOR;
		Long totalWordCount = wordCategoryMap
				.keySet()
				.stream()
				.mapToLong(
						key -> wordCategoryMap.getOrDefault(key,
								new WordFrequency()).getWordCountInCategory())
				.reduce(0l, (a, b) -> (a + b))
				+ ProcessingConstants.CONST_LAPLACE_SMOOTHING_FACTOR;
		return wordCountInCategory / (double) totalWordCount;
	}

}
