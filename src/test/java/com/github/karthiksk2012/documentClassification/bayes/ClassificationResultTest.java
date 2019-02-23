package com.github.karthiksk2012.documentClassification.bayes;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.karthiksk2012.documentClassification.CategoryClassification;

public class ClassificationResultTest {

	@Test
	public void testBestMatchInClassification() {
		Map<String, CategoryClassification> sampleResult = new HashMap<String, CategoryClassification>();
		sampleResult.put("hiking", new CategoryClassification("hiking", 0.832,
				0.52));
		sampleResult.put("trekking", new CategoryClassification("trekking",
				0.632, 0.92));
		sampleResult.put("mountaineering", new CategoryClassification(
				"mountaineering", 0.332, 0.192));

		ClassificationResult csResult = new ClassificationResult(sampleResult);

		assertEquals("hiking", csResult.bestMatch().getCategory());

	}

}
