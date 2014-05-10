package edu.ucsc.nosqlproject;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

public class Timestamp extends client{
	private int offset;
	private double v;
//	private String d;
	
	public Timestamp(int offset) //offset from start time
	{
	this.offset=offset;
	}

	public double value(){
	String qCQL="SELECT time FROM timeseries.sensordata WHERE subject_id=103"+"LIMIT ?;";
    PreparedStatement prepared=getSession().prepare(qCQL);
    offset=offset+1;
	BoundStatement bound =prepared.bind(offset);
    ResultSet results=getSession().execute(bound);
	for (Row row:results){
	v=row.getDouble("time");}
	return v;
	}
}
	