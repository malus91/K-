import java.util.ArrayList;
import java.util.List;

public class KmeansCluster {

	public List<DataPoint> clPtsList;
	public DataPoint clCentroid;
	public int clusterID;
		
	public KmeansCluster(int clusterID) 
	{		
		this.clusterID = clusterID;
		this.clPtsList = new ArrayList<>();
		this.clCentroid = null;
	}

	public void addPt(DataPoint pt)
	{
		this.clPtsList.add(pt);
	}

	public List<DataPoint> getPoints() {
		return this.clPtsList;	
		
	}
	
	
	
	
}
