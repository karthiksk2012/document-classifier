# Java Document Classifier
[![Build Status](https://travis-ci.com/karthiksk2012/document-classifier.svg?branch=master)](https://travis-ci.com/karthiksk2012/document-classifier)

A simple document classifier written in Java based on Naive Bayesian Model. Currently, the classifier implemented is a Multinomial Bayes, but it can be extended to implement a classifier on Bernoulli and other probability models.

### Example usage
Here is a quick example of how to use the classifier to classify a small piece of unknown text
```java   
// Collection of pre-tagged documents as the training set
List<TaggedDocument> trainingSet = readTrainingSet("trainingset/bbc-text.csv");

// Construct a BayesClassifier with the training data
DocumentClassifier classifier = new BayesDocumentClassifier(trainingSet);

// Classify unknown piece of text into a category
String textToClassify = "OpenAI said its new natural language model was trained
                         to predict the next word in a sample of 40 gigabytes of
                         internet text";

ClassificationResult result = classifier.classify(Arrays
				.asList(textToClassify.split("\\s+")));

// Prints "tech"
System.out.println(result.bestMatch().getCategory());
```

### Sources
The training set in the application has been taken from [public data set on BBC news articles](http://mlg.ucd.ie/datasets/bbc.html)

The algorithm for stemming has been taken as is from [Martin Porter's original java implementation of porter stemmer algorithm](https://tartarus.org/martin/PorterStemmer/java.txt)
