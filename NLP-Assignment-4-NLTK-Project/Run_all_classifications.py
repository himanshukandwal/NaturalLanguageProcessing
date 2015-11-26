import os, sys
import subprocess

__author__ = 'Himanshu Kandwal'

print '\n\n', '*' * 42
print '*' * 9, ' Running Everything ! ', '*' * 9
print '*' * 42, '\n\n'

subprocess.call([sys.executable, os.getcwd() + '/balanced_collection_classification/Run_all_balanced_classifications.py', 'balanced_collection_classification'])

subprocess.call([sys.executable, os.getcwd() + '/unbalanced_collection_classification/Run_all_unbalanced_classifications.py', 'unbalanced_collection_classification'])

print '\n\n', '*' * 10, ' Finished Running. ', '*' * 10