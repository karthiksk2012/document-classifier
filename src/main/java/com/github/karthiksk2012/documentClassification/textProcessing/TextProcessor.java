package com.github.karthiksk2012.documentClassification.textProcessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A wrapper for frequently used methods related to processing textual content
 * 
 * @author karthik
 *
 */
public final class TextProcessor {

	/**
	 * @param word
	 *            to be checked
	 * @return if the specified word is not an english stop word
	 */
	public static boolean isNotStopWord(String word) {
		return !Arrays.asList(ProcessingConstants.CONST_STOP_WORDS).contains(
				word.toLowerCase());
	}

	/**
	 * Stem the word to its root form. A NLP technique used to eliminate
	 * different forms of a word and treat it as the same Uses Martin Porter's
	 * original stemmer algorithm as is
	 * 
	 * @param word
	 *            to be stemmed
	 * @return the root form of the word
	 */
	public static String stemWord(String word) {
		Stemmer s = new Stemmer();
		s.add(word.toCharArray(), word.length());
		s.stem();
		return s.toString();
	}

	/**
	 * 
	 * @param word
	 *            to be counted
	 * @param processedDoc
	 *            the document
	 * @return number of times the word occurs in the specified document
	 */
	public static Long wordCount(String word, Collection<String> processedDoc) {
		return processedDoc.stream().filter(w -> w.equals(word)).count();
	}

	/**
	 * Split the list of items in the given ratio into two
	 * 
	 * @param collection
	 *            collection of items to be split
	 * @param percent
	 *            Fraction of split for the first part
	 * @return A wrapper list of two lists representing two splits of the
	 *         collection
	 */
	public static <T> List<List<T>> splitCollectionAt(List<T> collection,
			int percent) {
		List<List<T>> splitCollection = new ArrayList<>();
		List<T> firstPortion = new ArrayList<>();
		List<T> secondPortion = new ArrayList<>();
		int endOfFirstPosition = (int) (collection.size() * (percent / 100d));
		for (int i = 0; i < collection.size(); i++) {
			if (i <= endOfFirstPosition - 1)
				firstPortion.add(collection.get(i));
			else
				secondPortion.add(collection.get(i));
		}
		splitCollection.add(firstPortion);
		splitCollection.add(secondPortion);
		return splitCollection;
	}

	/**
	 * Process the document before being used. Applies processing techniques of
	 * casing the words, filtering stop words and stemming the words
	 * 
	 * @param taggedDoc
	 *            to be processed
	 * @return processed set of unique words
	 */
	public static Set<String> processDocument(List<String> taggedDoc) {
		Set<String> processedDoc = taggedDoc.stream().map(String::toLowerCase)
				.filter(TextProcessor::isNotStopWord)
				.map(TextProcessor::stemWord).collect(Collectors.toSet());
		return processedDoc;
	}

}
