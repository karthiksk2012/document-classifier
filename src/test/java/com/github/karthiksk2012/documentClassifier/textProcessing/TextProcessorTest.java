package com.github.karthiksk2012.documentClassifier.textProcessing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class TextProcessorTest {

	@Test
	public void testIsNotStopWordFalse() {
		assertFalse(TextProcessor.isNotStopWord("the"));;
		assertTrue(TextProcessor.isNotStopWord("tennis"));;
	}
	@Test
	public void testIsNotStopWordFalseIgnoreCase() {
		assertFalse(TextProcessor.isNotStopWord("The"));;
	}

	@Test
	public void testStemWord() {
		assertEquals("caress", TextProcessor.stemWord("caresses"));
	}
	@Test
	public void testStemWordCaseRootWordRemainsIntact() {
		assertEquals("cat", TextProcessor.stemWord("cat"));
	}

	@Test
	public void testWordCount() {
		List<String> document = new ArrayList<>(Arrays.asList("car","cat","carmen","master","tigress","master","shifu","has","clearly","mastered","car"));
		assertEquals(2, TextProcessor.wordCount("master", document).longValue());
	}
	@Test
	public void testWordCountNonExistingWord() {
		List<String> document = new ArrayList<>(Arrays.asList("car","cat","carmen","master","tigress","master","shifu","has","clearly","mastered","car"));
		assertEquals(0, TextProcessor.wordCount("oogway", document).longValue());
	}

	@Test
	public void testSplitCollectionAt() {
		List<Integer> numberItems = IntStream.rangeClosed(1, 120).boxed().collect(Collectors.toList());
		assertEquals(96, TextProcessor.splitCollectionAt(numberItems, 80).get(0).size());
		assertEquals(24, TextProcessor.splitCollectionAt(numberItems, 80).get(1).size());
	}

}
