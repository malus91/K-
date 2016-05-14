import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler{
	
	public static List<DataPoint> readInputFile(String filePath)
	{
	List<DataPoint> pointList = new ArrayList<>();
	String inputLine;
	String[] input;
	int count =0;
	try
	{
		//Scanner in = new Scanner(new FileReader(filePath));
		BufferedReader reader= new BufferedReader(new FileReader(filePath));
		System.out.println("REad in");
		while((inputLine = reader.readLine())!=null)
		{
			count++;
			if(count==1)
				continue; //Skip first line
			
			//inputLine = in.nextLine();			
			input=inputLine.split("\t");		
			//int id = Integer.parseInt(input[0]);
			double X = Double.parseDouble(input[1]);
			double Y = Double.parseDouble(input[2]);
			DataPoint point = new DataPoint(X,Y); //Initialize a point
			pointList.add(point);				  
										
		}
		
		//in.close();
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
	return pointList;
	}
	
	public static void writeOutput(List<KmeansCluster> points,int k, String outputFile,double sse)
	{
		PrintWriter pw;
		try{
			pw = new PrintWriter(outputFile);
			for(int i=0;i<k;i++)
			{
				pw.print((i+1)+"\t ");
				for(int l=0;l<points.get(i).getPoints().size();l++)
				{
					pw.print(points.get(i).getPoints().get(l).ptID+",");
				}
				pw.print(System.getProperty("line.separator"));
			}
			pw.println("SSE "+sse);
			pw.close();
			
			
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}

}
