This repo is made up of 4 code segments:
1. client is the entry point.  It reads data from Cassandra with the Data_block function.  It validates data with Timestamp.  It call MachineLearning to train model on training set and test accuracy on test set.

2. Data_block reads a block of data from the data base

3. MachineLearning imports the Weka library and applies random forest classification on a randomly selected data sample.  

4. Timestamp reads cassandra generated timestamp.