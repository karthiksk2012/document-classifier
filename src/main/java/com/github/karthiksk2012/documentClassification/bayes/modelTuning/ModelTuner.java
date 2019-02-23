package com.github.karthiksk2012.documentClassification.bayes.modelTuning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.karthiksk2012.documentClassification.TaggedDocument;
import com.github.karthiksk2012.documentClassification.bayes.BayesDocumentClassifier;
import com.github.karthiksk2012.documentClassifier.textProcessing.ProcessingConstants;

/**
 * Runs classifier on set of pre-tagged documents and determines the threshold
 * for a good classification
 * 
 * @author karthik
 *
 */
public class ModelTuner {

	Map<String, Double> threshold = new HashMap<>();

	/**
	 * Classifier to run the validation set against
	 */
	BayesDocumentClassifier bayesDocumentClassifier;

	/**
	 * 
	 * @param bayesDocumentClassifier
	 *            the document classifier to run the tests against
	 */
	public ModelTuner(BayesDocumentClassifier bayesDocumentClassifier) {
		this.bayesDocumentClassifier = bayesDocumentClassifier;
		this.tune();
	}

	private void tune() {
		this.threshold = calculateThresholdForAllCategories();
	}

	/**
	 * 
	 * @return a map of category to its corresponding threshold
	 */
	private Map<String, Double> calculateThresholdForAllCategories() {
		Set<String> allTopics = this.bayesDocumentClassifier.getAllCategories();
		Map<String, Double> thresholdMap = new HashMap<String, Double>();
		for (String category : allTopics) {
			thresholdMap.put(
					category,
					getThresholdForCategory(category,
							this.bayesDocumentClassifier));
		}
		return thresholdMap;
	}

	private static Double getThresholdForCategory(String category,
			BayesDocumentClassifier classifier) {
		List<Double> categoryProbability = getCategoryProbability(category,
				classifier.getValidationSet(category), classifier);
		Double meanProbability = categoryProbability.stream()
				.mapToDouble(Double::valueOf).average().orElse(0.0d);
		Double variance = categoryProbability
				.stream()
				.mapToDouble(
						arg -> Math.pow(Math.abs(arg - meanProbability), 2))
				.average().orElse(0.0d);
		Double standardDeviation = Math.sqrt(variance);

		List<Double> zscoreFilteredProbability = categoryProbability
				.stream()
				.filter(prob -> {
					return (Math.abs(meanProbability - prob) / standardDeviation) <= ProcessingConstants.CONST_ZSCORE_LIMIT;
				}).collect(Collectors.toList());
		Double threshHold = zscoreFilteredProbability.stream()
				.mapToDouble(Double::doubleValue).min().orElse(1.0d);
		return threshHold;
	}

	/**
	 * Classify and calculate probability for all the documents in the category
	 */
	private static List<Double> getCategoryProbability(String category,
			List<TaggedDocument> documents, BayesDocumentClassifier classifier) {
		return documents
				.parallelStream()
				.map(arg -> classifier.predictProbability(arg.getWords(),
						category)).collect(Collectors.toList());
	}

	public Map<String, Double> getThreshold() {
		return this.threshold;
	}

}
