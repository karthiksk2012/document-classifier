package com.github.karthiksk2012.documentClassification.bayes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.karthiksk2012.documentClassification.TaggedDocument;

public class BayesDocumentClassifierTest {

	private static BayesDocumentClassifier bayesClassifier;
	private static List<TaggedDocument> historyDocs = new ArrayList<>();
	private static List<TaggedDocument> sportDocs = new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		List<TaggedDocument> trainingSet = new ArrayList<TaggedDocument>();
		TaggedDocument historyOne = new TaggedDocument("history", new ArrayList<>(Arrays.asList("the","history","war","world","history","past","war")));
		TaggedDocument historyTwo = new TaggedDocument("history", new ArrayList<>(Arrays.asList("the","second","world","war","was","a","history","disaster")));
		historyDocs.add(historyOne);
		historyDocs.add(historyTwo);
		TaggedDocument sportOne = new TaggedDocument("sport", new ArrayList<>(Arrays.asList("sachin","greatest","player","sport","cricket","score","big")));
		TaggedDocument sportTwo = new TaggedDocument("sport", new ArrayList<>(Arrays.asList("baseball","ball","player","sport","tennis","score","victory")));
		sportDocs.add(sportOne);
		sportDocs.add(sportTwo);
		
		trainingSet.addAll(historyDocs);
		trainingSet.addAll(sportDocs);
		
	    bayesClassifier = new BayesDocumentClassifier(trainingSet);	
	}

	@Test
	public void testClassifyResultExistForAllCategories() {
		ClassificationResult result = bayesClassifier.classify(sportDocs.get(0).getWords());
		assertEquals(2, result.getClassifications().keySet().size());
	}

	@Test
	public void testPredictProbability() {
		Double probabilityForSports = bayesClassifier.predictProbability(historyDocs.get(0).getWords(), "sports");
		Double probabilityForHistory = bayesClassifier.predictProbability(historyDocs.get(0).getWords(), "history");
		assertTrue(probabilityForHistory > probabilityForSports);
	}

	@Test
	public void testGetCategories() {
		Set<String> actualCategories = new HashSet<>(Arrays.asList("history","sport"));
		Set<String> expectedCategories = bayesClassifier.getAllCategories();
		assertEquals(actualCategories, expectedCategories);
	}


	@Test
	public void testGetTrainingAndValidationSetByCategory() {
		assertEquals(2, bayesClassifier.getTrainingSet("history").size()+bayesClassifier.getValidationSet("history").size());
		assertEquals(2, bayesClassifier.getTrainingSet("sport").size()+bayesClassifier.getValidationSet("sport").size());
	}



}
