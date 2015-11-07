import os, sys

__author__ = 'Himanshu Kandwal'

directory = '';

if len(sys.argv) > 1:
    directory = '/' + sys.argv[1];
    print 'directory is : ' + directory

print '\n\n', '*' * 10, ' UNPROCESSED CORPUS ', '*' * 10

execfile(os.getcwd() + directory + '/unprocessed_classification/Unprocessed_MaximumEntropyClassification_25_Percent_Training.py')
execfile(os.getcwd() + directory + '/unprocessed_classification/Unprocessed_MaximumEntropyClassification_50_Percent_Training.py')
execfile(os.getcwd() + directory + '/unprocessed_classification/Unprocessed_MaximumEntropyClassification_75_Percent_Training.py')
execfile(os.getcwd() + directory + '/unprocessed_classification/Unprocessed_MaximumEntropyClassification_80_Percent_Training.py')
execfile(os.getcwd() + directory + '/unprocessed_classification/Unprocessed_MaximumEntropyClassification_90_Percent_Training.py')


print '\n\n', '*' * 10, ' LEMMATIZED CORPUS ', '*' * 10
execfile(os.getcwd() + directory + '/lemmatized_classification/Lemmatized_MaximumEntropyClassification_25_Percent_Training.py')
execfile(os.getcwd() + directory + '/lemmatized_classification/Lemmatized_MaximumEntropyClassification_50_Percent_Training.py')
execfile(os.getcwd() + directory + '/lemmatized_classification/Lemmatized_MaximumEntropyClassification_75_Percent_Training.py')
execfile(os.getcwd() + directory + '/lemmatized_classification/Lemmatized_MaximumEntropyClassification_80_Percent_Training.py')
execfile(os.getcwd() + directory + '/lemmatized_classification/Lemmatized_MaximumEntropyClassification_90_Percent_Training.py')


print '\n\n', '*' * 10, ' PUNCTUATION FILTERED CORPUS ', '*' * 10
execfile(os.getcwd() + directory + '/punctuation_filtered_classification/Punctuation_Filtered_MaximumEntropyClassification_25_Percent_Training.py')
execfile(os.getcwd() + directory + '/punctuation_filtered_classification/Punctuation_Filtered_MaximumEntropyClassification_50_Percent_Training.py')
execfile(os.getcwd() + directory + '/punctuation_filtered_classification/Punctuation_Filtered_MaximumEntropyClassification_75_Percent_Training.py')
execfile(os.getcwd() + directory + '/punctuation_filtered_classification/Punctuation_Filtered_MaximumEntropyClassification_80_Percent_Training.py')
execfile(os.getcwd() + directory + '/punctuation_filtered_classification/Punctuation_Filtered_MaximumEntropyClassification_90_Percent_Training.py')


print '\n\n', '*' * 10, ' STOPWORDS FILTERED CORPUS ', '*' * 10
execfile(os.getcwd() + directory + '/stopwords_filtered_classification/Stopwords_Filtered_MaximumEntropyClassification_25_Percent_Training.py')
execfile(os.getcwd() + directory + '/stopwords_filtered_classification/Stopwords_Filtered_MaximumEntropyClassification_50_Percent_Training.py')
execfile(os.getcwd() + directory + '/stopwords_filtered_classification/Stopwords_Filtered_MaximumEntropyClassification_75_Percent_Training.py')
execfile(os.getcwd() + directory + '/stopwords_filtered_classification/Stopwords_Filtered_MaximumEntropyClassification_80_Percent_Training.py')
execfile(os.getcwd() + directory + '/stopwords_filtered_classification/Stopwords_Filtered_MaximumEntropyClassification_90_Percent_Training.py')

