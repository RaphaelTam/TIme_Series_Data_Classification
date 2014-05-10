package edu.ucsc.nosqlproject;
import weka.classifiers.Classifier;
//import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.Instance;
//import weka.core.FastVector;
import java.util.ArrayList;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instances;
//import weka.core.OptionHandler;
//import weka.core.Utils;
//@SuppressWarnings("deprecation")
public class MachineLearning {
	
	private float [][] inputdata;
	private Instances trainset;
	private Instances testset;
	private Classifier model;
	private int nrow;
	private double outcome[];
	public MachineLearning (float[][]inputdata,int nrow)
	{
		
		this.inputdata=inputdata;
		this.nrow=nrow;
		
	}
	//Set up training dataset in weka format	
	//Weka training set needs to be a double matrix
	//Attribute class defines features
	//Attribute(String) defines a numerical attribute
	//Attribute class also defines class labels with new Attribute(String for the label header, ArrayList)
	//Input class labels by adding to ArrayList
	//Weka uses a label's position in the ArrayList to code a nominal label into a number used by the algorithms
	//Group all attributes into an ArrayList<Attribute>(number of attributes including label) as an attribute vector
	//Construct an empty dataset with new Instances
	//Construct an instance with new DenseInstance
	//Fill an instance with setValue
	//Add to dataset with .add
	//Set class index with .setClassIndex(attribute index for class labels)
	
	public void set_trainInstances(int n_examples)
	{
		
		Attribute HR=new Attribute("HR");
		Attribute hd_ac11=new Attribute ("hd_ac11");
		Attribute hd_ac12=new Attribute ("hd_acc12");
		Attribute hd_ac13=new Attribute ("hd_acc13");
		Attribute hd_ac21=new Attribute ("hd_ac21");
		Attribute hd_ac22=new Attribute("hd_ac22");
		Attribute hd_ac23=new Attribute("hd_ac23");
		Attribute hd_gy1 =new Attribute("hd_gy1");
		Attribute hd_gy2 =new Attribute("hd_gy2");
		Attribute hd_gy3 =new Attribute("hd_gy3");
		Attribute ch_ac11=new Attribute ("ch_ac11");
		Attribute ch_ac12=new Attribute ("ch_acc12");
		Attribute ch_ac13=new Attribute ("ch_acc13");
		Attribute ch_ac21=new Attribute ("ch_ac21");
		Attribute ch_ac22=new Attribute("ch_ac22");
		Attribute ch_ac23=new Attribute("ch_ac23");
		Attribute ch_gy1 =new Attribute("ch_gy1");
		Attribute ch_gy2 =new Attribute("ch_gy2");
		Attribute ch_gy3 =new Attribute("ch_gy3");
		Attribute ak_ac11=new Attribute ("ak_ac11");
		Attribute ak_ac12=new Attribute ("ak_acc12");
		Attribute ak_ac13=new Attribute ("ak_acc13");
		Attribute ak_ac21=new Attribute ("ak_ac21");
		Attribute ak_ac22=new Attribute("ak_ac22");
		Attribute ak_ac23=new Attribute("ak_ac23");
		Attribute ak_gy1 =new Attribute("ak_gy1");
		Attribute ak_gy2 =new Attribute("ak_gy2");
		Attribute ak_gy3 =new Attribute("ak_gy3");
		ArrayList<String> labelvalue=new ArrayList<String>(25);
		labelvalue.add("NA");
		labelvalue.add("lying");
		labelvalue.add("sitting");
		labelvalue.add("standing");
		labelvalue.add("wallking");
		labelvalue.add("running");
		labelvalue.add("cycling");
		labelvalue.add("Nodic walking");
		labelvalue.add("NA1");
		labelvalue.add("watching TV");
		labelvalue.add("computer work");
		labelvalue.add("car driving");
		labelvalue.add("ascending stairs");
		labelvalue.add("descending stairs");
		labelvalue.add("NA2");
		labelvalue.add("NA3");
		labelvalue.add("vaccum cleaning");
		labelvalue.add("ironing");
		labelvalue.add("folding laundry");
		labelvalue.add("house cleaing");
		labelvalue.add("playing soccer");
		labelvalue.add("NA4");
		labelvalue.add("NA5");
		labelvalue.add("NA6");
		labelvalue.add("rope jumping");
		Attribute labeltype = new Attribute("class",labelvalue);
		
		ArrayList<Attribute> train_attr = new ArrayList<Attribute>();
		train_attr.add(HR);
		train_attr.add(hd_ac11);
		train_attr.add(hd_ac12);
		train_attr.add(hd_ac13);
		train_attr.add(hd_ac21);
		train_attr.add(hd_ac22);
		train_attr.add(hd_ac23);
		train_attr.add(hd_gy1);
		train_attr.add(hd_gy2);
		train_attr.add(hd_gy3);
		train_attr.add(ch_ac11);
		train_attr.add(ch_ac12);
		train_attr.add(ch_ac13);
		train_attr.add(ch_ac21);
		train_attr.add(ch_ac22);
		train_attr.add(ch_ac23);
		train_attr.add(ch_gy1);
		train_attr.add(ch_gy2);
		train_attr.add(ch_gy3);
		train_attr.add(ak_ac11);
		train_attr.add(ak_ac12);
		train_attr.add(ak_ac13);
		train_attr.add(ak_ac21);
		train_attr.add(ak_ac22);
		train_attr.add(ak_ac23);
		train_attr.add(ak_gy1);
		train_attr.add(ak_gy2);
		train_attr.add(ak_gy3);
		train_attr.add(labeltype);
		trainset=new Instances("Train",train_attr,n_examples);
		for (int i=0; i<n_examples; ++i){
		Instance ex=new DenseInstance(29);
		
		ex.setValue(HR, (double)inputdata[i][0]);
		ex.setValue(hd_ac11, (double)inputdata[i][1]);
		ex.setValue(hd_ac12, (double)inputdata[i][2]);
		ex.setValue(hd_ac13, (double)inputdata[i][3]);
		ex.setValue(hd_ac21, (double)inputdata[i][4]);
		ex.setValue(hd_ac22, (double)inputdata[i][5]);
		ex.setValue(hd_ac23, (double)inputdata[i][6]);
		ex.setValue(hd_gy1, (double)inputdata[i][7]);
		ex.setValue(hd_gy2, (double)inputdata[i][8]);
		ex.setValue(hd_gy3, (double)inputdata[i][9]);
		ex.setValue(ch_ac11, (double)inputdata[i][10]);
		ex.setValue(ch_ac12, (double)inputdata[i][11]);
		ex.setValue(ch_ac13, (double)inputdata[i][12]);
		ex.setValue(ch_ac21, (double)inputdata[i][13]);
		ex.setValue(ch_ac22, (double)inputdata[i][14]);
		ex.setValue(ch_ac23, (double)inputdata[i][15]);
		ex.setValue(ch_gy1, (double)inputdata[i][16]);
		ex.setValue(ch_gy2, (double)inputdata[i][17]);
		ex.setValue(ch_gy3, (double)inputdata[i][18]);
		ex.setValue(ak_ac11, (double)inputdata[i][19]);
		ex.setValue(ak_ac12, (double)inputdata[i][20]);
		ex.setValue(ak_ac13, (double)inputdata[i][21]);
		ex.setValue(ak_ac21, (double)inputdata[i][22]);
		ex.setValue(ak_ac22, (double)inputdata[i][23]);
		ex.setValue(ak_ac23, (double)inputdata[i][24]);
		ex.setValue(ak_gy1, (double)inputdata[i][25]);
		ex.setValue(ak_gy2, (double)inputdata[i][26]);
		ex.setValue(ak_gy3, (double)inputdata[i][27]);
		ex.setValue(labeltype, labelvalue.get((int)inputdata[i][28]));
		trainset.add(ex);
		}
		trainset.setClassIndex(28);
		
		
	}
	public Instances set_testInstances(int n_testex) throws Exception
	{
		
		Attribute HR=new Attribute("HR");
		Attribute hd_ac11=new Attribute ("hd_ac11");
		Attribute hd_ac12=new Attribute ("hd_acc12");
		Attribute hd_ac13=new Attribute ("hd_acc13");
		Attribute hd_ac21=new Attribute ("hd_ac21");
		Attribute hd_ac22=new Attribute("hd_ac22");
		Attribute hd_ac23=new Attribute("hd_ac23");
		Attribute hd_gy1 =new Attribute("hd_gy1");
		Attribute hd_gy2 =new Attribute("hd_gy2");
		Attribute hd_gy3 =new Attribute("hd_gy3");
		Attribute ch_ac11=new Attribute ("ch_ac11");
		Attribute ch_ac12=new Attribute ("ch_acc12");
		Attribute ch_ac13=new Attribute ("ch_acc13");
		Attribute ch_ac21=new Attribute ("ch_ac21");
		Attribute ch_ac22=new Attribute("ch_ac22");
		Attribute ch_ac23=new Attribute("ch_ac23");
		Attribute ch_gy1 =new Attribute("ch_gy1");
		Attribute ch_gy2 =new Attribute("ch_gy2");
		Attribute ch_gy3 =new Attribute("ch_gy3");
		Attribute ak_ac11=new Attribute ("ak_ac11");
		Attribute ak_ac12=new Attribute ("ak_acc12");
		Attribute ak_ac13=new Attribute ("ak_acc13");
		Attribute ak_ac21=new Attribute ("ak_ac21");
		Attribute ak_ac22=new Attribute("ak_ac22");
		Attribute ak_ac23=new Attribute("ak_ac23");
		Attribute ak_gy1 =new Attribute("ak_gy1");
		Attribute ak_gy2 =new Attribute("ak_gy2");
		Attribute ak_gy3 =new Attribute("ak_gy3");
		ArrayList<String> labelvalue=new ArrayList<String>(25);
		labelvalue.add("NA");
		labelvalue.add("lying");
		labelvalue.add("sitting");
		labelvalue.add("standing");
		labelvalue.add("wallking");
		labelvalue.add("running");
		labelvalue.add("cycling");
		labelvalue.add("Nodic walking");
		labelvalue.add("NA1");
		labelvalue.add("watching TV");
		labelvalue.add("computer work");
		labelvalue.add("car driving");
		labelvalue.add("ascending stairs");
		labelvalue.add("descending stairs");
		labelvalue.add("NA2");
		labelvalue.add("NA3");
		labelvalue.add("vaccum cleaning");
		labelvalue.add("ironing");
		labelvalue.add("folding laundry");
		labelvalue.add("house cleaing");
		labelvalue.add("playing soccer");
		labelvalue.add("NA4");
		labelvalue.add("NA5");
		labelvalue.add("NA6");
		labelvalue.add("rope jumping");
		Attribute labeltype = new Attribute("class",labelvalue);
		
		ArrayList<Attribute> train_attr = new ArrayList<Attribute>();
		train_attr.add(HR);
		train_attr.add(hd_ac11);
		train_attr.add(hd_ac12);
		train_attr.add(hd_ac13);
		train_attr.add(hd_ac21);
		train_attr.add(hd_ac22);
		train_attr.add(hd_ac23);
		train_attr.add(hd_gy1);
		train_attr.add(hd_gy2);
		train_attr.add(hd_gy3);
		train_attr.add(ch_ac11);
		train_attr.add(ch_ac12);
		train_attr.add(ch_ac13);
		train_attr.add(ch_ac21);
		train_attr.add(ch_ac22);
		train_attr.add(ch_ac23);
		train_attr.add(ch_gy1);
		train_attr.add(ch_gy2);
		train_attr.add(ch_gy3);
		train_attr.add(ak_ac11);
		train_attr.add(ak_ac12);
		train_attr.add(ak_ac13);
		train_attr.add(ak_ac21);
		train_attr.add(ak_ac22);
		train_attr.add(ak_ac23);
		train_attr.add(ak_gy1);
		train_attr.add(ak_gy2);
		train_attr.add(ak_gy3);
		train_attr.add(labeltype);
		testset=new Instances("Test",train_attr,n_testex);
		for (int i=0; i<n_testex; ++i){
		Instance ex=new DenseInstance(29);
		
		ex.setValue(HR, (double)inputdata[i][0]);
		ex.setValue(hd_ac11, (double)inputdata[i][1]);
		ex.setValue(hd_ac12, (double)inputdata[i][2]);
		ex.setValue(hd_ac13, (double)inputdata[i][3]);
		ex.setValue(hd_ac21, (double)inputdata[i][4]);
		ex.setValue(hd_ac22, (double)inputdata[i][5]);
		ex.setValue(hd_ac23, (double)inputdata[i][6]);
		ex.setValue(hd_gy1, (double)inputdata[i][7]);
		ex.setValue(hd_gy2, (double)inputdata[i][8]);
		ex.setValue(hd_gy3, (double)inputdata[i][9]);
		ex.setValue(ch_ac11, (double)inputdata[i][10]);
		ex.setValue(ch_ac12, (double)inputdata[i][11]);
		ex.setValue(ch_ac13, (double)inputdata[i][12]);
		ex.setValue(ch_ac21, (double)inputdata[i][13]);
		ex.setValue(ch_ac22, (double)inputdata[i][14]);
		ex.setValue(ch_ac23, (double)inputdata[i][15]);
		ex.setValue(ch_gy1, (double)inputdata[i][16]);
		ex.setValue(ch_gy2, (double)inputdata[i][17]);
		ex.setValue(ch_gy3, (double)inputdata[i][18]);
		ex.setValue(ak_ac11, (double)inputdata[i][19]);
		ex.setValue(ak_ac12, (double)inputdata[i][20]);
		ex.setValue(ak_ac13, (double)inputdata[i][21]);
		ex.setValue(ak_ac21, (double)inputdata[i][22]);
		ex.setValue(ak_ac22, (double)inputdata[i][23]);
		ex.setValue(ak_ac23, (double)inputdata[i][24]);
		ex.setValue(ak_gy1, (double)inputdata[i][25]);
		ex.setValue(ak_gy2, (double)inputdata[i][26]);
		ex.setValue(ak_gy3, (double)inputdata[i][27]);
//Label data is absent from the testing set		
		ex.setValue(labeltype, "NA");
		testset.add(ex);
		}
		testset.setClassIndex(28);
		return testset;
	}
	//Run random forest algorithm
	public Classifier rf() throws Exception
	{
		
		model=(Classifier) new RandomForest();
		model.buildClassifier(trainset);
		return model;
		
	}
/**	
	
	public void evaluate() throws Exception
	{
		Evaluation eval=new Evaluation(trainset);
		eval.evaluateModel(model, testset);
		String evalsummary=eval.toSummaryString();
		System.out.println(evalsummary);
		double[][] cmMatrix=eval.confusionMatrix();
		
		
	}
**/	
	public int cal_accuracy(Classifier m, Instances Itest) throws Exception
	{
		outcome=new double[nrow];
		double epsilon=.000000001;
		int correct_class=0;
		for (int i=0;i<nrow; ++i)
		{
		Instance tobeclass=Itest.get(i);	
		outcome[i]=	m.classifyInstance(tobeclass);
		if ((outcome[i]-inputdata[i][28])<epsilon){++correct_class;}
		}
		return correct_class;
	}

}
