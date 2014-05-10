package edu.ucsc.nosqlproject;
import com.datastax.driver.core.BoundStatement;
//import java.lang.Math;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import java.util.Random;
import java.util.List;
//import java.math.BigDecimal;
public class Data_block extends client {
	private double subject_id;
	private double start_time;
//	private ResultSet results;
//	private List<BigDecimal> readings;
	private List<Float>readings;
//	private float[][]sensor_array;
	private float[][]dataset;
	private float[][]clean_dataset;
	private int column_width;
	private int manycolumns;
	public  int n_cleanrecords;
	
	private int[] which_sreadings;
	private int num_srdg4class;
	
//manycolumns is the number of columns to be fetched		
	public Data_block (double subject_id, double time, int manycolumns,int column_width,int n_4class)
	{
		this.subject_id=subject_id;
		this.start_time=time;
		this.manycolumns=manycolumns;
		this.column_width=column_width;
		this.num_srdg4class=n_4class;
		dataset=new float [manycolumns][num_srdg4class+1];
		
	}
 
	

//query the whole block of data and set up dataset. 
//Label in the last column of dataset.
//select_sreadings picks the sensor readings used for classification with sreading_vector
//it produces a vector of length 28
//dataset width is 29 because the label column is added
//label stored in dataset[i][28]
	

	public float[][] sensor_readings()
	{
		float [][]sensor_array=new float[manycolumns][column_width];
		double end_time=start_time+manycolumns*0.01;
		String qCQL="SELECT * FROM timeseries.sensordata WHERE subject_id=?" + "AND time>=?"+" AND time<?;";
	    PreparedStatement prepared=getSession().prepare(qCQL);
	
		BoundStatement bound =prepared.bind(subject_id,start_time,end_time);
	    ResultSet results=getSession().execute(bound);
//pack the data into an array	    
	    int m=0;
	    for (Row row:results){

	    readings=row.getList("raw",Float.class);
	    dataset[m][num_srdg4class]=(float) row.getInt("label");
	    
	    for (int i=0; i<column_width; i++){

	    	sensor_array[m][i]=readings.get(i);}
	     	
	    m++;
	    }
	    which_sreadings=select_sreadings();
	    for (int i=0; i<manycolumns; ++i){	
			for (int j=0; j<num_srdg4class; ++j){
				dataset[i][j]=sensor_array[i][which_sreadings[j]];}}
	    
		return clean_set();
	}
//Read a block of data without packing it into an array; used to measure Cassandra read speed	
	public ResultSet db_block()
	{
		double end_time=start_time+manycolumns*0.01;
		String qCQL="SELECT * FROM timeseries.sensordata WHERE subject_id=?" + "AND time<?;";
	    PreparedStatement prepared=getSession().prepare(qCQL);
	
		BoundStatement bound =prepared.bind(subject_id,end_time);
	    ResultSet results=getSession().execute(bound);
	    return results;
	}


/**
	public float[][] get_dataset(){
	
		which_sreadings=select_sreadings();

		get_sensorMatrix();
		return dataset;
	}
//Read labels from sensor_readings (don't call this after db_block) and 
//populate the last column of dataset		

	public void get_sensorMatrix(){
		
		for (int i=0; i<manycolumns; ++i){	
			for (int j=0; j<num_srdg4class; ++j){
				dataset[i][j]=sensor_array[i][which_sreadings[j]];
		}
		}
	return;
	}
**/
	public int[] select_sreadings()
	{
		
		int[] sreading_vector = new int[28];
		sreading_vector[0]=0;
		int dat=2;
		for (int i=1; i<10; ++i){
			sreading_vector[i]=dat;
			++dat;
		}
		dat=19;
		for (int i=10; i<19; ++i){
			sreading_vector[i]=dat;
			++dat;
		}
		dat=36;
		for (int i=19; i<28; ++i){
			sreading_vector[i]=dat;
			++dat;
		}
		return sreading_vector;
	}
//copy a row from source matrix to destination matrix of the same width	
	
	public void copyrow(float[][]source,int src_row, float[][]dest, int dest_row)
	{
		for (int i=0; i<29; ++i)
		{dest[dest_row][i]=source[src_row][i];}
		return;
	}
//Further clean dataset by removing records with label =0
	public float[][] clean_set()
	{
		
		
		int count_0label=0;
//Find how many rows with zero label	
		for (int i=0; i<manycolumns; ++i)
		{if (((int)dataset[i][28])==0){
		 ++count_0label;}}
	n_cleanrecords=manycolumns-count_0label;
	clean_dataset=new float[n_cleanrecords][num_srdg4class+1];
	int j=0;
	for (int i=0; i<manycolumns; ++i)
	{if(((int)dataset[i][28])!=0)
	{copyrow (dataset,i,clean_dataset,j);++j;}
	}
	
	return clean_dataset;
	}
//use random generator to build a training set from clean_dataset
	public float[][] build_train(int trainset_size)
	{
		
		float[][]trainset=new float[trainset_size][num_srdg4class+1];
		
		for (int i=0; i<trainset_size; ++i){
			Random generator=new Random();
			double r=generator.nextDouble();
			int	rand_index =(int)(r*n_cleanrecords);
			copyrow(clean_dataset, rand_index, trainset,i);}
	return trainset;		
	}
	public int check_activity(int activity)
	{
		int count_activity=0;
		for (int i=0; i<n_cleanrecords; ++i)
		{if (((int)clean_dataset[i][28])==activity){
		 ++count_activity;}}
		return count_activity;
	}
}
