import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.karthiksk2012.documentClassification.DocumentClassifier;
import com.github.karthiksk2012.documentClassification.TaggedDocument;
import com.github.karthiksk2012.documentClassification.bayes.BayesDocumentClassifier;
import com.github.karthiksk2012.documentClassification.bayes.ClassificationResult;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
/**
 * A runnable example that calls a classifier to classify two documents
 * 
 * @author karthik
 *
 */
public class Example {

	public static void main(String[] args) throws FileNotFoundException {

		/*
		 * Read training Set data from a CSV file. You can even create a simple
		 * list of tagged documents for testing purposes
		 */
		List<TaggedDocument> trainingSet = readTrainingSet("trainingset/bbc-text.csv");

		/*
		 * Create an instance of classifier with the given pre-tagged documents
		 */
		DocumentClassifier classifier = new BayesDocumentClassifier(trainingSet);

		String sampleTechDocument = "OpenAI said its new natural language model, GPT-2, was trained to predict the next word in a sample of 40 gigabytes of internet text. The end result was the system generating text that “adapts to the style and content of the conditioning text,” allowing the user to “generate realistic and coherent continuations about a topic of their choosing.” The model is a vast improvement on the first version by producing longer text with greater coherence.";
		String sampleSportDocument = " Paul Pogba inspired Manchester United to a 2-0 win over Chelsea on Monday to book their place in the FA Cup quarterfinals. The midfielder made one and scored one as United won at Stamford Bridge for the first time since 2012 to further improve Ole Gunnar Solskjaer's chances of getting the manager's job permanently.As Chelsea fans revolted against Maurizio Sarri with chants of Youre getting sacked in the morning the United supporters spent most of the second half making it clear they want the Norwegian to get the gig on a permanent basis. Following impressive wins at Tottenham and Arsenal, Solskjaer added Chelsea to his CV. Ander Herrera scored the first with a header after Pogba's sublime cross to the back post before the Frenchman got his 14th goal of the season on the stroke of half-time with a diving header after fine work on the right from Marcus Rashford";

		ClassificationResult resultOne = classifier.classify(Arrays
				.asList(sampleTechDocument.split("\\s+")));
		// Prints "tech" as the best matched category
		System.out.println(resultOne.bestMatch().getCategory());

		ClassificationResult resultTwo = classifier.classify(Arrays
				.asList(sampleSportDocument.split("\\s+")));
		// Prints "sport" as the best matched category
		System.out.println(resultTwo.bestMatch().getCategory());
	}

	/**
	 * Convert a CSV file into the training set data
	 * 
	 * @param fileUrl
	 *            URL of the CSV file
	 * @return List of tagged training documents
	 * @throws FileNotFoundException
	 */
	private static List<TaggedDocument> readTrainingSet(String fileUrl)
			throws FileNotFoundException {
		List<CsvTopic> allCsvArticles = readCSVRowsIntoDocuments(fileUrl);
		List<TaggedDocument> trainingDocuments = new ArrayList<TaggedDocument>();

		for (CsvTopic csv : allCsvArticles) {
			trainingDocuments.add(new TaggedDocument(csv.getCategory(),
					new ArrayList<String>(Arrays.asList(csv.getTextData()
							.split("\\s+")))));
		}
		return trainingDocuments;
	}

	/**
	 * Reads the csv file at the specified url, parses the rows and converts
	 * them to tagged documents
	 * 
	 * @param fileUrl
	 *            url of the csv file
	 * @return A list of rows read from the file representing tagged documents
	 * @throws FileNotFoundException
	 */
	private static List<CsvTopic> readCSVRowsIntoDocuments(String fileUrl)
			throws FileNotFoundException {
		CSVReader reader = new CSVReader(new FileReader(fileUrl));
		HeaderColumnNameMappingStrategy<CsvTopic> strategy = new HeaderColumnNameMappingStrategy<CsvTopic>();
		CsvToBean<CsvTopic> csvbean = new CsvToBean<CsvTopic>();
		strategy.setType(CsvTopic.class);
		List<CsvTopic> allCsvArticles = csvbean.parse(strategy, reader);
		return allCsvArticles;
	}

}
