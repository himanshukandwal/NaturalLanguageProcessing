import os, sys

__author__ = 'Himanshu Kandwal'

directory = '';

if len(sys.argv) > 1:
    directory = '/' + sys.argv[1];
    print 'directory is : ' + directory

print '\n\n', '*' * 10, ' UNPROCESSED CORPUS ', '*' * 10

execfile(os.getcwd() + directory + '/unprocessed_classification/Unprocessed_MaxEntropyClassification_25_Percent_Neg_45_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/unprocessed_classification/Unprocessed_MaxEntropyClassification_50_Percent_Neg_25_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/unprocessed_classification/Unprocessed_MaxEntropyClassification_75_Percent_Neg_50_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/unprocessed_classification/Unprocessed_MaxEntropyClassification_25_Percent_Neg_80_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/unprocessed_classification/Unprocessed_MaxEntropyClassification_90_Percent_Neg_75_Percent_Pos_Training.py')


print '\n\n', '*' * 10, ' LEMMATIZED CORPUS ', '*' * 10
execfile(os.getcwd() + directory + '/lemmatized_classification/Lemmatized_MaxEntropyClassification_25_Percent_Neg_45_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/lemmatized_classification/Lemmatized_MaxEntropyClassification_25_Percent_Neg_80_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/lemmatized_classification/Lemmatized_MaxEntropyClassification_50_Percent_Neg_25_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/lemmatized_classification/Lemmatized_MaxEntropyClassification_75_Percent_Neg_50_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/lemmatized_classification/Lemmatized_MaxEntropyClassification_90_Percent_Neg_75_Percent_Pos_Training.py')


print '\n\n', '*' * 10, ' PUNCTUATION FILTERED CORPUS ', '*' * 10
execfile(os.getcwd() + directory + '/punctuation_filtered_classification/Punctuation_Filtered_MaxEntropyClassification_25_Percent_Neg_45_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/punctuation_filtered_classification/Punctuation_Filtered_MaxEntropyClassification_25_Percent_Neg_80_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/punctuation_filtered_classification/Punctuation_Filtered_MaxEntropyClassification_50_Percent_Neg_25_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/punctuation_filtered_classification/Punctuation_Filtered_MaxEntropyClassification_75_Percent_Neg_50_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/punctuation_filtered_classification/Punctuation_Filtered_MaxEntropyClassification_90_Percent_Neg_75_Percent_Pos_Training.py')


print '\n\n', '*' * 10, ' STOPWORDS FILTERED CORPUS ', '*' * 10
execfile(os.getcwd() + directory + '/stopwords_filtered_classification/Stopwords_Filtered_MaxEntropyClassification_25_Percent_Neg_45_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/stopwords_filtered_classification/Stopwords_Filtered_MaxEntropyClassification_25_Percent_Neg_80_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/stopwords_filtered_classification/Stopwords_Filtered_MaxEntropyClassification_50_Percent_Neg_25_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/stopwords_filtered_classification/Stopwords_Filtered_MaxEntropyClassification_75_Percent_Neg_50_Percent_Pos_Training.py')
execfile(os.getcwd() + directory + '/stopwords_filtered_classification/Stopwords_Filtered_MaxEntropyClassification_90_Percent_Neg_75_Percent_Pos_Training.py')

