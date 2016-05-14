import java.util.Random;

public class DataPoint{

	public static int maxID =0;
	public int ptID =0;
	public double xVal=0;
	public double yVal=0;
	public int clusterID =0;
	
	
	public DataPoint(double x, double y) {	
		ptID = maxID+1;
		maxID++;
		this.xVal = x;
		this.yVal = y;
	}
	
	public static double calcEuclideanDist(DataPoint currPt, DataPoint centroid)
	{
		double x1 = currPt.xVal;
		double y1 = currPt.yVal;	
		double x2 = centroid.xVal;
		double y2 = centroid.yVal;
		
		return (double)Math.abs(Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2)));
		
	}
	
	public static DataPoint createRandomCentroidPt(int min,int max)
	{
		Random ran = new Random();
		double x = min+(max-min)*ran.nextDouble();
		double y = min+(max-min)*ran.nextDouble();	
		DataPoint newPt = new DataPoint(x, y);
		return newPt;
	}
	

}
