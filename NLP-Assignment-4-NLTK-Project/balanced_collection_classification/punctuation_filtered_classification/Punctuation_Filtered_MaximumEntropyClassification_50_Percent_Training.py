import nltk
import nltk.classify.util
import nltk.metrics
import string

__author__ = 'Himanshu Kandwal'

#appending corpus location
nltk.data.path.append("/Users/Heman/Documents/workstation/Developement_Studio/Python/NLP_Corpus/nltk_data");

from nltk.corpus import movie_reviews

print '\n\n', '+' * 10, '[ 50% Training without punctuations ]', '+' * 10, '\n\n'

#Filtering Punctuations
punctuations = list(string.punctuation)

def word_feats(words):
    return dict([(word, True) for word in words if word not in punctuations])

# Generate all the reviews with negative dataset and positive dataset.
negids = movie_reviews.fileids('neg')
posids = movie_reviews.fileids('pos')

# Identify the words in the datasets as positive and negative.
negfeats = [(word_feats(movie_reviews.words(fileids=[f])), 'neg') for f in negids]
posfeats = [(word_feats(movie_reviews.words(fileids=[f])), 'pos') for f in posids]

negcutoff = len(negfeats)*1/2
poscutoff = len(posfeats)*1/2

# Based on the cut off, fill the training data and testing data with its respective positive and negative dataset.
trainfeats = negfeats[:negcutoff] + posfeats[:poscutoff]
testfeats = negfeats[negcutoff:] + posfeats[poscutoff:]
print 'train on %d instances, test on %d instances' % (len(trainfeats), len(testfeats))

# Executing Maximum Entropy Classifier to classify the training data.
maxentClassifierAlgorithm = nltk.classify.MaxentClassifier.ALGORITHMS[0]
classifier = nltk.MaxentClassifier.train(trainfeats, maxentClassifierAlgorithm, max_iter=5)
classifier.show_most_informative_features(10)

# Print the accuracy of the algorithm.
print 'accuracy:', nltk.classify.util.accuracy(classifier, testfeats)

# --------------------------------------------------------------------------
