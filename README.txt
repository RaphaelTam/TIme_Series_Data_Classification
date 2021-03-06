This project stores sensor data in Cassandra and simulates real-time motion detection using the random forrest classifier.

RaphaelTam_NoSQL_Project_Report.pdf describes how data is cleaned with R before being bulk loaded into a Cassandra instance.  The data model is designed for fast data read and real-time classification.  It simulates motion detection by leveraging Cassandra’s special architecture.

The following java modules simulates motion detection:

1. client is the entry point.  It reads data from Cassandra with the Data_block function.  It validates data with Timestamp.  It call MachineLearning to train model on training set and test accuracy on test set.

2. Data_block reads a block of data from the data base

3. MachineLearning imports the Weka library and applies random forest classification on a randomly selected data sample.  

4. Timestamp reads cassandra generated timestamp.

