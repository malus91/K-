import java.util.ArrayList;
import java.util.List;

public class KmeansDriver {
	
	public static int dListSize =0;	
	public static List<DataPoint> inputDataList;
	//public List<DataPoint> centroidList= null;
	public static List<KmeansCluster> clusterList = new ArrayList<>();
	public static void main(String args[])
	{
		
		
		if(args.length<0)
		{
		args = new String[3];
		args[0] = String.valueOf(8);
		args[1] = "src/test";
		args[2] = "src/output";
		}
				
		Integer numClusters = Integer.parseInt(args[0]);
		String inputFilePath = args[1];
		String outputFilePath = args[2];
		//Gets the input Data in List
		System.out.println("Gets Input");
		inputDataList = FileHandler.readInputFile(inputFilePath);
		System.out.println("Input read completed");
		dListSize = inputDataList.size();
		KmeansDriver kmDriver = new KmeansDriver();
		kmDriver.initiateProcess(numClusters);
		kmDriver.processKMeans(numClusters);
		double sse = validateSSE(clusterList, numClusters);
		System.out.println("SSE "+sse);
		FileHandler.writeOutput(clusterList, numClusters, outputFilePath, sse);
	}
	
	public void initiateProcess(int numClusters)
	{
		//Create random Cluster Centroids
		for(int i=0;i<numClusters;i++)
		{
			KmeansCluster cluster = new KmeansCluster(i);
			DataPoint centroid = DataPoint.createRandomCentroidPt(0, 1);
			
			for(KmeansCluster cltrVal:clusterList)
			{
				if(centroid.xVal == cltrVal.clCentroid.xVal && centroid.yVal == cltrVal.clCentroid.yVal)
					centroid = DataPoint.createRandomCentroidPt(0, 1);
			}
			//Set centroid 
			cluster.clCentroid = centroid;
			cluster.clusterID = i;
			clusterList.add(cluster);
					
		}
		printCluster();
	}
		
	public void printCluster()
	{
		for(int i=0;i<clusterList.size();i++)
		{
			
			printCluster(clusterList.get(i));
		}
	}
   public void processKMeans(int numclts)
   {
		boolean finishFlg = false;
		int iter =0;
		while(!finishFlg && iter<=25)
		{
			clearClusterList();
			List<DataPoint> oldCentroids = getCentroidList();
			
			//Assign Clusters
			assignCluster(numclts);
			updateCentroids();
			iter++;
			
			List<DataPoint> currCentroidList = getCentroidList();
			
			//Calculates total distance between new and old centroids
			double dist =0;
			for(int i=0;i<oldCentroids.size();i++)
			{
				dist+=DataPoint.calcEuclideanDist(oldCentroids.get(i),currCentroidList.get(i));
			}
			System.out.println("********Iteration*****"+iter);
			System.out.println("CentroidDist "+dist);
			
			if(dist==0) //Convergence
				finishFlg = true;
		}
   }
  
	
	private void updateCentroids()
	{
	  for(KmeansCluster cltr:clusterList)
	  {
		  double Xsum =0;
		  double Ysum =0;
		  List<DataPoint> lst = cltr.getPoints();
		  int lstSize =0;
		  if(!lst.isEmpty())
		   lstSize = lst.size();
		  
		  for(DataPoint pt:lst)
		  {
			  Xsum +=pt.xVal;
			  Ysum +=pt.yVal;
			  
		  }
		  
		  DataPoint center = cltr.clCentroid;
		  if(lstSize>0)
		  {
			  double xNew = Xsum/lstSize;
			  double yNew = Ysum/lstSize;
			  center.xVal = xNew;
			  center.yVal = yNew;
		  }
		  
	  }
	
    }

	private void assignCluster(int numCltrs) 
	{
	  double max = Double.MAX_VALUE;
	  double min = Double.MIN_VALUE;
	  int cltr =0;
	  double dist = 0.0;
	  
	  for(DataPoint pt:inputDataList)
	  {
		  min= max;
		  for(int i=0;i<numCltrs;i++)
		  {
			 KmeansCluster clust = clusterList.get(i);
			 dist = DataPoint.calcEuclideanDist(pt, clust.clCentroid);
			 if(dist<min)
			 {
				 min = dist;
				 cltr =i;
			 }
		  }
		  pt.clusterID = cltr;
		  clusterList.get(cltr).addPt(pt);
	  }
	  printCluster();
	
    }

	private List<DataPoint> getCentroidList() 
	{
		List<DataPoint> centroids = new ArrayList<>();
		for(KmeansCluster cltr:clusterList)
		{
			DataPoint aux = cltr.clCentroid;
			DataPoint pnt = new DataPoint(aux.xVal, aux.yVal);
			centroids.add(pnt);
			
		}	    
	    return centroids;
    }

	private void clearClusterList() 
	{
		for(KmeansCluster cltr:clusterList)
		{
			if(cltr.clPtsList!=null)
			 cltr.clPtsList.clear();
		}
		
	}

	public void printCluster(KmeansCluster cluster)
	{
		System.out.println("********************");
		System.out.println("Cluster "+cluster.clusterID);
		System.out.println("Cluster Centroid "+cluster.clCentroid.xVal+" "+cluster.clCentroid.yVal);
		System.out.println("Points in this Cluster");
		for(DataPoint p:cluster.clPtsList)
		{
			System.out.println("ID: "+(p.ptID)+" X: "+p.xVal+" Y: "+p.yVal);
		}
		
	}
	
	private static double validateSSE(List<KmeansCluster> cltrList, int k) 
	{
		double sum=0;
		for(int j=0;j<k;j++)
		{
			for(int l=0;l<cltrList.get(j).clPtsList.size();l++)
			{
				sum +=Math.pow(DataPoint.calcEuclideanDist(cltrList.get(j).clPtsList.get(l),cltrList.get(j).clCentroid),2);
			}
		}
		return sum;
	}

}
