package com.github.karthiksk2012.documentClassification;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.karthiksk2012.documentClassification.bayes.ClassificationResult;
import com.github.karthiksk2012.documentClassifier.textProcessing.TextProcessor;

/**
 * Abstract base extended by any classifier. This class provides skeletal
 * functionalities useful for common document classifiers, such as building a
 * feature count from the training set documents. The classify function must be
 * implemented by the concrete classifier.
 * 
 * @author karthik
 *
 */
public abstract class DocumentClassifier {

	/**
	 * Raw set of tagged documents for training the model
	 */
	private final List<TaggedDocument> trainingSet;
	
	/**
	 * Raw set of tagged documents used for parameter fitting or validation
	 */
	private final List<TaggedDocument> validationSet;

	/**
	 * A mapping of every word in the training set to its occurrences in
	 * individual documents and in the category
	 */
	protected Map<String, Map<String, WordFrequency>> wordOccurence = new HashMap<>();

	/**
	 * Construct a new classifier with the training data
	 * 
	 * @param trainingDocuments
	 */
	protected DocumentClassifier(List<TaggedDocument> trainingDocuments) {
		super();
		Collections.shuffle(trainingDocuments);
		List<List<TaggedDocument>> splitSet = TextProcessor.splitCollectionAt(
				trainingDocuments, 80);
		this.trainingSet = splitSet.get(0);
		this.validationSet = splitSet.get(1);
		this.learn();
	}

	/**
	 * Parses all the training documents and builds the feature count map
	 */
	private void learn() {
		for (TaggedDocument taggedDoc : this.trainingSet) {
			Set<String> processedDoc = TextProcessor.processDocument(taggedDoc
					.getWords());
			for (String word : processedDoc) {
				Long wordFrequency = TextProcessor
						.wordCount(word, processedDoc);
				learnWord(taggedDoc.getCategory(), word, wordFrequency);
			}
		}
	}

	/**
	 * Adds every word from the training documents and its corresponding
	 * occurrence count with respect to both frequency in documents and in
	 * category it belongs to
	 * 
	 * @param category
	 *            Category of the document the word appeared in
	 * @param word
	 *            the word
	 * @param wordFrequency
	 *            frequency of the word in the document
	 */
	private void learnWord(String category, String word, Long wordFrequency) {

		Map<String, WordFrequency> wordMap = this.wordOccurence.getOrDefault(
				word, new HashMap<String, WordFrequency>());
		WordFrequency wf = wordMap.getOrDefault(category, new WordFrequency());
		wf.incrementWordFrequency(wordFrequency);
		wf.incrementWordOccurenceInDocsByOne();
		wordMap.put(category, wf);
		this.wordOccurence.put(word, wordMap);
	}

	/**
	 * The classify functionality to be implemented by the concrete class
	 * 
	 * @param document
	 *            Document in the form of list of words to classify
	 */
	public abstract ClassificationResult classify(List<String> document);

	/**
	 * @return all the categories present in the training set
	 */
	public Set<String> getAllCategories() {
		return Stream
				.concat(this.trainingSet.stream(), this.validationSet.stream())
				.map(taggedDocument -> taggedDocument.getCategory())
				.collect(Collectors.toSet());
	}

	public List<TaggedDocument> getTrainingSet() {
		return trainingSet;
	}

	/**
	 * @param category
	 *            one of the categories present in the training set
	 * @return training set documents tagged to the specified category
	 */
	public List<TaggedDocument> getTrainingSet(String category) {
		return this.trainingSet.stream()
				.filter(doc -> doc.getCategory().equals(category))
				.collect(Collectors.toList());
	}

	/**
	 * @param category
	 *            one of the categories present in the training set
	 * @return validation documents in the training set tagged to the specified
	 *         category
	 */
	public List<TaggedDocument> getValidationSet(String category) {
		return this.validationSet.stream()
				.filter(doc -> doc.getCategory().equals(category))
				.collect(Collectors.toList());
	}

	public List<TaggedDocument> getValidationSet() {
		return validationSet;
	}

}
