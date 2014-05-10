package edu.ucsc.nosqlproject;

import java.util.Date;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

import weka.classifiers.Classifier;
import weka.core.Instances;
public class client {
   private Cluster cluster;
   protected static Session session;
 
   public void connect(String node) {
      cluster = Cluster.builder()
            .addContactPoint(node).build();
      Metadata metadata = cluster.getMetadata();
      System.out.printf("Connected to cluster: %s\n", 
            metadata.getClusterName());
      for ( Host host : metadata.getAllHosts() ) {
         System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
               host.getDatacenter(), host.getAddress(), host.getRack());
         System.out.println();
      }
      session=cluster.connect();
   }
// returns the session instance field
   public Session getSession(){
     return this.session;
   }

   public void close() {
      cluster.shutdown();
   }

   public static void main(String[] args) throws Exception
   {
//Connect to Cassandra server
      client client = new client();
      client.connect("127.0.0.1");
      
//Use timeseries database
      session.execute("use timeseries;");
      double subject=103;

      
//Get the time stamp for the first set of sensor readings
      Timestamp start_time=new Timestamp(0);
      double stime_innum=start_time.value();
      System.out.println("Start Time = "+ stime_innum+"\n");
//Print the time stamp for the first set of sensor readings in Date Format     
      long l=(new Double(stime_innum*1000)).longValue();
      Date d = new Date(l);
      System.out.println("Start Time in Date Format is "+d+"\n");
      
//Get the time stamp after 10000 records and check if the time difference is 10000
      Timestamp end_time=new Timestamp(10000);
      double etime_innum=end_time.value();
      double timediff=etime_innum-stime_innum;
      System.out.println("Data recorded every 10 ms.  Time Difference between the 1st and the 10000th record is "+ timediff+ "seconds");
   

      int num_records=174338;
      int num_sreadings=52;
      int num_readings4class=28;
      int n_trainrecords=5000;
      
 //Print set up information
      System.out.println("\nNumber of records stored in the database for subject 103 is "+num_records);
      System.out.format("%n%d sensor attributes are stored in the database",num_sreadings);
      System.out.format("%n%nNumber of records processed in this classification= %d",num_records);
      
      System.out.format("%n%n%d sensor attributes are used for classification",num_readings4class);
      System.out.format("%n%nNumber of records used for training= %d",n_trainrecords);
    //Measure read speed by starting a timer, read 100000 columns and stop timer
    //Speed = 100000/timer reading    
      long start_timer=System.currentTimeMillis();
      Data_block large_block=new Data_block(subject,stime_innum,num_records,num_sreadings,num_readings4class);
      large_block.db_block();
      long stop_timer=System.currentTimeMillis();
      float read_speed=100000/(stop_timer-start_timer);
      System.out.format("%n%nRead Speed = %5.0f records/second, or%5.0f MB/sec%n",read_speed*1000,read_speed*.436);
      System.out.format("Reading a data block of %d records, clean these records and set up matrix for classification%n",num_records);
      System.out.format(".....................................%n");
//Scan the database starting from the first record.  
//Return dataset in an array;
//dataset is a subset of all the sensor data
//Not all sensor data used for classification
//Only records with labels not equal to zero are used
//Print the number of non-zero records

      
     
      Data_block big_block=new Data_block(subject, stime_innum,num_records,num_sreadings,num_readings4class);
      float[][] Xy=big_block.sensor_readings();
      System.out.println("\nUse only records that have non-zero data for classfication");
      System.out.format("Number of clean records for classification is %d%n%n",big_block.n_cleanrecords);    

      
    	     
      
//Print a fragment of the first 10 records to check if data is ok
      System.out.println("Fragments of the first 10 records:");
      System.out.println("Heart Rate            Hand Accelerarometer          Label");
      for (int i=0; i<10; i++)
      {
      System.out.format("%10.4f %10.4f %10.4f %10.4f %10.4f %n",Xy[i][0],Xy[i][1],Xy[i][2],Xy[i][3],Xy[i][28]);
      }
/**
 //Print a fragment of the records with label=17
      double blocktime=stime_innum+71339*.01;
      Data_block small_block=new Data_block(subject,blocktime,100,52,28);
      float[][] ir_dat=small_block.sensor_readings();
      System.out.println("Ironing Block Data");
      for (int i=1; i<10; i++)
      {
      System.out.format("%10.4f %10.4f %10.4f %10.4f %10.4f %n",ir_dat[i][0],ir_dat[i][1],ir_dat[i][2],ir_dat[i][3],ir_dat[i][28]);
      }
 **/
 //Print the first 2 records and last record of the dataset completely to check dataset validity
      System.out.println("\nData for the first 2 and the last records:");
      System.out.println("Record 1"+"     "+"Record 2"+"     "+"Last Record");
      for (int i=0; i<29; i++){
    	  System.out.format("%10.4f %10.4f %10.4f %n",Xy[1][i],Xy[2][i],Xy[big_block.n_cleanrecords-1][i]);
      }
  //Build training set of n_trainingrecords with random number generator
  //Print fragments of the first 20 records in trainingset   
  //Use 5000 examples to train
      
      float[][]training_set=big_block.build_train(n_trainrecords);
      System.out.println("\nFor the first 20 TRAINING records");
      System.out.println("    HR          Hand Accelerarometer             label");
      for (int i=0; i<20; i++)
      {
      System.out.format("%10.4f %10.4f %10.4f %10.4f %10.4f%n",training_set[i][0],training_set[i][1],training_set[i][2],training_set[i][3],training_set[i][28]);
      }
  //check the number of records with a specific activity 
      int des_stairs=12;
      System.out.println();
      System.out.println("No. of descending stairs records= "+big_block.check_activity(des_stairs)); 
      
  
//Construct training data set      
      MachineLearning training= new MachineLearning (training_set,n_trainrecords);
      training.set_trainInstances(n_trainrecords);
      System.out.println("\nSetting up training set in Weka format");
      
//Learn a model with Random Forest Algorithm, return model learned   
      System.out.println("\nLearning from the training set with Random Forest");
      Classifier learned = training.rf();  
//Construct testing data set.  Labels are not in the test set    
      int n_testrecords=big_block.n_cleanrecords;    
      MachineLearning testing = new MachineLearning (Xy,n_testrecords);
      Instances test_set=testing.set_testInstances(n_testrecords);
//Classify test data  
      System.out.println("\nClassifying all clean records");
      int correct_pred=testing.cal_accuracy(learned,test_set);
//Get accuracy and error rate
      System.out.println("\nNo. of correct predictions= "+correct_pred);
      System.out.println("\nNo. of erroneous predictions= "+(big_block.n_cleanrecords-correct_pred));
      float accuracy_rate=(float) correct_pred/big_block.n_cleanrecords;
      System.out.format("%nError Rate= %6.2f percent",(1.0-accuracy_rate)*100);
      
      
//close session
      client.close();
     

      
}
}